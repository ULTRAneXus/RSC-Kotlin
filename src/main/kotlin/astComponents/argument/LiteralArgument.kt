package astComponents.argument

/**
 * Literal value [Argument]
 */
data class LiteralArgument(val value: Int) : Argument {
    override fun getVal(): Int {
        return value
    }

}
