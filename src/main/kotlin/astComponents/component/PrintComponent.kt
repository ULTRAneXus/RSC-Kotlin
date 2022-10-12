package astComponents.component

import astComponents.argument.Argument
import astComponents.operator.PrintOperator

/**
 * Print component for building the AST, RageScript equivalent of a print statement
 * @property operator PrintOperator defines the interpretation of the arguments
 * @property arguments MutableList<Argument> content that's being printed
 */
data class PrintComponent(val operator: PrintOperator) : Component {
    val arguments = mutableListOf<Argument>()
}
