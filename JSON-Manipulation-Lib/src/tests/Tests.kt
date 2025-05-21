package tests

import json.converter.convert
import json.models.*
import json.models.JsonArray.Constructor.jsonArrayOf
import json.models.JsonObject.Constructor.jsonObjectOf
import json.rest.Server
import org.junit.jupiter.api.Assertions.assertEquals
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
        val jsonArray = jsonArrayOf(
            JsonBoolean(true),
            JsonNumber(256),
            JsonString("test"),
            JsonNull()
        )

        assertEquals(jsonArray.size(), 4)

        val bool: JsonBoolean = jsonArray[0] as JsonBoolean
        val number: JsonNumber = jsonArray[1] as JsonNumber
        val string: JsonString = jsonArray[2] as JsonString
        val nulll: JsonNull = jsonArray[3] as JsonNull

        assertEquals(bool.value, true)
        assertEquals(number.value, 256)
        assertEquals(string.value, "test")
        assertEquals(nulll.value, null)
    }

    @Test
    fun jsonObjectTest() {
        val jsonObject = jsonObjectOf(
            "boolean" to JsonBoolean(true),
            "number" to JsonNumber(256),
            "string" to JsonString("test"),
            "null" to JsonNull()
        )

        val bool: JsonBoolean = jsonObject["boolean"] as JsonBoolean
        val number: JsonNumber = jsonObject["number"] as JsonNumber
        val string: JsonString = jsonObject["string"] as JsonString
        val nulll: JsonNull = jsonObject["null"] as JsonNull

        assertEquals(bool.value, true)
        assertEquals(number.value, 256)
        assertEquals(string.value, "test")
        assertEquals(nulll.value, null)
    }

    @Test
    fun jsonArrayMapTest() {
        val json = jsonArrayOf(
            JsonNumber(10),
            JsonNumber(0.5),
            JsonString("user")
        )

        val mapped = json.map {
            if (it is JsonNumber)
                JsonNumber(it.value.toDouble() + 1)
            else
                it
        }

        val number1: JsonNumber = mapped[0] as JsonNumber
        val number2: JsonNumber = mapped[1] as JsonNumber
        val string: JsonString = mapped[2] as JsonString

        assertEquals(11.0, number1.value)
        assertEquals(1.5, number2.value)
        assertEquals("user", string.value)
    }

    @Test
    fun jsonArrayFilterTest() {
        val json = jsonArrayOf(
            JsonString("Alice"),
            JsonNumber(30.0),
            JsonBoolean(true),
            jsonObjectOf(
                "number" to JsonNumber(10),
                "string_a" to JsonString("dev"),
                "string_b" to JsonString("user")
            ),
            JsonNull()
        )

        val filteredExpected = jsonArrayOf(
            jsonObjectOf(
                "number" to JsonNumber(10),
                "string_a" to JsonString("dev"),
                "string_b" to JsonString("user")
            )
        )

        val filtered = json.filter { element -> element is JsonObject }

        assertEquals(filteredExpected, filtered)
    }

    @Test
    fun jsonObjectFilterTest() {
        val json = jsonObjectOf(
            "name" to JsonString("Alice"),
            "age" to JsonNumber(30.0),
            "active" to JsonBoolean(true),
            "tags" to jsonArrayOf(
                JsonNumber(10),
                JsonString("dev"),
                JsonString("user")
            ),
            "note" to JsonNull()
        )

        val filteredExpected = jsonObjectOf(
            "tags" to jsonArrayOf(
                JsonNumber(10),
                JsonString("dev"),
                JsonString("user")
            )
        )

        val filtered = json.filter { key, value -> value is JsonArray }

        assertEquals(filteredExpected, filtered)
    }

    @Test
    fun jsonArrayCheckTypesTest() {
        val testA = jsonObjectOf(
            "ids" to jsonArrayOf(
                JsonNumber(1),
                JsonNumber(2),
                JsonNumber(3),
            ),
            "tags" to jsonArrayOf(
                JsonNumber(10),
                JsonString("dev"),
                JsonString("user")
            )
        )

        val testB = jsonArrayOf(
            jsonArrayOf(
                JsonNumber(1),
                JsonNumber(2),
                JsonNumber(3),
            ),
            jsonArrayOf(
                JsonNumber(10),
                JsonString("dev"),
                JsonString("user")
            )
        )

        val testC = jsonObjectOf(
            "ids" to jsonArrayOf(
                JsonNumber(1),
                JsonNumber(2),
                JsonNumber(3),
            ),
            "tags" to jsonArrayOf(
                JsonString("user"),
                JsonString("dev"),
                JsonString("user")
            )
        )

        val testD = jsonArrayOf(
            jsonArrayOf(
                JsonNumber(1),
                JsonNumber(2),
                JsonNumber(3),
            ),
            jsonArrayOf(
                JsonString("admin"),
                JsonString("dev"),
                JsonString("user")
            )
        )

        val testE = jsonArrayOf(
            jsonArrayOf(
                JsonNumber(1),
                JsonNumber(2),
                JsonNumber(3),
            ),
            jsonArrayOf(
                jsonArrayOf(
                    JsonNumber(10),
                ),
                jsonArrayOf(
                    JsonString("admin")
                )
            )
        )

        val testF = jsonArrayOf(
            jsonArrayOf(
                JsonNumber(1),
                JsonNumber(2),
                JsonNumber(3),
            ),
            jsonArrayOf(
                jsonArrayOf(
                    JsonNumber(10),
                    jsonObjectOf()
                ),
                jsonArrayOf(
                    JsonString("admin"),
                    jsonObjectOf()
                )
            )
        )

        assertEquals(testA.checkArrayTypes(), false)
        assertEquals(testB.checkArrayTypes(), false)
        assertEquals(testC.checkArrayTypes(), true)
        assertEquals(testD.checkArrayTypes(), true)
        assertEquals(testE.checkArrayTypes(), true)
        assertEquals(testF.checkArrayTypes(), false)

    }

    @Test
    fun serializeTest() {
        val jsonObject = jsonObjectOf(
            "name" to JsonString("Alice"),
            "age" to JsonNumber(30.0),
            "active" to JsonBoolean(true),
            "tags" to jsonArrayOf(
                JsonString("dev"),
                JsonString("user")
            ),
            "note" to JsonNull()
        )
        val jsonArray = jsonArrayOf(
            JsonNumber(1),
            JsonNumber(2),
            JsonNumber(3)
        )

        val stringObject = """{
            |"name":"Alice",
            |"age":30.0,
            |"active":true,
            |"tags":["dev","user"],
            |"note":null
        |}""".trimMargin().replace("\n", "")

        val stringArray = """[
            |1,2,3
        |]""".trimMargin().replace("\n", "")

        assertEquals(stringObject, jsonObject.serialize())
        assertEquals(stringArray, jsonArray.serialize())
    }

    @Test
    fun jsonObjectEquals() {
        val json1 = jsonObjectOf(
            "name" to JsonString("Alice"),
            "age" to JsonNumber(30.0),
            "active" to JsonBoolean(true),
            "tags" to jsonArrayOf(
                JsonString("dev"),
                JsonString("user")
            ),
            "note" to JsonNull()
        )
        val json2 = jsonObjectOf(
            "name" to JsonString("Alice"),
            "age" to JsonNumber(30.0),
            "active" to JsonBoolean(true),
            "tags" to jsonArrayOf(
                JsonString("dev"),
                JsonString("user")
            ),
            "note" to JsonNull()
        )

        assertEquals(json1, json2)
    }

    @Test
    fun jsonConvertionTest() {
        val course = Course(
            "PA", 6, listOf(
                EvalItem("quizzes", .2, false, null),
                EvalItem("project", .8, true, EvalType.PROJECT)
            )
        )

        val json = jsonObjectOf(
            "name" to JsonString("PA"),
            "credits" to JsonNumber(6),
            "evaluation" to jsonArrayOf(
                jsonObjectOf(
                    "name" to JsonString("quizzes"),
                    "percentage" to JsonNumber(.2),
                    "mandatory" to JsonBoolean(false),
                    "type" to JsonNull()
                ),
                jsonObjectOf(
                    "name" to JsonString("project"),
                    "percentage" to JsonNumber(.8),
                    "mandatory" to JsonBoolean(true),
                    "type" to JsonString("PROJECT")
                )
            )
        )

        val converted = convert(course)

        assertEquals(json, converted)
    }

    @Test
    fun jsonControllerTest() {
        val server = Server(Controller())
        server.start()

        val responses = mutableListOf<String>()
        val expectedResponses = mutableListOf<String>()

        responses.add(getJsonString("http://localhost:8080/api/ints"))
        expectedResponses.add(jsonArrayOf(
            JsonNumber(1),
            JsonNumber(2),
            JsonNumber(3)
        ).serialize())

        responses.add(getJsonString("http://localhost:8080/api/pair"))
        expectedResponses.add(jsonObjectOf(
            "first" to JsonString("um"),
            "second" to JsonString("dois")
        ).serialize())

        responses.add(getJsonString("http://localhost:8080/api/path/test-string"))
        expectedResponses.add(JsonString("test-string"+"!").serialize())

        responses.add(getJsonString("http://localhost:8080/api/args?n=4&text=teste"))
        expectedResponses.add(jsonObjectOf(
            "teste" to JsonString("teste"+"teste"+"teste"+"teste")
        ).serialize())

        responses.add(getJsonString("http://localhost:8080/api/complex/test-string/3.35/false"))
        expectedResponses.add(JsonString("test-string"+"3.35"+"false"+"!").serialize())

        server.stop()

        expectedResponses.forEachIndexed { i, expected ->
            assertEquals(expected, responses[i])
        }
    }

    @Test
    fun jsonCourseControllerTest() {
        val courses = listOf(
            Course(
            "PA", 6, listOf(
                EvalItem("quizzes", .2, false, null),
                EvalItem("project", .8, true, EvalType.PROJECT)
            )),
            Course(
            "IAR", 6, listOf(
                EvalItem("exam", .1, false, EvalType.EXAM),
                EvalItem("project", .9, true, EvalType.PROJECT)
            )),
        )

        val server = Server(CourseController(courses))
        server.start()

        val responses = mutableListOf<String>()
        val expectedResponses = mutableListOf<String>()

        responses.add(getJsonString("http://localhost:8080/courses/courseId/0"))
        expectedResponses.add("""{
            |"credits":6,
            |"evaluation":[
                |{"mandatory":false,"name":"quizzes","percentage":0.2,"type":null},
                |{"mandatory":true,"name":"project","percentage":0.8,"type":"PROJECT"}
            |],
            |"name":"PA"
        |}""".trimMargin().replace("\n", ""))

        responses.add(getJsonString("http://localhost:8080/courses/courseId/1"))
        expectedResponses.add("""{
            |"credits":6,
            |"evaluation":[
                |{"mandatory":false,"name":"exam","percentage":0.1,"type":"EXAM"},
                |{"mandatory":true,"name":"project","percentage":0.9,"type":"PROJECT"}
            |],
            |"name":"IAR"
        |}""".trimMargin().replace("\n", ""))

        server.stop()

        expectedResponses.forEachIndexed { i, expected ->
            assertEquals(expected, responses[i])
        }
    }
}