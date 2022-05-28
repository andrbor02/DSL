package dsl.ast

import dsl.Token

class BinaryOperatorNode(
    val operator: Token,
    val leftNode: ExpressionNode,
    val rightNode: ExpressionNode
    ): ExpressionNode()