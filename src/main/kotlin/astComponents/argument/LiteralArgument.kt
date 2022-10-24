package astComponents.argument

import helper.BinaryConverter

/**
 * Literal value [Argument]
 * @param value String RageScript literal representation of the value
 */
data class LiteralArgument(val value: String) : Argument {
    val binaryConverter = BinaryConverter()
    override fun getVal(): String {
        return binaryConverter.literalToInt(value).toString()
    }
}
