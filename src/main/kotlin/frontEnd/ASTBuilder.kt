package frontEnd

import astComponents.argument.LiteralArgument
import astComponents.argument.VariableArgument
import astComponents.component.*
import astComponents.operator.ArithmeticOperator
import astComponents.operator.PrintOperator

/**
 * Class that builds an abstract syntax tree of the code in the code segment
 * @property rootComponent RootComponent holds the AST
 */
class ASTBuilder(private val rootComponent: RootComponent) {

    /**
     * Builds an AST from code and saves it the root component, assumes the input is lexically correct
     * @param input List<List<String>> code that will be used to build an AST
     */
    fun buildAbstractSyntaxTree(input: List<List<String>>) {
        val ast = builAST(input)
        for (component in ast) {
            rootComponent.ast.add(component)
        }
    }

    /**
     * Builds an AST from code, assumes the input is lexically correct
     * @param input List<List<String>> code that will be used to build an AST
     * @return List<Component> AST
     */
    private fun builAST(input: List<List<String>>): List<Component> {
        val ast = mutableListOf<Component>()
        for (linePointer in input.indices) {
            when (input[linePointer][0]) {
                "AA" -> ast.add(getCommentComponent(input[linePointer])) //comment
                "AAA", "AAAA" -> ast.add(getPrintComponent(input[linePointer])) //print
                "AAAAA", "AAAAAA", "AAAAAAA", "AAAAAAAA", "AAAAAAAAA" -> //arithmetic
                    ast.add(getArithmeticComponent(input[linePointer]))
            }
        }
        return ast
    }

    /**
     * Uses [content] to build a [CommentComponent]
     * @param content List<String>
     * @return CommentComponent finished component
     */
    private fun getCommentComponent(content: List<String>): CommentComponent {
        return CommentComponent(content.drop(1).joinToString(separator = " "))
    }

    /**
     * Uses [content] to build a [PrintComponent]
     * @param content List<String>
     * @return PrintComponent finished component
     */
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

    /**
     * Uses [content] to build a [ArithmeticComponent]
     * @param content List<String>
     * @return ArithmeticComponent finished component
     */
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