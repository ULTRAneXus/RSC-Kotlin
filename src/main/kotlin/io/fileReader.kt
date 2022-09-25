package io

/**
 * Class used for reading the content of files
 */
class fileReader {

    /**
     * Uses a [bufferedReader] to read a file from a given filePath
     * @param filePath the file to be read
     * @return returns a List<String> of the file
     */
    fun getContent(filePath: String):List<String>{
        println("initialising file $filePath")
        val file = java.io.File(filePath)
        require(file.isFile){"$filePath is not a file"}
        require(file.canRead()){"$filePath is not readable"}
        println("reading File $filePath")
        val bufferedFileReader = file.bufferedReader()
        val output = mutableListOf<String>()
        bufferedFileReader.lines().forEach { output.add(it) }
        println("finished reading file $filePath")
        return output
    }
}