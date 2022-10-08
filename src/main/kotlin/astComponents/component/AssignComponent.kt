package astComponents.component

import astComponents.argument.Argument
import astComponents.operator.ArithmeticOperator

/**
 * Assign component for building the AST, RageScript equivalent of an arithmetic function
 */
data class AssignComponent(val operator: ArithmeticOperator) : Component {
    init {
        var target: Argument
    }

    var arguments: List<Argument> = listOf()
    override var nextComponent: Component? = null
}
