package astComponents.component

import astComponents.argument.Argument
import astComponents.operator.PrintOperator

data class PrintComponent(val operator: PrintOperator) : Component{
    val arguments = mutableListOf<Argument>()
}
