package astComponents.component

import astComponents.argument.Argument
import astComponents.operator.RelationalOperator

/**
 * Loop component for building the AST, RageScript equivalent of a while loop
 * @property operator RelationalOperator describes the desired relation between the [relationalArguments]
 * @property relationalArguments Pair<Argument, Argument> arguments whose state is being evaluated
 * @property body MutableList<Component> body of while loop
 */
data class LoopComponent(val operator: RelationalOperator) : Component {
    lateinit var relationalArguments: Pair<Argument, Argument>
    var body = listOf<Component>()
}
