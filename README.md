# PA
This library aids in generating and handling all supported value types for Json: Object, Array,
Number, Boolean, String, Null

To create a primitive ex. JsonNumber

    val jsonNumber = JsonNumber(256)
note: in order for the Json to be valid it must be inside a JsonObject

---

To create a JsonObject from a supported type Element using jsonObjectOf()

    val json = jsonObjectOf(
        "key1" to JsonElement(v:Element),
        "key2" to JsonElement(v:Element)
    )

note: for Number JsonElement(v) will be JsonNumber(v) and "key" can be any string
jsonObjectOf(), and jsonObjectOf() can also use Iterable<Pair<String, JsonElement>> or Map<String, JsonElement>

---


To filter an JsonObject or JsonArray you use .filter(expression)

    val filtered = json.filter { _, value -> value is JsonNumber }

note:in this case filtered will return the JsonObject with the key/value pairs that have a value::JsonNumber

---


To perform a mapping function on a JsonArray you use .map()

    val mapped = json.map {
        if (it is JsonNumber)
            JsonNumber(it.value.toDouble() + 1)
        else
            it
    }
note: this specific function will increment any JsonNumber in the Array

---


To check if all JsonArrays contain values of the same type

    val isSame = json.checkArrayTypes()

---


To print the JsonObject as a string use

    json.serialize()

