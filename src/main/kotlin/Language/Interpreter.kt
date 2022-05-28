package Language

import Language.AST.*
import Language.Exceptions.InterpreterException
import java.util.*

class Interpreter {
    private val variableStorage = HashMap<String, String>()

    fun interpret(node: ExpressionNode): String {
        when (node) {
            is UnaryOperationNode -> unaryOperationHandler(node)
            is BinaryOperatorNode -> return binaryOperatorHandler(node)
            is IfNode -> ifHandler(node)
            is WhileNode -> whileHandler(node)

            is NumberNode -> return node.number.value
            is VariableNode -> return variableStorage[node.variable.value] ?: "VARIABLE ERROR"
        }
        throw InterpreterException("Node at  ${node.toString()}")
    }

    private fun unaryOperationHandler(node: UnaryOperationNode) {
        when (node.operator.type.name) {
            TokenTypesList.LOGGER.tokenType.name -> println(interpret(node.operand))
        }
    }

    private fun binaryOperatorHandler(node: BinaryOperatorNode): String {
        when (node.operator.type.name) {
            TokenTypesList.ASSIGN.tokenType.name -> {
                val result = interpret(node.rightNode)
                val varNode = node.leftNode as VariableNode
                variableStorage.put(varNode.variable.value, result)
                return result
            }

            TokenTypesList.PLUS.tokenType.name,
            TokenTypesList.MINUS.tokenType.name,
            TokenTypesList.MULTIPLY.tokenType.name,
            TokenTypesList.DIVIDE.tokenType.name -> {
                val leftNumber = interpret(node.leftNode).toInt()
                val rightNumber = interpret(node.rightNode).toInt()
                return when (node.operator.type.name) {
                    TokenTypesList.PLUS.tokenType.name -> (leftNumber + rightNumber).toString()
                    TokenTypesList.MINUS.tokenType.name -> (leftNumber - rightNumber).toString()
                    TokenTypesList.DIVIDE.tokenType.name -> (leftNumber / rightNumber).toString()
                    TokenTypesList.MULTIPLY.tokenType.name -> (leftNumber * rightNumber).toString()
                    else -> throw Exception("Unknown math operation")
                }
            }
            else -> throw Exception("ERROR: In else branch of BinaryOperationHandler")
        }
    }

    private fun ifHandler(node: IfNode) {
        val leftVal = interpret(node.leftNode).toInt()
        val rightVal = interpret(node.rightNode).toInt()
        when (node.operator.type) {
            TokenTypesList.LESS.tokenType -> {
                if (leftVal < rightVal) {
                    getThenOperations(node)
                } else {
                    getElseOperations(node)
                }
            }

            TokenTypesList.MORE.tokenType -> {
                if (leftVal > rightVal) {
                    getThenOperations(node)
                } else {
                    getElseOperations(node)
                }
            }

            TokenTypesList.EQUAL.tokenType -> {
                if (leftVal == rightVal) {
                    getThenOperations(node)
                } else {
                    getElseOperations(node)
                }
            }
        }
    }

    private fun getThenOperations(node: IfNode) {
        for (thenOperation in node.thenOperations) {
            interpret(thenOperation)
        }
    }

    private fun getElseOperations(node: IfNode) {
        for (elseOperation in node.elseOperations) {
            interpret(elseOperation)
        }
    }

    private fun whileHandler(node: WhileNode) {
        var leftVal = interpret(node.leftNode).toInt()
        var rightVal = interpret(node.rightNode).toInt()
        when (node.operator.type) {
            TokenTypesList.LESS.tokenType -> {
                while (leftVal < rightVal) {
                    node.operations.forEach { interpret(it) }
                    leftVal = interpret(node.leftNode).toInt()
                    rightVal = interpret(node.rightNode).toInt()
                }
            }

            TokenTypesList.MORE.tokenType -> {
                while (leftVal > rightVal) {
                    node.operations.forEach { interpret(it) }
                    leftVal = interpret(node.leftNode).toInt()
                    rightVal = interpret(node.rightNode).toInt()
                }
            }

            TokenTypesList.EQUAL.tokenType -> {
                while (leftVal == rightVal) {
                    node.operations.forEach { interpret(it) }
                    leftVal = interpret(node.leftNode).toInt()
                    rightVal = interpret(node.rightNode).toInt()
                }
            }
        }
    }
}