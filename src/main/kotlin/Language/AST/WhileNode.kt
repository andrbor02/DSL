package Language.AST

import Language.Token

class WhileNode (
    val operator: Token,
    val leftNode: ExpressionNode,
    val rightNode: ExpressionNode
    ) : ExpressionNode() {
        val operations = ArrayList<ExpressionNode>()

        fun addOperations(operation: ExpressionNode) {
            operations.add(operation)
        }
}