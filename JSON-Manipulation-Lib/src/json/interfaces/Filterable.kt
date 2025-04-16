package json.interfaces

interface Filterable {
    abstract fun filter(filter: () -> Boolean)
}