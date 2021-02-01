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
package org.apache.jackrabbit.spi.commons.query.xpath;

import java.util.Collections;

import javax.jcr.query.InvalidQueryException;
import javax.jcr.query.Query;

import org.apache.jackrabbit.spi.Name;
import org.apache.jackrabbit.spi.commons.conversion.DefaultNamePathResolver;
import org.apache.jackrabbit.spi.commons.conversion.NameResolver;
import org.apache.jackrabbit.spi.commons.namespace.NamespaceResolver;
import org.apache.jackrabbit.spi.commons.query.DefaultQueryNodeFactory;
import org.apache.jackrabbit.spi.commons.query.QueryNodeFactory;
import org.apache.jackrabbit.spi.commons.query.QueryParser;
import org.apache.jackrabbit.spi.commons.query.QueryRootNode;
import org.mockito.Mockito;

import junit.framework.TestCase;

/**
 * <code>QueryFormatTest</code> performs tests on {@link QueryFormat}.
 */
public class QueryFormatTest extends TestCase {

	public static NamespaceResolver mockNamespaceResolver1() {
		NamespaceResolver mockInstance = Mockito.spy(NamespaceResolver.class);
		try {
			Mockito.doAnswer((stubInvo) -> {
				String prefix = stubInvo.getArgument(0);
				if (Name.NS_REP_PREFIX.equals(prefix)) {
					return Name.NS_REP_URI;
				} else {
					return stubInvo.callRealMethod();
				}
			}).when(mockInstance).getURI(Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				String uri = stubInvo.getArgument(0);
				if (Name.NS_REP_URI.equals(uri)) {
					return Name.NS_REP_PREFIX;
				} else {
					return stubInvo.callRealMethod();
				}
			}).when(mockInstance).getPrefix(Mockito.any());
		} catch (Exception exception) {
		}
		return mockInstance;
	}

	private static final NameResolver RESOLVER = new DefaultNamePathResolver(mockNamespaceResolver1());

	private static final QueryNodeFactory FACTORY = new DefaultQueryNodeFactory(Collections.<Name>emptyList());

	public void testSelectWithOrderBy() throws InvalidQueryException {
		checkStatement("//element(*, foo)/(@a|@b) order by @bar");
	}

	public void testStarNameTest() throws Exception {
		checkStatement("//element(*, foo)[foo/*/@bar = 'bla']");
	}

	public void testStarNameAtBeginningOfPredicate() throws Exception {
		checkStatement("//element(*, foo)[*/*/@bar = 'bla']");
	}

	public void testChildStarName() throws Exception {
		checkStatement("//programs//*[*/@sunday]");
	}

	public void testRepSimilar() throws Exception {
		checkStatement("//element(*, foo)[rep:similar(foo, '/some/path')]");
	}

	protected void checkStatement(String stmt) throws InvalidQueryException {
		QueryRootNode root = QueryParser.parse(stmt, Query.XPATH, RESOLVER, FACTORY);
		assertEquals(stmt, QueryFormat.toString(root, RESOLVER));
	}
}
