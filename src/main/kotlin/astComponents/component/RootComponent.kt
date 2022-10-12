package astComponents.component

/**
 * Root component of the AST, holds the dataTable
 * @property dataTable Map<String, Int> table containing all variables and their values
 * @property ast MutableList<Component> the AST
 */
data class RootComponent(val dataTable: Map<String, Int>) : Component {
    val ast = mutableListOf<Component>()
}
