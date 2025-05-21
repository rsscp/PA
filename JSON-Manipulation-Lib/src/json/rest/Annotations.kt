package json.rest


enum class HttpMethod {
    GET, POST, PUT, DELETE
}

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@MustBeDocumented
annotation class Method(val method: HttpMethod)

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@MustBeDocumented
annotation class Mapping(val path: String)

@Target(AnnotationTarget.VALUE_PARAMETER)
@MustBeDocumented
annotation class PathParam

@Target(AnnotationTarget.VALUE_PARAMETER)
@MustBeDocumented
annotation class HeaderParam