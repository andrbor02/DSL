package Language

import java.util.regex.Pattern

class TokenType(val name: String, val regex: String) {
    override fun toString(): String {
        return name
    }
}

enum class TokenTypesList (val tokenType: TokenType) {
    NUMBER(TokenType("NUMBER", "0|([1-9][0-9]*)")),
    VARIABLE(TokenType("VARIABLE", "[a-z]+")),

    STRINGEND(TokenType("STRINGEND", ";")),
    MARKING(TokenType("MARKING", "\\s")),
    ASSIGN(TokenType("ASSIGN", "=")),
    LOGGER(TokenType("LOGGER", "LOG>")),

    LBRACKET(TokenType("LBRACKET", "\\(")),
    RBRACKET(TokenType("RBRACKET", "\\)")),
    LBRACE(TokenType("LBRACE", "\\{")),
    RBRACE(TokenType("RBRACE", "\\}")),

    LESS(TokenType("LESS", "LESS")),
    MORE(TokenType("MORE", "MORE")),
    EQUAL(TokenType("EQUAL", "EQUAL")),

    PLUS(TokenType("PLUS", "\\+")),
    MINUS(TokenType("MINUS", "-")),
    DIVIDE(TokenType("DIVIDE", "%")),
    MULTIPLY(TokenType("MULTIPLY", "\\*")),

    IF(TokenType("IF", "^IF")),
    ELSE(TokenType("ELSE", "^ELSE")),
    FOR(TokenType("FOR", "^FOR")),
    WHILE(TokenType("WHILE", "^WHILE"))

//    ADD(TokenType("ADD", "ADD")),
//    GET(TokenType("GET", "GET")),
//    REMOVE(TokenType("CONTAINS", "CONTAINS"))
}