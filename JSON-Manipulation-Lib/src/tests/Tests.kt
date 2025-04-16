package tests

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*;
import json.containers.JsonArray
import json.containers.JsonObject
import json.primitives.JsonBoolean
import json.primitives.JsonNull
import json.primitives.JsonNumber
import json.primitives.JsonString

class Tests {
/*
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
        assertEquals("Alice", (json.getProperty("name") as JsonString).value)
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
    */
}