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

import java.util.Properties;

import javax.jcr.RepositoryException;

import org.apache.jackrabbit.test.NotExecutableException;
import org.mockito.Mockito;

import junit.framework.TestCase;

/**
 * <code>AbstractPrincipalProviderTest</code>...
 */
public class AbstractPrincipalProviderTest extends TestCase {

	public AbstractPrincipalProvider mockAbstractPrincipalProvider1() {
		boolean[] mockFieldVariableFirst = new boolean[] { true };
		AbstractPrincipalProvider mockInstance = Mockito.spy(AbstractPrincipalProvider.class);
		try {
			Mockito.doAnswer((stubInvo) -> {
				throw new UnsupportedOperationException();
			}).when(mockInstance).findPrincipals(Mockito.any(String.class));
			Mockito.doAnswer((stubInvo) -> {
				throw new UnsupportedOperationException();
			}).when(mockInstance).findPrincipals(Mockito.any(String.class), Mockito.anyInt());
			Mockito.doAnswer((stubInvo) -> {
				return true;
			}).when(mockInstance).canReadPrincipal(Mockito.any(), Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				if (mockFieldVariableFirst[0]) {
					mockFieldVariableFirst[0] = false;
					return null;
				} else {
					throw new UnsupportedOperationException();
				}
			}).when(mockInstance).providePrincipal(Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				throw new UnsupportedOperationException();
			}).when(mockInstance).getGroupMembership(Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				throw new UnsupportedOperationException();
			}).when(mockInstance).getPrincipals(Mockito.anyInt());
		} catch (Exception exception) {
		}
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