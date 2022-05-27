package Language.AST

import Language.Token

class BinaryOperatorNode(
    val operator: Token,
    val leftNode: ExpressionNode,
    val rightNode: ExpressionNode
    ): ExpressionNode() {
}