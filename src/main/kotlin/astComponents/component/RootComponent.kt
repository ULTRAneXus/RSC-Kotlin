package astComponents.component

data class RootComponent(val dataTable: Map<String, Int>) : Component {
    val ast = mutableListOf<Component>()
}
