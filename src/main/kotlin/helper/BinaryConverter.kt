package helper

import kotlin.math.pow

/**
 * Class that converts RageScript literals to integers
 */
class BinaryConverter {

    /**
     * converts a given [literal] to an integer value, assumes lexical correctness
     * @param literal String to be converted to integer
     * @return Integer value of the literal
     */
    fun literalToInt(literal: String): Int {
        var output = 0
        var segmentedLiteral = literal.toCharArray().toList()
        segmentedLiteral = segmentedLiteral.drop(1) //remove leading r
        segmentedLiteral = segmentedLiteral.reversed() //reverse for binary conversion
        val literalIsNegative = segmentedLiteral.first() == 'r'
        if (literalIsNegative) segmentedLiteral = segmentedLiteral.drop(1)
        for (i in segmentedLiteral.indices) { // binary conversion
            when (segmentedLiteral[i]) {
                'e' -> output += 2.0.pow(i).toInt()
                'a' -> {}
            }
        }
        if (literalIsNegative) output *= -1
        return output
    }
}