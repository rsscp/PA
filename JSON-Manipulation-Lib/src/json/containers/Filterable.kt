package json.containers

interface Filterable<T, U> {
    abstract fun filter(filter: (U) -> Boolean): T
}