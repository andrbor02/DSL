package Language.Exceptions

class LexerException(message: String?) : Exception(message) {
    override val message: String?
        get() = "Lexer Exception: ${super.message}"
}