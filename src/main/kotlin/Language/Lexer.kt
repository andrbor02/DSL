package Language

import Language.Exceptions.LexerException
import java.util.regex.Pattern

class Lexer(private val code: String) {
    private var position = 0
    private var tokenList = mutableListOf<Token>()

    fun lex(): List<Token> {
        println(code)
        nextToken()

        return tokenList
    }

    private fun nextToken() {
        if (position >= code.length) {
            return
        }
        val tokenTypesList = TokenTypesList.values()

        for (token in tokenTypesList) {
            val tokenType = token.tokenType
            val regex = tokenType.regex
            val matcher = Pattern.compile(regex).matcher(code)

            if (matcher.find(position) && matcher.start() == position) {
                if (tokenType.name != TokenTypesList.MARKING.name) {
                    val newToken = Token(tokenType, matcher.group(), position)
                    tokenList.add(newToken)
                }

                code.substring(position + matcher.group().length)
                position += matcher.group().length

                return nextToken()
            }
        }
        throw LexerException("Unknown symbol on $position position")
    }
}





















