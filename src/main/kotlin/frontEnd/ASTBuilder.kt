package frontEnd

import astComponents.argument.LiteralArgument
import astComponents.argument.VariableArgument
import astComponents.component.ArithmeticComponent
import astComponents.component.CommentComponent
import astComponents.component.PrintComponent
import astComponents.component.RootComponent
import astComponents.operator.ArithmeticOperator
import astComponents.operator.PrintOperator

class ASTBuilder(private val rootComponent: RootComponent) {

    private val ast = rootComponent.ast

    fun buildAST(input: List<List<String>>) {
        for (linePointer in input.indices) {
            when (input[linePointer][0]) {
                "AA" -> ast.add(getCommentComponent(input[linePointer])) //comment
                "AAA", "AAAA" -> ast.add(getPrintComponent(input[linePointer])) //print
                "AAAAA", "AAAAAA", "AAAAAAA", "AAAAAAAA", "AAAAAAAAA" -> //arithmetic
                    ast.add(getArithmeticComponent(input[linePointer]))
            }
        }
    }

    private fun getCommentComponent(content: List<String>): CommentComponent {
        return CommentComponent(content.drop(1).joinToString(separator = " "))
    }

    private fun getPrintComponent(content: List<String>): PrintComponent {
        val printComponent = PrintComponent(
            when (content[0]) {
                "AAA" -> PrintOperator.LITERAL
                else -> PrintOperator.INTERPRETED
            }
        )
        for (argument in content.drop(1)) {
            when (argument.toCharArray()[0]) {
                'r' -> printComponent.arguments.add(LiteralArgument(argument))
                'E' -> printComponent.arguments.add(VariableArgument(argument))
            }
        }
        return printComponent
    }

    private fun getArithmeticComponent(content: List<String>): ArithmeticComponent {
        val arithmeticComponent = ArithmeticComponent(
            when (content[0]) {
                "AAAAA" -> ArithmeticOperator.ADD
                "AAAAAA" -> ArithmeticOperator.SUBTRACT
                "AAAAAAA" -> ArithmeticOperator.MULTIPLY
                "AAAAAAAA" -> ArithmeticOperator.DIVIDE
                else -> ArithmeticOperator.MODULO
            }
        )
        arithmeticComponent.target = VariableArgument(content[1])
        for (argument in content.drop(2)) {
            when (argument.toCharArray()[0]) {
                'r' -> arithmeticComponent.arguments.add(LiteralArgument(argument))
                'E' -> arithmeticComponent.arguments.add(VariableArgument(argument))
            }
        }
        return arithmeticComponent
    }
}