package Language.AST

class StatementsNode: ExpressionNode() {
    val codeStrings = ArrayList<ExpressionNode>()

    fun addNode(node: ExpressionNode) {
        codeStrings.add(node)
    }
}