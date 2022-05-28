package dsl.ast

import dsl.Token

class UnaryOperationNode(val operator: Token, val operand: ExpressionNode): ExpressionNode()