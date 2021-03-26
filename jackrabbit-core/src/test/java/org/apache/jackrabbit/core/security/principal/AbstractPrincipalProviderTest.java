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

import org.apache.jackrabbit.test.NotExecutableException;

import junit.framework.TestCase;

/**
 * <code>AbstractPrincipalProviderTest</code>...
 */
public class AbstractPrincipalProviderTest extends TestCase {

	public AbstractPrincipalProvider mockAbstractPrincipalProvider1() {
		boolean[] mockFieldVariableFirst = new boolean[] { true };
		AbstractPrincipalProvider mockInstance = spy(AbstractPrincipalProvider.class);
		doAnswer((stubInvo) -> {
			if (mockFieldVariableFirst[0]) {
				mockFieldVariableFirst[0] = false;
				return null;
			} else {
				throw new UnsupportedOperationException();
			}
		}).when(mockInstance).providePrincipal(any());
		doThrow(new UnsupportedOperationException()).when(mockInstance).getGroupMembership(any(Principal.class));
		doReturn(true).when(mockInstance).canReadPrincipal(any(), any());
		doThrow(new UnsupportedOperationException()).when(mockInstance).findPrincipals(any(String.class));
		doThrow(new UnsupportedOperationException()).when(mockInstance).getPrincipals(anyInt());
		doThrow(new UnsupportedOperationException()).when(mockInstance).findPrincipals(any(String.class), anyInt());
		return mockInstance;
	}

	public void testNegativeCacheEntries() throws RepositoryException, NotExecutableException {
		String unknownName = "UnknownPrincipal";

		AbstractPrincipalProvider caching = mockAbstractPrincipalProvider1();
		Properties options = new Properties();
		options.setProperty(DefaultPrincipalProvider.NEGATIVE_ENTRY_KEY, "true");
		caching.init(options);

		// accessing from wrapper must not throw! as negative entry is expected
		// to be in the cache (default behavior of the DefaultPrincipalProvider)
		assertNull(caching.getPrincipal(unknownName));
		assertNull(caching.getPrincipal(unknownName));

		AbstractPrincipalProvider throwing = mockAbstractPrincipalProvider1();
		options = new Properties();
		options.setProperty(DefaultPrincipalProvider.NEGATIVE_ENTRY_KEY, "false");
		throwing.init(options);

		// however: the noNegativeCacheProvider configured NOT to cache null-results
		// is expected to call 'providePrincipal' for each call to 'getPrincipal'
		// with a principalName that doesn't exist.
		assertNull(throwing.getPrincipal(unknownName));
		try {
			throwing.getPrincipal(unknownName);
			fail("exception expected");
		} catch (UnsupportedOperationException e) {
			// success
		}
	}
}