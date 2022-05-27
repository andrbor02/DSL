package Language

import Language.AST.*

class Parser(val tokensList: List<Token>) {
    private var index = 0

    fun parse(): StatementsNode {
        val root = StatementsNode()
        while (index < tokensList.size) {
            val codeStringNode = parseExpression()
            require(listOf(TokenTypesList.STRINGEND.tokenType))
            root.addNode(codeStringNode)
        }
        return root
    }

    fun match(tokenTypes: List<TokenType>): Token? {
        if (index < tokensList.size) {
            val currentToken = tokensList[index]
            for (tokenType in tokenTypes) {
                if (tokenType == currentToken.type) {
                    index++
                    return currentToken
                }
            }
        }
        return null
    }

    fun require(expected: List<TokenType>) {
        val token = match(expected) ?: throw Exception("Expected ${expected[0]} on position $index")
    }

    fun parseExpression(): ExpressionNode {
        when (tokensList[index].type.name) {
            TokenTypesList.VARIABLE.tokenType.name -> {
                val varNode = parseVariableOrNumber()
                val operator = match(
                    listOf(
                        TokenTypesList.ASSIGN.tokenType
//                        TokenTypesList.ADD.tokenType,
//                        TokenTypesList.GET.tokenType,
//                        TokenTypesList.REMOVE.tokenType
                    )
                )
                if (operator != null) {
                    val rightVal = parseThirdPriority()
                    return BinaryOperatorNode(operator, varNode, rightVal)
                }
                throw Error("Binary operator expected on position:$index")
            }
            TokenTypesList.LOGGER.tokenType.name -> {
                index++
                return UnaryOperationNode(tokensList[index - 1], parseThirdPriority())
            }
            TokenTypesList.IF.tokenType.name -> {
                index++
                return parseIF()
            }
//            TokenTypesList.FOR.tokenType.name -> {
//                index++
//                return parseFor()
//            }
//            TokenTypesList.WHILE.tokenType.name -> {
//                index++
//                return parseWhile()
//            }
//            TokenTypesList.LINKEDLIST.tokenType.name -> {
//                index++
//                return parseLinked()
//            }
            else -> throw Exception("Expected variable or operator on position $index")
        }
    }

    fun parseFirstPriority(): ExpressionNode {
        if (tokensList[index].type == TokenTypesList.LBRACKET.tokenType) {
            index++
            val node = parseThirdPriority()
            require(listOf(TokenTypesList.RBRACKET.tokenType))
            return node
        } else {
            return parseVariableOrNumber()
        }
    }

    fun parseSecondPriority(): ExpressionNode {
        var leftVal = parseFirstPriority()
        var operator = match(
            listOf(
                TokenTypesList.MULTIPLY.tokenType,
                TokenTypesList.DIVIDE.tokenType
            )
        )
        while (operator != null) {
            val rightVal = parseFirstPriority()
            leftVal = BinaryOperatorNode(operator, leftVal, rightVal)
            operator = match(
                listOf(
                    TokenTypesList.MULTIPLY.tokenType,
                    TokenTypesList.DIVIDE.tokenType
                )
            )
        }
        return leftVal
    }

    fun parseThirdPriority(): ExpressionNode {
        var leftValue = parseSecondPriority()
        var operator = match(
            listOf(
                TokenTypesList.PLUS.tokenType,
                TokenTypesList.MINUS.tokenType
            )
        )
        while (operator != null) {
            val rightValue = parseSecondPriority()
            leftValue = BinaryOperatorNode(operator, leftValue, rightValue)
            operator = match(
                listOf(
                    TokenTypesList.PLUS.tokenType,
                    TokenTypesList.MINUS.tokenType
                )
            )
        }
        return leftValue
    }

    fun parseVariableOrNumber(): ExpressionNode { //TODO(refactor DRY)
        if (tokensList[index].type == TokenTypesList.NUMBER.tokenType) {
            index++
            return NumberNode(tokensList.get(index - 1));
        }
        if (tokensList[index].type == TokenTypesList.VARIABLE.tokenType) {
            index++
            return VariableNode(tokensList.get(index - 1));
        }
        throw Exception("Expected variable or number on $index position")
    }

    fun parseIF(): IfNode {
        val leftNode = parseThirdPriority()
        val operator = match(
            listOf(
                TokenTypesList.LESS.tokenType,
                TokenTypesList.MORE.tokenType,
                TokenTypesList.EQUAL.tokenType
            )
        ) ?: throw Exception("Exception in IF operator position $index!")
        val rightNode = parseThirdPriority()

        val ifNode = IfNode(operator, leftNode, rightNode)
        require(listOf(TokenTypesList.LBRACE.tokenType))
        while (tokensList[index].type != TokenTypesList.RBRACKET.tokenType) {
            ifNode.addThenOperations(getOperations())
            if (index == tokensList.size) {
                throw Exception("ERROR: Expected } on $index position")
            }
        }
        index++
//        require(listOf(TokenTypesList.ELSE.tokenType))
//        require(listOf(TokenTypesList.LBRACKET.tokenType))
//        while (tokensList[index].type != TokenTypesList.RBRACKET.tokenType) {
//            ifNode.addElseOperations(getOperations())
//            if (index == tokensList.size) {
//                throw Exception("ERROR: Expected } on $index position")
//            }
//        }
//        index++
        return ifNode
    }

    fun getOperations(): ExpressionNode {
        val codeStringNode = parseExpression()
        require(listOf(TokenTypesList.STRINGEND.tokenType))
        return codeStringNode
    }
}