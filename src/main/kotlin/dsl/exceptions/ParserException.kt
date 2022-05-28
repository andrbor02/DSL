package dsl.exceptions

class ParserException(message: String?): Exception(message) {
    override val message: String
        get() = "Parser Exception: ${super.message}"
}