package tests

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*;
import json.*

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
        assertEquals("Alice", (json.getProperty("name") as JsonString).value)
        assertEquals(30.0, (json.getProperty("age") as JsonNumber).value)
        assertEquals(true, (json.getProperty("active") as JsonBoolean).value)
        val tags = json.getProperty("tags") as JsonArray
        assertEquals("dev", (tags.getProperty(0) as JsonString).value)
        assertEquals("user", (tags.getProperty(1) as JsonString).value)
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
        val filteredObject = json.filter{k, _ -> k == "tags"}
        val tagsObject = filteredObject.getProperty("tags") as JsonArray
        assertEquals(10, (tagsObject.getProperty(0) as JsonNumber).value)
        assertEquals("dev", (tagsObject.getProperty(1) as JsonString).value)
        assertEquals("user", (tagsObject.getProperty(2) as JsonString).value)

        val filteredArray = tagsObject.filter{ it is JsonString }
        assertEquals("dev", (filteredArray.getProperty(0) as JsonString).value)
        assertEquals("user", (filteredArray.getProperty(1) as JsonString).value)

    }
}