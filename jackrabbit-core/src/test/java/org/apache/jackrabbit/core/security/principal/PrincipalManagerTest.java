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
package org.apache.jackrabbit.core.security.principal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

import java.security.Principal;
import java.util.Properties;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.jackrabbit.api.security.principal.GroupPrincipal;
import org.apache.jackrabbit.api.security.principal.JackrabbitPrincipal;
import org.apache.jackrabbit.test.AbstractJCRTest;
import org.apache.jackrabbit.test.NotExecutableException;
import org.mockito.Mockito;

/**
 * <code>PrincipalManagerTest</code>...
 */
public class PrincipalManagerTest extends AbstractJCRTest {

	public static AbstractPrincipalProvider mockAbstractPrincipalProvider1() {
		AbstractPrincipalProvider mockInstance = spy(AbstractPrincipalProvider.class);
		doReturn(true).when(mockInstance).canReadPrincipal(any(Session.class), any(Principal.class));
		doThrow(new UnsupportedOperationException()).when(mockInstance).findPrincipals(any(String.class));
		doAnswer((stubInvo) -> {
			String principalName = stubInvo.getArgument(0);
			return TESTGROUP_NAME.equals(principalName) ? TESTGROUP : null;
		}).when(mockInstance).providePrincipal(any(String.class));
		doThrow(new UnsupportedOperationException()).when(mockInstance).findPrincipals(any(String.class), anyInt());
		doThrow(new UnsupportedOperationException()).when(mockInstance).getGroupMembership(any(Principal.class));
		doThrow(new UnsupportedOperationException()).when(mockInstance).getPrincipals(anyInt());
		return mockInstance;
	}

	private static final String TESTGROUP_NAME = "org.apache.jackrabbit.core.security.principal.PrincipalManagerTest.testgroup";
	private static final GroupPrincipal TESTGROUP = Mockito.mock(GroupPrincipal.class);

	/**
	 * Test if a group which is not item based will be wrapped by a
	 * JackrabbitPrincipal implementation.
	 * 
	 * @throws NotExecutableException
	 * @throws RepositoryException
	 */
	public void testJackrabbitPrincipal() throws NotExecutableException, RepositoryException {

		final AbstractPrincipalProvider testProvider = PrincipalManagerTest.mockAbstractPrincipalProvider1();
		testProvider.init(new Properties());
		PrincipalManagerImpl principalManager = new PrincipalManagerImpl(superuser,
				new PrincipalProvider[] { testProvider });
		Principal principalFromManager = principalManager.getPrincipal(TESTGROUP_NAME);
		assertTrue(principalFromManager instanceof JackrabbitPrincipal);
	}
}