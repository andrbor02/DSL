package Language

import Language.AST.*

class Parser(val tokensList: List<Token>) {
    private var index = 0

    fun parse(): RootNode {
        val root = RootNode()
        while (index < tokensList.size) {
            val codeStringNode = parseExpression()
            require(listOf(TokenTypesList.STRINGEND.tokenType))
            root.addNode(codeStringNode)
        }
        return root
    }

    fun getSimilarToken(tokenTypes: List<TokenType>): Token? {
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
        getSimilarToken(expected) ?: throw Exception("Expected ${expected[0]} on position ${tokensList[index].position}")
    }

    fun parseExpression(): ExpressionNode {
        when (tokensList[index].type.name) {
            TokenTypesList.VARIABLE.tokenType.name -> {
                val varNode = parseVariableOrNumber()
                val operator = getSimilarToken(listOf(TokenTypesList.ASSIGN.tokenType))
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
            TokenTypesList.FOR.tokenType.name -> {
                index++
                return parseFor()
            }
            TokenTypesList.WHILE.tokenType.name -> {
                index++
                return parseWhile()
            }
            else -> throw Exception("Expected variable or operator on position ${tokensList[index].position}")
        }
    }

    fun parseFirstPriority(): ExpressionNode {
        return if (tokensList[index].type == TokenTypesList.LBRACKET.tokenType) {
            index++
            val node = parseThirdPriority()
            require(listOf(TokenTypesList.RBRACKET.tokenType))
            node
        } else {
            parseVariableOrNumber()
        }
    }

    fun parseSecondPriority(): ExpressionNode {
        var leftVal = parseFirstPriority()
        var operator = getSimilarToken(
            listOf(
                TokenTypesList.MULTIPLY.tokenType,
                TokenTypesList.DIVIDE.tokenType
            )
        )
        while (operator != null) {
            val rightVal = parseFirstPriority()
            leftVal = BinaryOperatorNode(operator, leftVal, rightVal)
            operator = getSimilarToken(
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
        var operator = getSimilarToken(
            listOf(
                TokenTypesList.PLUS.tokenType,
                TokenTypesList.MINUS.tokenType
            )
        )
        while (operator != null) {
            val rightValue = parseSecondPriority()
            leftValue = BinaryOperatorNode(operator, leftValue, rightValue)
            operator = getSimilarToken(
                listOf(
                    TokenTypesList.PLUS.tokenType,
                    TokenTypesList.MINUS.tokenType
                )
            )
        }
        return leftValue
    }

    fun parseVariableOrNumber(): ExpressionNode {
        if (tokensList[index].type == TokenTypesList.NUMBER.tokenType) {
            index++
            return NumberNode(tokensList[index - 1])
        }
        if (tokensList[index].type == TokenTypesList.VARIABLE.tokenType) {
            index++
            return VariableNode(tokensList[index - 1])
        }
        throw Exception("Expected variable or number on $index position")
    }

    fun parseIF(): IfNode {
        val leftNode = parseThirdPriority()
        val operator = getSimilarToken(
            listOf(
                TokenTypesList.LESS.tokenType,
                TokenTypesList.MORE.tokenType,
                TokenTypesList.EQUAL.tokenType
            )
        ) ?: throw Exception("IF ERROR: Expected operator on position $index!")
        val rightNode = parseThirdPriority()

        val ifNode = IfNode(operator, leftNode, rightNode)
        require(listOf(TokenTypesList.LBRACE.tokenType))
        while (tokensList[index].type != TokenTypesList.RBRACE.tokenType) {
            ifNode.addThenOperations(getOperations())
            if (index == tokensList.size) {
                throw Exception("ERROR: Expected } on ${tokensList[index].position} position")
            }
        }
        index++
        if (tokensList[index].type.name == TokenTypesList.ELSE.tokenType.name) {
            index++
            require(listOf(TokenTypesList.LBRACE.tokenType))
            while (tokensList[index].type != TokenTypesList.RBRACE.tokenType) {
                ifNode.addElseOperations(getOperations())
                if (index == tokensList.size) {
                    throw Exception("ERROR: Expected } on ${tokensList[index].position} position")
                }
            }
            index++
        }
        return ifNode
    }

    fun parseWhile(): WhileNode {
        val leftNode = parseThirdPriority()
        val operator = getSimilarToken(
            listOf(
                TokenTypesList.LESS.tokenType,
                TokenTypesList.MORE.tokenType,
                TokenTypesList.EQUAL.tokenType
            )
        ) ?: throw Exception("WHILE ERROR: Expected operator on position $index!")
        val rightNode = parseThirdPriority()

        val whileNode = WhileNode(operator, leftNode, rightNode)
        require(listOf(TokenTypesList.LBRACE.tokenType))
        while (tokensList[index].type != TokenTypesList.RBRACE.tokenType) {
            whileNode.addOperations(getOperations())
            if (index == tokensList.size) {
                throw Exception("ERROR: Expected } on ${tokensList[index].position} position")
            }
        }
        index++
        return whileNode
    }

    fun parseFor(): ExpressionNode {
//        val leftNode = parseThirdPriority()
//        val operator = getSimilarToken(
//            listOf(
//                TokenTypesList.LESS.tokenType,
//                TokenTypesList.MORE.tokenType,
//                TokenTypesList.EQUAL.tokenType
//            )
//        ) ?: throw Exception("FOR ERROR: Expected operator on position $index!")
//        val rightNode = parseThirdPriority()
//
//        val varNode = parseVariableOrNumber()
//        val assign = getSimilarToken(listOf(TokenTypesList.ASSIGN.tokenType))
//        val rightVal = parseThirdPriority()
//        val actionNode
//        if (assign != null) {
//            actionNode = BinaryOperatorNode(assign, varNode, rightVal)
//        } else {
//            throw Exception("Expected '=' in FOR on position $index")
//        }
//        val forNode = ForNode(operator, leftNode, rightNode,)
        return ExpressionNode()
    }

    fun getOperations(): ExpressionNode {
        val codeStringNode = parseExpression()
        require(listOf(TokenTypesList.STRINGEND.tokenType))
        return codeStringNode
    }
}