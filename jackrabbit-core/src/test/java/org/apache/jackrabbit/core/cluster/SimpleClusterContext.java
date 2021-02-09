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

	public static ClusterContext mockClusterContext2(ClusterConfig cc) throws Exception {
		ClusterConfig mockFieldVariableCc;
		File mockFieldVariableRepositoryHome;
		NamespaceResolver mockFieldVariableNsResolver;
		ClusterContext mockInstance = Mockito.mock(ClusterContext.class);
		mockFieldVariableCc = cc;
		mockFieldVariableRepositoryHome = null;
		mockFieldVariableNsResolver = new RegistryNamespaceResolver(new SimpleNamespaceRegistry());
		Mockito.when(mockInstance.getRepositoryHome()).thenAnswer((stubInvo) -> {
			return mockFieldVariableRepositoryHome;
		});
		Mockito.when(mockInstance.getNamespaceResolver()).thenAnswer((stubInvo) -> {
			return mockFieldVariableNsResolver;
		});
		Mockito.when(mockInstance.getClusterConfig()).thenAnswer((stubInvo) -> {
			return mockFieldVariableCc;
		});
		return mockInstance;
	}

	public static ClusterContext mockClusterContext1(ClusterConfig cc, File repositoryHome) throws Exception {
		ClusterConfig mockFieldVariableCc;
		File mockFieldVariableRepositoryHome;
		NamespaceResolver mockFieldVariableNsResolver;
		ClusterContext mockInstance = Mockito.mock(ClusterContext.class);
		mockFieldVariableCc = cc;
		mockFieldVariableRepositoryHome = repositoryHome;
		mockFieldVariableNsResolver = new RegistryNamespaceResolver(new SimpleNamespaceRegistry());
		Mockito.when(mockInstance.getRepositoryHome()).thenAnswer((stubInvo) -> {
			return mockFieldVariableRepositoryHome;
		});
		Mockito.when(mockInstance.getNamespaceResolver()).thenAnswer((stubInvo) -> {
			return mockFieldVariableNsResolver;
		});
		Mockito.when(mockInstance.getClusterConfig()).thenAnswer((stubInvo) -> {
			return mockFieldVariableCc;
		});
		return mockInstance;
	}

	// ----------------------------------------------------------- ClusterContext

}
