package dsl.ast

import dsl.Token

class IfNode(
    val operator: Token,
    val leftNode: ExpressionNode,
    val rightNode: ExpressionNode
) : ExpressionNode() {
    val thenOperations = ArrayList<ExpressionNode>()
    val elseOperations = ArrayList<ExpressionNode>()

    fun addThenOperations(operation: ExpressionNode) {
        thenOperations.add(operation)
    }

    fun addElseOperations(operation: ExpressionNode) {
        elseOperations.add(operation)
    }
}