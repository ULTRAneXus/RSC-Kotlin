package astComponents.argument

/**
 * Argument for [astComponents.component.ArithmeticComponent], can be variable or literal
 */
interface Argument {

    /**
     * Generic function to retrieve the value of an argument
     * @return Int value of an argument
     */
    fun getVal(): Int
}
