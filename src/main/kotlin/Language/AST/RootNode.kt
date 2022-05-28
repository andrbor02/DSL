package Language.AST

class RootNode: ExpressionNode() {
    val codeStrings = ArrayList<ExpressionNode>()

    fun addNode(node: ExpressionNode) {
        codeStrings.add(node)
    }
}