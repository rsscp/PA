package json.models

sealed class JsonContainer: JsonElement() {
    /**
     * Checks if all elements of a JsonArray contain JsonElements of the same type and makes the same check for itself in the case this object is also of type JsonArray
     *
     * @return True if each contained array is composed of JsonElements of the same type and the same for this object if it is also a JsonArray
     */
    fun checkArrayTypes(): Boolean {
        var result = true
        accept {
            if (it is JsonArray)
                result = result && it.isSameType()
        }
        return result
    }
}