package astComponents.component

import astComponents.argument.Argument
import astComponents.operator.RelationalOperator

/**
 * Loop component for building the AST, RageScript equivalent of a while loop
 */
data class LoopComponent(val operator: RelationalOperator) : Component {
    init {
        var relationalArguments: Pair<Argument, Argument>
    }

    var body: Component? = null
}
