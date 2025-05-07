package tests

import json.containers.JsonArray
import json.containers.JsonObject
import json.primitives.JsonBoolean
import json.primitives.JsonNull
import json.primitives.JsonNumber
import json.primitives.JsonString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class Tests {

    @Test
    fun primitivesTest() {
        val jsonBoolean = JsonBoolean(true)
        val jsonNumber = JsonNumber(256)
        val jsonString = JsonString("test")
        val jsonNull = JsonNull()

        assertEquals(jsonBoolean.value, true)
        assertEquals(jsonNumber.value, 256)
        assertEquals(jsonString.value, "test")
        assertEquals(jsonNull.value, null)
    }

    @Test
    fun jsonArrayTest() {
        val jsonArray = JsonArray(
            JsonBoolean(true),
            JsonNumber(256),
            JsonString("test"),
            JsonNull()
        )

        assertEquals(jsonArray.size(), 4)

        val bool: JsonBoolean? = jsonArray[0]
        val number: JsonNumber? = jsonArray[1]
        val string: JsonString? = jsonArray[2]
        val nulll: JsonNull? = jsonArray[3]

        assertEquals(bool?.value, true)
        assertEquals(number?.value, 256)
        assertEquals(string?.value, "test")
        assertEquals(nulll?.value, null)
    }

    @Test
    fun jsonObjectTest() {
        val jsonObject = JsonObject(
            "boolean" to JsonBoolean(true),
            "number" to JsonNumber(256),
            "string" to JsonString("test"),
            "null" to JsonNull()
        )

        val bool: JsonBoolean? = jsonObject["boolean"]
        val number: JsonNumber? = jsonObject["number"]
        val string: JsonString? = jsonObject["string"]
        val nulll: JsonNull? = jsonObject["null"]

        assertEquals(bool?.value, true)
        assertEquals(number?.value, 256)
        assertEquals(string?.value, "test")
        assertEquals(nulll?.value, null)
    }

    @Test
    fun jsonArrayMapTest() {
        val jsonArray = JsonArray(
            JsonBoolean(true),
            JsonNumber(256),
            JsonString("test"),
            JsonNull()
        )
    }

    @Test
    fun filterTest() {
        val json = JsonObject(
            "name" to JsonString("Alice"),
            "age" to JsonNumber(30.0),
            "active" to JsonBoolean(true),
            "tags" to JsonArray(
                JsonNumber(10),
                JsonString("dev"),
                JsonString("user")
            ),
            "note" to JsonNull()
        )

        val filteredObject = json.filter{ property -> property.getKey() == "tags" }
        val tagsNullable: JsonArray? = filteredObject["tags"]

        assertNotNull(tagsNullable)
        val tags: JsonArray = tagsNullable!!

        assertEquals(tags.size(), 3)

        val number: JsonNumber? = tags[0]
        val dev1: JsonString? = tags[1]
        val user1: JsonString? = tags[2]

        assertEquals(10, number?.value)
        assertEquals("dev", dev1?.value)
        assertEquals("user", user1?.value)

        val filteredArray = tags.filter{ it is JsonString }

        assertEquals(filteredArray.size(), 2)

        val dev2: JsonString? = filteredArray[0]
        val user2: JsonString? = filteredArray[1]

        assertEquals("dev", dev2?.value)
        assertEquals("user", user2?.value)

    }

    @Test
    fun mappingTest() {
        val json = JsonArray(
            JsonNumber(10),
            JsonNumber(0.5),
            JsonString("user")
        )

        val mappedArray = json.map {
            if(it is JsonNumber){
                JsonNumber(it.value.toDouble() + 1)
            } else it
        }

        val number1: JsonNumber? = mappedArray[0]
        val number2: JsonNumber? = mappedArray[1]
        val string: JsonString? = mappedArray[2]

        assertEquals(11.0, number1?.value)
        assertEquals(1.5, number2?.value)
        assertEquals("user", string?.value)
    }






/*


    @Test
    fun contentTest() {
        val json = JsonObject(
            mutableMapOf(
                "name" to JsonString("Alice"),
                "age" to JsonNumber(30),
                "active" to JsonBoolean(true),
                "tags" to JsonArray(mutableListOf(
                    JsonString("dev"),
                    JsonString("user")
                )),
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
        assertEquals("{\"name\": \"Alice\", \"age\": 30.0, \"active\": true, \"tags\": [\"dev\", \"user\"], \"note\": null}", json.toString())
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
        val filteredObject = json.filter{ property -> property.getKey() == "tags" }
        val tagsObject = filteredObject.getProperty("tags") as JsonArray
        assertEquals(10, (tagsObject.getProperty(0) as JsonNumber).getPrimitiveValue())
        assertEquals("dev", (tagsObject.getProperty(1) as JsonString).getPrimitiveValue())
        assertEquals("user", (tagsObject.getProperty(2) as JsonString).getPrimitiveValue())

        val filteredArray = tagsObject.filter{ it is JsonString }
        assertEquals("dev", (filteredArray.getProperty(0) as JsonString).getPrimitiveValue())
        assertEquals("user", (filteredArray.getProperty(1) as JsonString).getPrimitiveValue())

    }
    */
}