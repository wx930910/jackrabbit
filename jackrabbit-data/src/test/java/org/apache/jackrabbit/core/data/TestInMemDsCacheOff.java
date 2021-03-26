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
package org.apache.jackrabbit.core.data;

import javax.jcr.RepositoryException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mockito.Mockito;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.*;
import org.apache.jackrabbit.core.data.Backend;
import org.apache.jackrabbit.core.data.CachingDataStore;
import java.util.Properties;

/**
 * Test {@link CachingDataStore} with InMemoryBackend and local cache off.
 */

public class TestInMemDsCacheOff extends TestCaseBase {

    protected static final Logger LOG = LoggerFactory.getLogger(TestInMemDsCacheOff.class);
    @Override
    protected DataStore createDataStore() throws RepositoryException {
        CachingDataStore inMemDS = spy(CachingDataStore.class);
		Properties[] inMemDSProperties = new Properties[1];
		doReturn("mem.init.done").when(inMemDS).getMarkerFile();
		doAnswer((stubInvo) -> {
			InMemoryBackend backend = new InMemoryBackend();
			if (inMemDSProperties[0] != null) {
				backend.setProperties(inMemDSProperties[0]);
			}
			return backend;
		}).when(inMemDS).createBackend();
        inMemDSProperties[0] = null;
        inMemDS.init(dataStoreDir);
        inMemDS.setSecret("12345");
        inMemDS.setCacheSize(0);
        return inMemDS;
    }
}
