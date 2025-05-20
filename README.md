# PA
This is a library capable of converting all the value types: Object, Array,
    Number, Boolean, String, Null, into valid Json

To create a primitive ex. JsonNumber 

    val jsonNumber = JsonNumber(256)
note: in order for the Json to be valid it must be inside a JsonObject

To create a JsonObject from a supported type Element using jsonObjectOf()

    val json = jsonObjectOf(
        "key1" to JsonElement(v:Element),
        "key2" to JsonElement(v:Element)
    )

note: for Number JsonElement(v) will be JsonNumber(v) and "key" can be any string
jsonObjectOf() can also use Iterable<Pair<String, JsonElement>> or Map<String, JsonElement> 


