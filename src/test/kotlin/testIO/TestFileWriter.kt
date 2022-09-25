package testIO

import io.FileWriter
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestFileWriter {

    @Test
    fun testWriteContentToFile() {
        val fileWriter = FileWriter()
        fileWriter.writeContentToFile(
            "C:\\sheeeeesh\\RSC-Kotlin\\src\\test\\kotlin\\testIO\\testOutput.txt",
            listOf("Test1", "Test2")
        )
        val file = File("C:\\sheeeeesh\\RSC-Kotlin\\src\\test\\kotlin\\testIO\\testOutput.txt")
        assertTrue { file.isFile }
        assertTrue { file.canWrite() }
        require(file.canRead())
        assertEquals(listOf("Test1", "Test2"), file.readLines())
    }
}