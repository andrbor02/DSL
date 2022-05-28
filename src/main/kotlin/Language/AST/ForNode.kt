package Language.AST

import Language.Token

class ForNode(
    val operator: Token,
    val leftNode: ExpressionNode,
    val rightNode: ExpressionNode,
    val action: ExpressionNode
) : ExpressionNode() {
    val operations = ArrayList<ExpressionNode>()

    fun addOperations(operation: ExpressionNode) {
        operations.add(operation)
    }
}