package testIO

import io.FileReader
import io.FileWriter
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class TestFileReader {

    @Test
    fun testWriteContentToFile() {
        //make a new file to validate file reader
        val fileWriter = FileWriter()
        fileWriter.writeContentToFile(
            "C:\\sheeeeesh\\RSC-Kotlin\\src\\test\\kotlin\\testIO\\testOutput.txt", listOf("Test1", "Test2")
        )
        val file = File("C:\\sheeeeesh\\RSC-Kotlin\\src\\test\\kotlin\\testIO\\testOutput.txt")
        require(file.canRead())  //sanity check

        //test file reader
        val fileReader = FileReader()
        val content = fileReader.getContent(file.path)
        assertEquals(listOf("Test1", "Test2"), content)
    }
}