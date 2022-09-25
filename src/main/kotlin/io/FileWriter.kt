package io

/**
 * Class used for writing content to a file
 */
class FileWriter {

    /**
     * Uses a [bufferedWriter] to write content to a file at a given filePath
     * @param filePath the file to be written to
     * @param content takes a List<String> to be written to the specified file
     */
    fun writeContentToFile(filePath: String, content: List<String>) {
        println("initialising writing file $filePath")
        val file = java.io.File(filePath)
        require(file.isFile) { "$filePath is not a file" }
        require(file.canWrite()) { "$filePath is not writable" }
        println("writing to file $filePath")
        val bufferedFileWriter = file.bufferedWriter()
        content.forEach { bufferedFileWriter.write(it) }
        println("finished writing to file $filePath")
    }
}