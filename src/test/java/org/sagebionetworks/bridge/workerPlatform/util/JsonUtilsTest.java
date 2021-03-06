package org.sagebionetworks.bridge.workerPlatform.util;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableList;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.sagebionetworks.bridge.json.DefaultObjectMapper;

public class JsonUtilsTest {
    // branch coverage
    @Test
    public void asStringListNullParent() {
        assertNull(JsonUtils.asStringList(null, "any"));
    }

    @DataProvider(name = "asStringListProvider")
    public Object[][] asStringListProvider() {
        // { [input json text], [expected] }
        // property="prop"
        return new Object[][] {
                { "null", null },
                { "\"string\"", null },
                { "{}", null },
                { "{\"prop\":null}", null },
                { "{\"prop\":42}", null },
                { "{\"prop\":[]}", ImmutableList.of() },
                { "{\"prop\":[\"foo\", null]}", null },
                { "{\"prop\":[\"foo\", 42]}", null },
                { "{\"prop\":[\"foo\", \"bar\"]}", ImmutableList.of("foo", "bar") },
        };
    }

    @Test(dataProvider = "asStringListProvider")
    public void asStringList(String inputJsonText, List<String> expected) throws Exception {
        JsonNode parent = DefaultObjectMapper.INSTANCE.readTree(inputJsonText);
        assertEquals(JsonUtils.asStringList(parent, "prop"), expected);
    }

    // branch coverage
    @Test
    public void asTextNullParent() {
        assertNull(JsonUtils.asText(null, "any"));
    }

    @DataProvider(name = "asTextProvider")
    public Object[][] asTextProvider() {
        // { [input json text], [expected] }
        // property="prop"
        return new Object[][] {
                { "null", null },
                { "\"string\"", null },
                { "{}", null },
                { "{\"prop\":null}", null },
                { "{\"prop\":42}", null },
                { "{\"prop\":\"\"}", null },
                { "{\"prop\":\"   \"}", null },
                { "{\"prop\":\"value\"}", "value" },
        };
    }

    @Test(dataProvider = "asTextProvider")
    public void asText(String inputJsonText, String expected) throws Exception {
        JsonNode parent = DefaultObjectMapper.INSTANCE.readTree(inputJsonText);
        assertEquals(JsonUtils.asText(parent, "prop"), expected);
    }
}
