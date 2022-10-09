package frontEnd

import kotlin.math.pow

/**
 * Class that builds a table of all variables defined in the data segment
 * @param input cleaned source code from data segment
 */
class dataTableBuilder() {

    /**
     * Builds a table containing all variables defined in the data segment with a Name -> value relation.
     * Assumes all values are lexically correct
     * @return Map<String, Int> data table
     */
    fun buildDataTable(input: List<List<String>>): Map<String, Int> {
        val output = mutableMapOf<String, Int>()
        var temp = 0
        for (line in input) {
            output[line[0]] = literalToInt(line[1])
        }
        return output
    }

    //TODO move to a dedicated helper class
    /**
     * converts a given [literal] to an integer value
     * @param literal String to be converted to integer
     * @return Integer value of the literal
     */
    private fun literalToInt(literal: String): Int {
        var output = 0
        var segmentedLiteral = literal.toCharArray().toList()
        segmentedLiteral = segmentedLiteral.drop(1) //remove leading r
        segmentedLiteral = segmentedLiteral.reversed() //reverse for binary conversion
        val literalIsNegative = segmentedLiteral.first() == 'r'
        if (literalIsNegative) segmentedLiteral = segmentedLiteral.drop(1)
        for (i in segmentedLiteral.indices) { //essentially binary conversion
            when (segmentedLiteral[i]) {
                'e' -> output += 2.0.pow(i).toInt()
                'a' -> {}
            }
        }
        if (literalIsNegative) output *= -1
        return output
    }
}