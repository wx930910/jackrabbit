/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  The ASF licenses this file to You
 * under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.jackrabbit.webdav.security;

import org.apache.jackrabbit.webdav.property.AbstractDavProperty;
import org.apache.jackrabbit.webdav.property.DavProperty;
import org.apache.jackrabbit.webdav.DavException;
import org.apache.jackrabbit.webdav.DavServletResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Collection;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;

/**
 * <code>CurrentUserPrivilegeSetProperty</code>...
 */
public class CurrentUserPrivilegeSetProperty extends AbstractDavProperty {

    private final Set privileges;

    /**
     * Create a new instance of this property.
     *
     * @param privileges array privileges.
     */
    public CurrentUserPrivilegeSetProperty(Privilege[] privileges) {
        super(SecurityConstants.CURRENT_USER_PRIVILEGE_SET, true);

        this.privileges = new HashSet();
        for (int i = 0; i < privileges.length; i++) {
            if (privileges[i] != null) {
                this.privileges.add(privileges[i]);
            }
        }
    }

    /**
     * Create a new <code>CurrentUserPrivilegeSetProperty</code> from a DavProperty
     * as obtained from a MultiStatusResponse.
     *
     * @param xmlDavProperty
     * @throws DavException
     */
    public CurrentUserPrivilegeSetProperty(DavProperty xmlDavProperty) throws DavException {
        super(xmlDavProperty.getName(), xmlDavProperty.isProtected());
        if (!SecurityConstants.CURRENT_USER_PRIVILEGE_SET.equals(getName()) || !isProtected()) {
            throw new DavException(DavServletResponse.SC_BAD_REQUEST, "DAV:current-user-privilege-set expected.");
        }
        privileges = new HashSet();

        // parse property value
        Object value = xmlDavProperty.getValue();
        if (value != null) {
            if (value instanceof Element) {
                privileges.add(Privilege.getPrivilege((Element)value));
            } else if (value instanceof Collection) {
                Iterator it = ((Collection)value).iterator();
                while (it.hasNext()) {
                    Object entry = it.next();
                    if (entry instanceof Element) {
                        privileges.add(Privilege.getPrivilege((Element)entry));
                    }
                }
            }
        }
    }

    /**
     * @return a Collection of <code>Privilege</code> objects use for xml serialization.
     * @see DavProperty#getValue()
     * @see AbstractDavProperty#toXml(Document)
     */
    public Object getValue() {
        return privileges;
    }
}