package dsl.exceptions

class InterpreterException(message: String?) : Exception(message) {
    override val message: String
        get() = "Interpreter Exception: ${super.message}"
}