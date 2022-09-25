package io

/**
 * Class used for writing content to a file
 */
class FileWriter {

    /**
     * Uses a [bufferedWriter] to set the content of a file at a given filePath.
     * Will create new file if it doesn't exist.
     * @param filePath the file to be written to
     * @param content takes a List<String> to be written to the specified file
     */
    fun writeContentToFile(filePath: String, content: List<String>) {
        println("initialising writing file $filePath")
        val file = java.io.File(filePath)
        file.createNewFile()
        require(file.isFile) { "$filePath is not a file" }
        require(file.canWrite()) { "$filePath is not writable" }
        println("writing to file $filePath")
        file.bufferedWriter().use { out -> out.write(convertToString(content)) }
        println("finished writing to file $filePath")
    }

    private fun convertToString(content: List<String>): String {
        require(content.isNotEmpty()) { "content must not be empty" }
        if (content.size > 1) {
            var output = content.first()
            for (i in 1 until content.size)
                output += "\n" + content[i]
            return output
        } else return content.first()
    }
}

