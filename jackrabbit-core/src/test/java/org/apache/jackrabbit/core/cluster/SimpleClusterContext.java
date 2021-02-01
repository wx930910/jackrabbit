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
package org.apache.jackrabbit.core.cluster;

import java.io.File;

import org.apache.jackrabbit.core.config.ClusterConfig;
import org.apache.jackrabbit.core.nodetype.xml.SimpleNamespaceRegistry;
import org.apache.jackrabbit.spi.commons.namespace.NamespaceResolver;
import org.apache.jackrabbit.spi.commons.namespace.RegistryNamespaceResolver;
import org.mockito.Mockito;

/**
 * Simple cluster context, providing only limited functionality.
 */
public class SimpleClusterContext {

	static public ClusterContext mockClusterContext2(ClusterConfig cc) {
		File[] mockFieldVariableRepositoryHome = new File[1];
		ClusterConfig[] mockFieldVariableCc = new ClusterConfig[1];
		NamespaceResolver[] mockFieldVariableNsResolver = new NamespaceResolver[1];
		ClusterContext mockInstance = Mockito.spy(ClusterContext.class);
		mockFieldVariableCc[0] = cc;
		mockFieldVariableRepositoryHome[0] = null;
		mockFieldVariableNsResolver[0] = new RegistryNamespaceResolver(new SimpleNamespaceRegistry());
		try {
			Mockito.doAnswer((stubInvo) -> {
				return mockFieldVariableNsResolver[0];
			}).when(mockInstance).getNamespaceResolver();
			Mockito.doAnswer((stubInvo) -> {
				return mockFieldVariableRepositoryHome[0];
			}).when(mockInstance).getRepositoryHome();
			Mockito.doAnswer((stubInvo) -> {
				return mockFieldVariableCc[0];
			}).when(mockInstance).getClusterConfig();
		} catch (Exception exception) {
		}
		return mockInstance;
	}

	static public ClusterContext mockClusterContext1(ClusterConfig cc, File repositoryHome) {
		File[] mockFieldVariableRepositoryHome = new File[1];
		ClusterConfig[] mockFieldVariableCc = new ClusterConfig[1];
		NamespaceResolver[] mockFieldVariableNsResolver = new NamespaceResolver[1];
		ClusterContext mockInstance = Mockito.spy(ClusterContext.class);
		mockFieldVariableCc[0] = cc;
		mockFieldVariableRepositoryHome[0] = repositoryHome;
		mockFieldVariableNsResolver[0] = new RegistryNamespaceResolver(new SimpleNamespaceRegistry());
		try {
			Mockito.doAnswer((stubInvo) -> {
				return mockFieldVariableNsResolver[0];
			}).when(mockInstance).getNamespaceResolver();
			Mockito.doAnswer((stubInvo) -> {
				return mockFieldVariableRepositoryHome[0];
			}).when(mockInstance).getRepositoryHome();
			Mockito.doAnswer((stubInvo) -> {
				return mockFieldVariableCc[0];
			}).when(mockInstance).getClusterConfig();
		} catch (Exception exception) {
		}
		return mockInstance;
	}

	// ----------------------------------------------------------- ClusterContext

}
