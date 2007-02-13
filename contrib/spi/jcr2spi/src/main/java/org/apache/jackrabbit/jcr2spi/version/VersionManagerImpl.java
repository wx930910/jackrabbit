/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.jackrabbit.jcr2spi.version;

import org.apache.jackrabbit.jcr2spi.state.NodeState;
import org.apache.jackrabbit.jcr2spi.state.Status;
import org.apache.jackrabbit.jcr2spi.state.PropertyState;
import org.apache.jackrabbit.jcr2spi.state.ItemStateException;
import org.apache.jackrabbit.jcr2spi.operation.Operation;
import org.apache.jackrabbit.jcr2spi.operation.Checkout;
import org.apache.jackrabbit.jcr2spi.operation.Checkin;
import org.apache.jackrabbit.jcr2spi.operation.Restore;
import org.apache.jackrabbit.jcr2spi.operation.ResolveMergeConflict;
import org.apache.jackrabbit.jcr2spi.operation.Merge;
import org.apache.jackrabbit.jcr2spi.operation.AddLabel;
import org.apache.jackrabbit.jcr2spi.operation.RemoveLabel;
import org.apache.jackrabbit.jcr2spi.operation.RemoveVersion;
import org.apache.jackrabbit.jcr2spi.WorkspaceManager;
import org.apache.jackrabbit.jcr2spi.hierarchy.NodeEntry;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.jcr.RepositoryException;
import javax.jcr.version.VersionException;

import org.apache.jackrabbit.name.QName;
import org.apache.jackrabbit.name.Path;
import org.apache.jackrabbit.spi.IdIterator;
import org.apache.jackrabbit.spi.NodeId;

/**
 * <code>VersionManagerImpl</code>...
 */
public class VersionManagerImpl implements VersionManager {

    private static Logger log = LoggerFactory.getLogger(VersionManagerImpl.class);

    private final WorkspaceManager workspaceManager;

    public VersionManagerImpl(WorkspaceManager workspaceManager) {
        this.workspaceManager = workspaceManager;
    }

    public void checkin(NodeState nodeState) throws RepositoryException {
        NodeState wspState = getWorkspaceState(nodeState);
        Operation ci = Checkin.create(wspState, this);
        workspaceManager.execute(ci);
    }

    public void checkout(NodeState nodeState) throws RepositoryException {
        NodeState wspState = getWorkspaceState(nodeState);
        Operation co = Checkout.create(wspState, this);
        workspaceManager.execute(co);
    }

    /**
     * Search nearest ancestor that is versionable. If no versionable ancestor
     * can be found, <code>true</code> is returned.
     *
     * @param nodeState
     * @return
     * @throws RepositoryException
     */
    public boolean isCheckedOut(NodeState nodeState) throws RepositoryException {
        // shortcut: if state is new, its ancestor must be checkout
        if (nodeState.getStatus() == Status.NEW) {
            return true;
        }

        NodeEntry nodeEntry = nodeState.getNodeEntry();
        try {
            // NOTE: since the hierarchy might not be completely loaded or some
            // entry might even not be accessible, the check may not detect
            // a checked-in parent. ok, as long as the 'server' find out upon
            // save or upon executing the workspace operation.
            while (!nodeEntry.hasPropertyEntry(QName.JCR_ISCHECKEDOUT)) {
                NodeEntry parent = nodeEntry.getParent();
                if (parent == null) {
                    // reached root state without finding a jcr:isCheckedOut property
                    return true;
                }
                nodeEntry = parent;
            }
            PropertyState propState = nodeEntry.getPropertyEntry(QName.JCR_ISCHECKEDOUT).getPropertyState();
            Boolean b = Boolean.valueOf(propState.getValue().getString());
            return b.booleanValue();
        } catch (ItemStateException e) {
            // error while accessing jcr:isCheckedOut property state.
            // -> assume that checkedOut status is ok. see above for general
            // notes about the capabilities of the jcr2spi implementation.
        }
        return true;
    }

    public void checkIsCheckedOut(NodeState nodeState) throws VersionException, RepositoryException {
        if (!isCheckedOut(nodeState)) {
            throw new VersionException(nodeState + " is checked-in");
        }
    }

    public void removeVersion(NodeState versionHistoryState, NodeState versionState) throws RepositoryException {
        NodeState wspVersionState = getWorkspaceState(versionState);
        Operation op = RemoveVersion.create(wspVersionState, getWorkspaceState(versionHistoryState), this);
        workspaceManager.execute(op);
    }

    public void addVersionLabel(NodeState versionHistoryState, NodeState versionState, QName qLabel, boolean moveLabel) throws RepositoryException {
        NodeState wspVHState = getWorkspaceState(versionHistoryState);
        NodeState wspVState = getWorkspaceState(versionState);
        Operation op = AddLabel.create(wspVHState, wspVState, qLabel, moveLabel);
        workspaceManager.execute(op);
    }

    public void removeVersionLabel(NodeState versionHistoryState, NodeState versionState, QName qLabel) throws RepositoryException {
        NodeState wspVHState = getWorkspaceState(versionHistoryState);
        NodeState wspVState = getWorkspaceState(versionState);
        Operation op = RemoveLabel.create(wspVHState, wspVState, qLabel);
        workspaceManager.execute(op);
    }

    public void restore(NodeState nodeState, Path relativePath, NodeState versionState, boolean removeExisting) throws RepositoryException {
        NodeState wspState = getWorkspaceState(nodeState);
        NodeState wspVState = getWorkspaceState(versionState);
        Operation op = Restore.create(wspState, relativePath, wspVState, removeExisting);
        workspaceManager.execute(op);
    }

    public void restore(NodeState[] versionStates, boolean removeExisting) throws RepositoryException {
        NodeState[] wspStates = new NodeState[versionStates.length];
        for (int i = 0; i < versionStates.length; i++) {
            wspStates[i] = getWorkspaceState(versionStates[i]);
        }

        Operation op = Restore.create(wspStates, removeExisting);
        workspaceManager.execute(op);
    }

    public IdIterator merge(NodeState nodeState, String workspaceName, boolean bestEffort) throws RepositoryException {
        NodeState wspState = getWorkspaceState(nodeState);
        Merge op = Merge.create(wspState, workspaceName, bestEffort, this);
        workspaceManager.execute(op);
        return op.getFailedIds();
    }

    public void resolveMergeConflict(NodeState nodeState, NodeState versionState, boolean done) throws RepositoryException {
        NodeState wspState = getWorkspaceState(nodeState);
        NodeState wspVState = getWorkspaceState(versionState);
        Operation op = ResolveMergeConflict.create(wspState, wspVState, done);
        workspaceManager.execute(op);
    }

    public NodeEntry getVersionableNodeState(NodeState versionState) throws RepositoryException {
        try {
            NodeState ns = versionState.getChildNodeState(QName.JCR_FROZENNODE, Path.INDEX_DEFAULT);
            PropertyState ps = ns.getPropertyState(QName.JCR_FROZENUUID);
            String uniqueID = ps.getValue().toString();

            NodeId versionableId = workspaceManager.getIdFactory().createNodeId(uniqueID);
            return (NodeEntry) workspaceManager.getHierarchyManager().getHierarchyEntry(versionableId);
        } catch (ItemStateException e) {
            throw new RepositoryException(e);
        }
    }

    public NodeEntry getVersionHistoryNodeState(NodeState versionableState) throws RepositoryException {
        try {
            PropertyState ps = versionableState.getPropertyState(QName.JCR_VERSIONHISTORY);
            String uniqueID = ps.getValue().toString();
            NodeId vhId = workspaceManager.getIdFactory().createNodeId(uniqueID);
            return (NodeEntry) workspaceManager.getHierarchyManager().getHierarchyEntry(vhId);
        } catch (ItemStateException e) {
            // should not occur
            throw new RepositoryException(e);
        }
    }

    //------------------------------------------------------------< private >---
    /**
     * If the given <code>NodeState</code> has an overlayed state, the overlayed
     * (workspace) state will be returned. Otherwise the given state is returned.
     *
     * @param nodeState
     * @return The overlayed state or the given state, if this one does not have
     * an overlayed state.
     */
    private NodeState getWorkspaceState(NodeState nodeState) {
        return (NodeState) nodeState.getWorkspaceState();
    }
}