package testHelper

import helper.BinaryConverter
import kotlin.test.Test
import kotlin.test.assertEquals

class TestBinaryConverter {

    @Test
    fun testLiteralToInt(){
        val binaryConverter = BinaryConverter()
        //general conversion
        assertEquals(69, binaryConverter.literalToInt("reaaaeae"))
        //negative value
        assertEquals(-69, binaryConverter.literalToInt("reaaaeaer"))
        //leading a
        assertEquals(1, binaryConverter.literalToInt("raaaaae"))
        //negative 0
        assertEquals(0, binaryConverter.literalToInt("rar"))
    }
}