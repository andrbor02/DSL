package Language

import java.util.regex.Pattern

class Lexer(val code: String) {
    private var position = 0
    var tokenList = mutableListOf<Token>()

    fun lex(): List<Token> {
        while (nextToken()) { }

        return tokenList
    }

    fun nextToken(): Boolean {
        if (position >= code.length) {
            return false
        }
        val tokenTypesList = TokenTypesList.values()

        for (i in 0 until tokenTypesList.size) {
            val tokenType = tokenTypesList[i].tokenType
            val regex = tokenType.regex
            val matcher = Pattern.compile(regex).matcher(code)
//            println(matcher.group().toString())
            if (matcher.find(position) && matcher.start() == position) {
                code.substring(position + matcher.group().length)
                val token = Token(tokenType, matcher.group(), position)
                position += matcher.group().length
                if (tokenType.name != TokenTypesList.MARKING.name) {
                    tokenList.add(token)
                }
                return true
            }
        }
        throw Exception("Error on $position character")
    }
}





















