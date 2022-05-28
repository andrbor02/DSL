import Language.*
import Language.AST.BinaryOperatorNode
import Language.AST.NumberNode
import Language.AST.UnaryOperationNode

fun main(args: Array<String>) {

    val code = """
        p = 5;
        WHILE p MORE 1 {
        LOG> p;
        p = p - 1;
        };
        IF 6 - 2 LESS p {
        LOG> 1 + 2 * (2 - 4);
        };
    """.trimIndent()

    val lexer = Lexer(code)
    val list = lexer.lex()

    for (token in list) {
        println(token)
    }

    val parser = Parser(list)
    val rootNode = parser.parse()

//    for (string in rootNode.codeStrings) {
//        val a = string as UnaryOperationNode
//        //println(a.operator.type.name)
//        val b = a.operand as BinaryOperatorNode
//        println((b.leftNode as NumberNode).number.value)
//        println(b.operator)
//        println(b.rightNode)
//    }


    val interpreter = Interpreter()
    //interpreter.interpret(rootNode)
    for (i in 0 until rootNode.codeStrings.size) {
        interpreter.interpret(rootNode.codeStrings[i])
    }
}