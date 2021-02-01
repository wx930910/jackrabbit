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
package org.apache.jackrabbit.commons.json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.mockito.Mockito;

import junit.framework.TestCase;

/**
 * <code>JSONParserTest</code>...
 */
public class JsonParserTest extends TestCase {

	public JsonHandler mockJsonHandler12() {
		JsonHandler mockInstance = Mockito.spy(JsonHandler.class);
		try {
			Mockito.doAnswer((stubInvo) -> {
				boolean value = stubInvo.getArgument(0);
				assertEquals(true, value);
				return null;
			}).when(mockInstance).value(Mockito.anyBoolean());
			Mockito.doAnswer((stubInvo) -> {
				fail();
				return null;
			}).when(mockInstance).value(Mockito.any(String.class));
			Mockito.doAnswer((stubInvo) -> {
				fail();
				return null;
			}).when(mockInstance).value(Mockito.anyDouble());
			Mockito.doAnswer((stubInvo) -> {
				fail();
				return null;
			}).when(mockInstance).value(Mockito.anyLong());
			Mockito.doAnswer((stubInvo) -> {
				String key = stubInvo.getArgument(0);
				assertEquals("boolean", key);
				return null;
			}).when(mockInstance).key(Mockito.any());
		} catch (Exception exception) {
		}
		return mockInstance;
	}

	public JsonHandler mockJsonHandler11() {
		JsonHandler mockInstance = Mockito.spy(JsonHandler.class);
		try {
			Mockito.doAnswer((stubInvo) -> {
				fail();
				return null;
			}).when(mockInstance).value(Mockito.anyBoolean());
			Mockito.doAnswer((stubInvo) -> {
				long value = stubInvo.getArgument(0);
				assertEquals(123456, value);
				return null;
			}).when(mockInstance).value(Mockito.anyLong());
			Mockito.doAnswer((stubInvo) -> {
				String key = stubInvo.getArgument(0);
				assertEquals("long", key);
				return null;
			}).when(mockInstance).key(Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				fail();
				return null;
			}).when(mockInstance).value(Mockito.any(String.class));
			Mockito.doAnswer((stubInvo) -> {
				fail();
				return null;
			}).when(mockInstance).value(Mockito.anyDouble());
		} catch (Exception exception) {
		}
		return mockInstance;
	}

	public JsonHandler mockJsonHandler10() {
		JsonHandler mockInstance = Mockito.spy(JsonHandler.class);
		try {
			Mockito.doAnswer((stubInvo) -> {
				fail();
				return null;
			}).when(mockInstance).value(Mockito.anyBoolean());
			Mockito.doAnswer((stubInvo) -> {
				fail();
				return null;
			}).when(mockInstance).value(Mockito.anyLong());
			Mockito.doAnswer((stubInvo) -> {
				String key = stubInvo.getArgument(0);
				assertEquals("double", key);
				return null;
			}).when(mockInstance).key(Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				double value = stubInvo.getArgument(0);
				assertEquals(new Double(1235674.342424), new Double(value));
				return null;
			}).when(mockInstance).value(Mockito.anyDouble());
			Mockito.doAnswer((stubInvo) -> {
				fail();
				return null;
			}).when(mockInstance).value(Mockito.any(String.class));
		} catch (Exception exception) {
		}
		return mockInstance;
	}

	public JsonHandler mockJsonHandler9() {
		JsonHandler mockInstance = Mockito.spy(JsonHandler.class);
		try {
			Mockito.doAnswer((stubInvo) -> {
				String key = stubInvo.getArgument(0);
				assertEquals("string", key);
				return null;
			}).when(mockInstance).key(Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				fail();
				return null;
			}).when(mockInstance).value(Mockito.anyBoolean());
			Mockito.doAnswer((stubInvo) -> {
				String value = stubInvo.getArgument(0);
				assertEquals("abc", value);
				return null;
			}).when(mockInstance).value(Mockito.any(String.class));
			Mockito.doAnswer((stubInvo) -> {
				fail();
				return null;
			}).when(mockInstance).value(Mockito.anyDouble());
			Mockito.doAnswer((stubInvo) -> {
				fail();
				return null;
			}).when(mockInstance).value(Mockito.anyLong());
		} catch (Exception exception) {
		}
		return mockInstance;
	}

	public JsonHandler mockJsonHandler8() {
		JsonHandler mockInstance = Mockito.spy(JsonHandler.class);
		try {
			Mockito.doAnswer((stubInvo) -> {
				String value = stubInvo.getArgument(0);
				assertEquals("abc\"abc", value);
				return null;
			}).when(mockInstance).value(Mockito.any(String.class));
			Mockito.doAnswer((stubInvo) -> {
				fail();
				return null;
			}).when(mockInstance).value(Mockito.anyDouble());
			Mockito.doAnswer((stubInvo) -> {
				String key = stubInvo.getArgument(0);
				assertEquals("string", key);
				return null;
			}).when(mockInstance).key(Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				fail();
				return null;
			}).when(mockInstance).value(Mockito.anyLong());
			Mockito.doAnswer((stubInvo) -> {
				fail();
				return null;
			}).when(mockInstance).value(Mockito.anyBoolean());
		} catch (Exception exception) {
		}
		return mockInstance;
	}

	public JsonHandler mockJsonHandler7() {
		JsonHandler mockInstance = Mockito.spy(JsonHandler.class);
		try {
			Mockito.doAnswer((stubInvo) -> {
				fail();
				return null;
			}).when(mockInstance).value(Mockito.anyDouble());
			Mockito.doAnswer((stubInvo) -> {
				fail();
				return null;
			}).when(mockInstance).value(Mockito.anyBoolean());
			Mockito.doAnswer((stubInvo) -> {
				String key = stubInvo.getArgument(0);
				assertEquals("string", key);
				return null;
			}).when(mockInstance).key(Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				String value = stubInvo.getArgument(0);
				assertEquals("abc\\abc", value);
				return null;
			}).when(mockInstance).value(Mockito.any(String.class));
			Mockito.doAnswer((stubInvo) -> {
				fail();
				return null;
			}).when(mockInstance).value(Mockito.anyLong());
		} catch (Exception exception) {
		}
		return mockInstance;
	}

	public JsonHandler mockJsonHandler6() {
		JsonHandler mockInstance = Mockito.spy(JsonHandler.class);
		try {
			Mockito.doAnswer((stubInvo) -> {
				String key = stubInvo.getArgument(0);
				assertEquals("string", key);
				return null;
			}).when(mockInstance).key(Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				fail();
				return null;
			}).when(mockInstance).value(Mockito.anyDouble());
			Mockito.doAnswer((stubInvo) -> {
				fail();
				return null;
			}).when(mockInstance).value(Mockito.anyLong());
			Mockito.doAnswer((stubInvo) -> {
				fail();
				return null;
			}).when(mockInstance).value(Mockito.anyBoolean());
			Mockito.doAnswer((stubInvo) -> {
				String value = stubInvo.getArgument(0);
				assertEquals("\'abc\\\\x\\'abc", value);
				return null;
			}).when(mockInstance).value(Mockito.any(String.class));
		} catch (Exception exception) {
		}
		return mockInstance;
	}

	public JsonHandler mockJsonHandler5() {
		JsonHandler mockInstance = Mockito.spy(JsonHandler.class);
		try {
			Mockito.doAnswer((stubInvo) -> {
				String key = stubInvo.getArgument(0);
				assertEquals("string", key);
				return null;
			}).when(mockInstance).key(Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				String value = stubInvo.getArgument(0);
				assertEquals("abc\u2345ab\u00EB\u0633c", value);
				return null;
			}).when(mockInstance).value(Mockito.any(String.class));
			Mockito.doAnswer((stubInvo) -> {
				fail();
				return null;
			}).when(mockInstance).value(Mockito.anyLong());
			Mockito.doAnswer((stubInvo) -> {
				fail();
				return null;
			}).when(mockInstance).value(Mockito.anyDouble());
			Mockito.doAnswer((stubInvo) -> {
				fail();
				return null;
			}).when(mockInstance).value(Mockito.anyBoolean());
		} catch (Exception exception) {
		}
		return mockInstance;
	}

	public JsonHandler mockJsonHandler4() {
		JsonHandler mockInstance = Mockito.spy(JsonHandler.class);
		try {
			Mockito.doAnswer((stubInvo) -> {
				String value = stubInvo.getArgument(0);
				assertEquals("\u00EB", value);
				return null;
			}).when(mockInstance).value(Mockito.any(String.class));
			Mockito.doAnswer((stubInvo) -> {
				fail();
				return null;
			}).when(mockInstance).value(Mockito.anyLong());
			Mockito.doAnswer((stubInvo) -> {
				fail();
				return null;
			}).when(mockInstance).value(Mockito.anyBoolean());
			Mockito.doAnswer((stubInvo) -> {
				String key = stubInvo.getArgument(0);
				assertEquals("string", key);
				return null;
			}).when(mockInstance).key(Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				fail();
				return null;
			}).when(mockInstance).value(Mockito.anyDouble());
		} catch (Exception exception) {
		}
		return mockInstance;
	}

	public JsonHandler mockJsonHandler3(String expValue) {
		JsonHandler mockInstance = Mockito.spy(JsonHandler.class);
		try {
			Mockito.doAnswer((stubInvo) -> {
				String value = stubInvo.getArgument(0);
				assertEquals(expValue, value);
				return null;
			}).when(mockInstance).value(Mockito.any(String.class));
			Mockito.doAnswer((stubInvo) -> {
				String key = stubInvo.getArgument(0);
				assertEquals("string", key);
				return null;
			}).when(mockInstance).key(Mockito.any());
		} catch (Exception exception) {
		}
		return mockInstance;
	}

	public JsonHandler mockJsonHandler2() {
		JsonHandler mockInstance = Mockito.spy(JsonHandler.class);
		try {
			Mockito.doAnswer((stubInvo) -> {
				String value = stubInvo.getArgument(0);
				assertNull(value);
				return null;
			}).when(mockInstance).value(Mockito.any(String.class));
			Mockito.doAnswer((stubInvo) -> {
				String key = stubInvo.getArgument(0);
				assertEquals("null", key);
				return null;
			}).when(mockInstance).key(Mockito.any());
		} catch (Exception exception) {
		}
		return mockInstance;
	}

	public JsonHandler mockJsonHandler1(String expValue, String expKey) {
		JsonHandler mockInstance = Mockito.spy(JsonHandler.class);
		try {
			Mockito.doAnswer((stubInvo) -> {
				String value = stubInvo.getArgument(0);
				assertEquals(expValue, value);
				return null;
			}).when(mockInstance).value(Mockito.any(String.class));
			Mockito.doAnswer((stubInvo) -> {
				String key = stubInvo.getArgument(0);
				assertEquals(expKey, key);
				return null;
			}).when(mockInstance).key(Mockito.any());
		} catch (Exception exception) {
		}
		return mockInstance;
	}

	private final JsonHandler handler = new DummyJsonHandler();

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testParser() throws Exception {
		// TODO validate output. use specific handler ext.
		JsonParser parser = new JsonParser(handler);
		parser.parse(getObj().toString());
	}

	public void testParseBooleanValue() throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("boolean", true);

		JsonHandler handler = mockJsonHandler12();
		JsonParser parser = new JsonParser(handler);
		parser.parse(obj.toString());
	}

	public void testParseLongValue() throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("long", 123456);

		JsonHandler handler = mockJsonHandler11();
		JsonParser parser = new JsonParser(handler);
		parser.parse(obj.toString());
	}

	public void testDoubleValue() throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("double", 1235674.342424);

		JsonHandler handler = mockJsonHandler10();
		JsonParser parser = new JsonParser(handler);
		parser.parse(obj.toString());
	}

	public void testStringValue() throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("string", "abc");

		JsonHandler handler = mockJsonHandler9();
		JsonParser parser = new JsonParser(handler);
		parser.parse(obj.toString());
	}

	public void testStringWithQuoteValue() throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("string", "abc\"abc");

		JsonHandler handler = mockJsonHandler8();
		JsonParser parser = new JsonParser(handler);
		parser.parse(obj.toString());
	}

	public void testStringWithBackSlashValue() throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("string", "abc\\abc");

		JsonHandler handler = mockJsonHandler7();
		JsonParser parser = new JsonParser(handler);
		parser.parse(obj.toString());
	}

	public void testStringWithBackSlashValue2() throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("string", "\'abc\\\\x\\'abc");

		JsonHandler handler = mockJsonHandler6();
		JsonParser parser = new JsonParser(handler);
		parser.parse(obj.toString());
	}

	public void testStringWithUnicode() throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("string", "abc\u2345ab\u00EB\u0633c");

		JsonHandler handler = mockJsonHandler5();
		JsonParser parser = new JsonParser(handler);
		parser.parse(obj.toString());
	}

	public void testStringWithUnicode2() throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("string", "\u00EB");

		JsonHandler handler = mockJsonHandler4();
		JsonParser parser = new JsonParser(handler);
		parser.parse(obj.toString());
	}

	public void testStringWithReturn() throws Exception {
		ArrayList l = new ArrayList();
		l.add("abc\ndef");
		l.add("abc\rdef");
		l.add("abc\n\rdef");
		l.add("abc\tdef");
		l.add("abc\bdef");
		l.add("abc\n\\\tdef");
		l.add("abc\f\u3456\b\\def");

		for (Iterator it = l.iterator(); it.hasNext();) {
			final String expValue = it.next().toString();
			JSONObject obj = new JSONObject();
			obj.put("string", expValue);

			JsonHandler handler = mockJsonHandler3(expValue);
			JsonParser parser = new JsonParser(handler);
			parser.parse(obj.toString());
		}
	}

	public void testNullValue() throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("null", null);

		JsonHandler handler = mockJsonHandler2();
		JsonParser parser = new JsonParser(handler);
		parser.parse(obj.toString());
	}

	public void testArray() throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("array", Arrays.asList(new String[] { "a", "b", "c" }));

		JsonHandler handler = new DummyJsonHandler() {
			boolean arrayStarted = false;
			int index = 0;

			public void key(String key) {
				assertEquals("array", key);
			}

			public void array() {
				assertFalse(arrayStarted);
				arrayStarted = true;
			}

			public void endArray() {
				assertTrue(arrayStarted);
			}

			public void value(long value) {
				fail();
			}

			public void value(double value) {
				fail();
			}

			public void value(boolean value) {
				fail();
			}

			public void value(String value) {
				assertTrue(arrayStarted);
				switch (index) {
				case 0:
					assertEquals("a", value);
					break;
				case 1:
					assertEquals("b", value);
					break;
				case 2:
					assertEquals("c", value);
					break;
				default:
					fail();
				}
				index++;
			}
		};
		JsonParser parser = new JsonParser(handler);
		parser.parse(obj.toString());
	}

	public void testLongArray() throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("longarray", Arrays.asList(new Long[] { new Long(123), new Long(3456), new Long(45367) }));

		JsonHandler handler = new DummyJsonHandler() {
			boolean arrayStarted = false;
			int index = 0;

			public void key(String key) {
				assertEquals("longarray", key);
			}

			public void array() {
				assertFalse(arrayStarted);
				arrayStarted = true;
			}

			public void endArray() {
				assertTrue(arrayStarted);
			}

			public void value(long value) {
				assertTrue(arrayStarted);
				switch (index) {
				case 0:
					assertEquals(123, value);
					break;
				case 1:
					assertEquals(3456, value);
					break;
				case 2:
					assertEquals(45367, value);
					break;
				default:
					fail();
				}
				index++;
			}

			public void value(double value) {
				fail();
			}

			public void value(boolean value) {
				fail();
			}

			public void value(String value) {
				fail();
			}
		};
		JsonParser parser = new JsonParser(handler);
		parser.parse(obj.toString());
	}

	public void testParser2() throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("obj1", getSimpleObj("bla"));
		obj.put("obj2", getSimpleObj("blu"));
		obj.put("obj3", getSimpleObj("bli"));

		// TODO validate output. use specific handler ext.
		JsonParser parser = new JsonParser(handler);
		parser.parse(obj.toString());
	}

	public void testParser4() throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("arr1", getSimpleArray(new String[] { "s", "t", "r" }));
		obj.put("arr2", getSimpleArray(new String[] { "s", "t", "r" }));
		obj.put("arr3", getSimpleArray(new String[] { "s", "t", "r" }));
		obj.put("arr4", getSimpleArray(new String[] { "s", "t", "r" }));

		// TODO validate output. use specific handler ext.
		JsonParser parser = new JsonParser(handler);
		parser.parse(obj.toString());
	}

	public void testParser5() throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("arr1", getSimpleArray(
				new JSONObject[] { getSimpleObj(new Integer(1)), getSimpleObj("abc"), getSimpleObj(Boolean.TRUE) }));
		obj.put("objvalue", getSimpleObj(getSimpleArray(new Object[] { "a", new Double(2.3), Boolean.FALSE })));
		obj.put("arrarr",
				getSimpleArray(new Object[] { getSimpleArray(new Object[] { "a", new Double(2.3), Boolean.FALSE }) }));
		obj.put("simplv", Boolean.TRUE);

		// TODO validate output. use specific handler ext.
		JsonParser parser = new JsonParser(handler);
		parser.parse(obj.toString());
	}

	public void testParser6() throws Exception {
		final String expKey = "prop1";
		final String expValue = "Any string containing comma, period. question mark?";

		JsonHandler handler = mockJsonHandler1(expValue, expKey);

		String str = "{\"" + expKey + "\":\"" + expValue + "\"}";

		JsonParser parser = new JsonParser(handler);
		parser.parse(str);
	}

	public void testParseEmptyObject() throws Exception {
		JsonHandler handler = new DummyJsonHandler() {
			private int objectCnt = 0;

			public void object() {
				objectCnt++;
			}

			public void endObject() {
				assertEquals(1, objectCnt);
			}

			public void array() {
				fail();
			}

			public void endArray() {
				fail();
			}

			public void key(String key) {
				fail();
			}

			public void value(String value) {
				fail();
			}

			public void value(long value) {
				fail();
			}

			public void value(double value) {
				fail();
			}

			public void value(boolean value) {
				fail();
			}
		};
		JsonParser parser = new JsonParser(handler);
		parser.parse("{}");
	}

	public void testParseEmptyObjectValue() throws Exception {

		List l = new ArrayList();
		l.add("{\"a\":{},\"b\":{},\"c\":{}}");
		l.add("{\"a\":{\"b\":{\"c\":{}}}}");
		l.add("{\"a\":{},\"b\":{\"c\":{}}}");
		l.add("{\"a\":{\"b\":{},\"c\":{}}}");

		for (Iterator it = l.iterator(); it.hasNext();) {
			JsonHandler handler = new DummyJsonHandler() {
				private int objectCnt = 0;

				public void object() {
					objectCnt++;
				}

				public void endObject() {
					assertFalse(objectCnt > 4);
				}

				public void array() {
					fail();
				}

				public void endArray() {
					fail();
				}

				public void value(String value) {
					fail();
				}

				public void value(long value) {
					fail();
				}

				public void value(double value) {
					fail();
				}

				public void value(boolean value) {
					fail();
				}
			};

			JsonParser parser = new JsonParser(handler);
			parser.parse(it.next().toString());
		}
	}

	private static JSONObject getObj() {
		JSONObject obj = new JSONObject();
		obj.put("boolean", true);
		obj.put("long", 1);
		obj.put("double", 1235674.342424);
		obj.put("array", Arrays.asList(new String[] { "a", "b", "c" }));
		obj.put("longarray", Arrays.asList(new Long[] { new Long(123), new Long(3456), new Long(45367) }));
		obj.put("string", "abc");
		obj.put("string1", "123.456");
		return obj;
	}

	private static JSONObject getSimpleObj(Object value) {
		JSONObject obj = new JSONObject();
		obj.put("v", value);
		return obj;
	}

	private static JSONArray getSimpleArray(Object[] values) {
		JSONArray arr = new JSONArray();
		for (int i = 0; i < values.length; i++) {
			arr.add(values[i]);
		}
		return arr;
	}

	// --------------------------------------------------------------------------
	/**
	 * Dummy handler impl that does nothing.
	 */
	private class DummyJsonHandler implements JsonHandler {

		public void object() {
		}

		public void endObject() {
		}

		public void array() {
		}

		public void endArray() {
		}

		public void key(String key) {
		}

		public void value(String value) {
		}

		public void value(long value) {
		}

		public void value(double value) {
		}

		public void value(boolean value) {
		}
	}
}