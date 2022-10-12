package astComponents.component

import astComponents.argument.Argument
import astComponents.operator.ArithmeticOperator

/**
 * Assign component for building the AST, RageScript equivalent of an arithmetic function
 */
data class ArithmeticComponent(val operator: ArithmeticOperator) : Component {
    lateinit var target: Argument
    var arguments = mutableListOf<Argument>()
}
