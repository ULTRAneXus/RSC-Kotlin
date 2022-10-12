package astComponents.component

import astComponents.argument.Argument
import astComponents.operator.ArithmeticOperator

/**
 * Arithmetic component for building the AST, RageScript equivalent of an arithmetic function
 * @property operator [ArithmeticOperator]
 * @property target Argument Target variable for saving the result
 * @property arguments MutableList<Argument> Arguments used for the calculation
 */
data class ArithmeticComponent(val operator: ArithmeticOperator) : Component {
    lateinit var target: Argument
    var arguments = mutableListOf<Argument>()
}
