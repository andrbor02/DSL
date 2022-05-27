package Language.AST

import Language.Token

class UnaryOperationNode(val operator: Token, val operand: ExpressionNode): ExpressionNode() {
}