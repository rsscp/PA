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
        assertEquals("age", json.getProperty("age"))
    }


    @Test
    fun serialieTest() {
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
}