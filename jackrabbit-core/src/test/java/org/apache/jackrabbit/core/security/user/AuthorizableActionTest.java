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
package org.apache.jackrabbit.core.security.user;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.jackrabbit.api.security.user.AbstractUserTest;
import org.apache.jackrabbit.api.security.user.User;
import org.apache.jackrabbit.core.security.user.action.AbstractAuthorizableAction;
import org.apache.jackrabbit.core.security.user.action.AuthorizableAction;

/**
 * <code>AuthorizableActionTest</code>...
 */
public class AuthorizableActionTest extends AbstractUserTest {

	private UserManagerImpl impl;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		impl = (UserManagerImpl) userMgr;
	}

	@Override
	protected void tearDown() throws Exception {
		// reset the actions
		setActions(null);

		super.tearDown();
	}

	private void setActions(AuthorizableAction action) {
		AuthorizableAction[] actions = (action == null) ? new AuthorizableAction[0]
				: new AuthorizableAction[] { action };
		impl.setAuthorizableActions(actions);
	}

	public void testPasswordAction() throws Exception {
		User u = null;

		try {
			TestAction action = new TestAction();
			setActions(action);

			String uid = getTestPrincipal().getName();
			u = impl.createUser(uid, buildPassword(uid));

			u.changePassword("pw1");
			assertEquals(1, action.called);

			u.changePassword("pw2", "pw1");
			assertEquals(2, action.called);
		} finally {
			if (u != null) {
				u.remove();
			}
			save(superuser);
		}
	}

	// --------------------------------------------------------------------------
	private class TestAction extends AbstractAuthorizableAction {

		private int called = 0;

		@Override
		public void onPasswordChange(User user, String newPassword, Session session) throws RepositoryException {
			called++;
		}
	}
}