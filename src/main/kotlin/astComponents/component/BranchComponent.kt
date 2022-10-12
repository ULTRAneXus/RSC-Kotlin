package astComponents.component

import astComponents.argument.Argument
import astComponents.operator.RelationalOperator

/**
 * Branch component for building the AST, RageScript equivalent of an if/else statement
 * @property operator RelationalOperator describes the desired relation between the [relationalArguments]
 * @property relationalArguments Pair<Argument, Argument> arguments whose state is being evaluated
 * @property ifBody MutableList<Component> if branch of AST
 * @property elseBody MutableList<Component> else branch of AST
 */
data class BranchComponent(val operator: RelationalOperator) : Component {
    lateinit var relationalArguments: Pair<Argument, Argument>
    var ifBody = mutableListOf<Component>()
    var elseBody = mutableListOf<Component>()
}