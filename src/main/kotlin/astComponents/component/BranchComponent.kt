package astComponents.component

import astComponents.argument.Argument
import astComponents.operator.RelationalOperator

/**
 * Branch component for building the AST, RageScript equivalent of an if/else statement
 */
data class BranchComponent(val operator: RelationalOperator) : Component {
    init {
        var relationalArguments: Pair<Argument, Argument>
    }

    var ifBody: Component? = null
    var elseBody: Component? = null
    override var nextComponent: Component? = null
}