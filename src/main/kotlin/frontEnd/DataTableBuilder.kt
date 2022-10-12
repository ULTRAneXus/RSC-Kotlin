package frontEnd

import helper.BinaryConverter

/**
 * Class that builds a table of all variables defined in the data segment
 */
class DataTableBuilder {

    /**
     * Builds a table containing all variables defined in the data segment with a Name -> value relation.
     * Assumes all values are lexically correct
     * @param input cleaned source code from data segment
     * @return Map<String, Int> data table
     */
    fun buildDataTable(input: List<List<String>>): Map<String, Int> {
        val output = mutableMapOf<String, Int>()
        val binaryConverter = BinaryConverter()
        for (line in input) {
            output[line[0]] = binaryConverter.literalToInt(line[1])
        }
        return output
    }
}