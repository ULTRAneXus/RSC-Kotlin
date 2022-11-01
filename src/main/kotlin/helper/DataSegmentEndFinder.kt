package helper

/**
 * Class that finds the end of the data segment
 */
class DataSegmentEndFinder {

    /**
     * finds the end of the data segment in [input] or returns 0
     * @param input List<List<String>> code to be analysed
     * @return Int index of data segment end
     */
    fun findDataSegmentEnd(input: List<List<String>>): Int {
        for (line in input.indices) {
            if (input[line] == listOf("A")) return line
        }
        return 0
    }
}