package dsl

class Token(val type: TokenType, val value: String, val position: Int) {
    override fun toString(): String {
        return "Type: $type, Value: $value, Position: $position"
    }
}