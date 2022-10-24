package astComponents.argument

/**
 * Variable value [Argument]
 * @param name String Name of the Variable
 */
data class VariableArgument(val name: String) : Argument {
    override fun getVal(): String {
        return name
    }
}
