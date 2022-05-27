import Language.*
import Language.AST.BinaryOperatorNode
import Language.AST.NumberNode
import Language.AST.UnaryOperationNode

fun main(args: Array<String>) {

//    val code = """
//        p = 5;
//        g = 10;
//        IF;
//        LOG> p + g * 10 - 7;
//    """.trimIndent()

    val code = """
        IF 4 < 5 {
        LOG> 10 - 7 * 7 + 30;
        }
        LOG> 5;
    """.trimIndent()


//    IF a < b {
//      LOG> "IF works properly"
//    }
//        LINKEDLIST ADD p;
//        LINKEDLIST ADD g;
//        LOG> LINKEDLIST GET 0;
//        LOG> LINKEDLIST GET 0 % LINKEDLIST GET 1;

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