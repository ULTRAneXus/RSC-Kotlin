package astComponents.argument

/**
 * Variable value [Argument]
 * @param name String Name of the Variable
 */
data class VariableArgument(val name: String) : Argument {

    /**
     * Function to retrieve the value of an argument. Uses the data table
     * @return Int value of an argument
     */
    override fun getVal(): Int {
        TODO("Not yet implemented")
    }
}
