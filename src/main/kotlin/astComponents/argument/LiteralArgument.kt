package astComponents.argument

/**
 * Literal value [Argument]
 * @param value String RageScript literal representation of the value
 */
data class LiteralArgument(val value: String) : Argument {

    /**
     * Function to retrieve the value of an argument. Uses binary conversion.
     * @return Int value of an argument
     */
    override fun getVal(): Int {
        TODO("Not yet implemented")
    }

}
