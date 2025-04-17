package tests

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*;
import json.containers.*
import json.primitives.*

class Tests {

    @Test
    fun contentTest() {
        val json = JsonObject(
            mutableMapOf(
                "name" to JsonString("Alice"),
                "age" to JsonNumber(30.0),
                "active" to JsonBoolean(true),
                "tags" to JsonArray(mutableListOf(JsonString("dev"), JsonString("user"))),
                "note" to JsonNull()
            )
        )
        assertEquals("Alice", (json.getProperty("name") as JsonString).getPrimitiveValue())
        assertEquals(30.0, (json.getProperty("age") as JsonNumber).getPrimitiveValue())
        assertEquals(true, (json.getProperty("active") as JsonBoolean).getPrimitiveValue())
        val tags = json.getProperty("tags") as JsonArray
        assertEquals("dev", (tags.getProperty(0) as JsonString).getPrimitiveValue())
        assertEquals("user", (tags.getProperty(1) as JsonString).getPrimitiveValue())
        assertTrue(json.getProperty("note") is JsonNull)
    }


    @Test
    fun serializeTest() {
        val json = JsonObject(
            mutableMapOf(
                "name" to JsonString("Alice"),
                "age" to JsonNumber(30.0),
                "active" to JsonBoolean(true),
                "tags" to JsonArray(mutableListOf(JsonString("dev"), JsonString("user"))),
                "note" to JsonNull()
            )
        )
        assertEquals("{name: \"Alice\", age: 30.0, active: true, tags: [\"dev\", \"user\"], note: null}", json.toString())
    }

    @Test
    fun filterTest() {
        val json = JsonObject(
            mutableMapOf(
                "name" to JsonString("Alice"),
                "age" to JsonNumber(30.0),
                "active" to JsonBoolean(true),
                "tags" to JsonArray(mutableListOf(JsonNumber(10), JsonString("dev"), JsonString("user"))),
                "note" to JsonNull()
            )
        )
        val filteredObject = json.filter{kv -> kv.first == "tags"}
        val tagsObject = filteredObject.getProperty("tags") as JsonArray
        assertEquals(10, (tagsObject.getProperty(0) as JsonNumber).getPrimitiveValue())
        assertEquals("dev", (tagsObject.getProperty(1) as JsonString).getPrimitiveValue())
        assertEquals("user", (tagsObject.getProperty(2) as JsonString).getPrimitiveValue())

        val filteredArray = tagsObject.filter{ it is JsonString }
        assertEquals("dev", (filteredArray.getProperty(0) as JsonString).getPrimitiveValue())
        assertEquals("user", (filteredArray.getProperty(1) as JsonString).getPrimitiveValue())

    }
}