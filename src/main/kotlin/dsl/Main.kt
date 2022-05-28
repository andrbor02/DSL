import dsl.Interpreter
import dsl.Lexer
import dsl.Parser

fun main(args: Array<String>) {

    val code = """
        p = 5;
        WHILE p MORE 1 {
        LOG> p;
        p = p - 1;
        };
        IF 6 - 2 MORE p {
        LOG> 1 + 2 * (2 - 4);
        };
    """.trimIndent()

    println(code)
    val lexer = Lexer(code)
    val list = lexer.lex()

    for (token in list) {
        println(token)
    }

    val parser = Parser(list)
    val rootNode = parser.parse()

    val interpreter = Interpreter()
    for (i in 0 until rootNode.codeStrings.size) {
        interpreter.interpret(rootNode.codeStrings[i])
    }
}