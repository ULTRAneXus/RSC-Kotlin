package testHelper

import helper.DataSegmentEndFinder
import kotlin.test.Test
import kotlin.test.assertEquals

class TestDataSegmentEndFinder {

    @Test
    fun testFindDataSegmentEnd(){
        val dataSegmentEndFinder = DataSegmentEndFinder()
        var code: List<List<String>> = listOf(listOf("EEE", "reee"), listOf("A"))
        assertEquals(1, dataSegmentEndFinder.findDataSegmentEnd(code))
        code = listOf(listOf("EE", "rer"), listOf("EEE", "reaea"), listOf("A"), listOf("AA", "comment"))
        assertEquals(2, dataSegmentEndFinder.findDataSegmentEnd(code))
        code = listOf(listOf("AA", "another comment"))
        assertEquals(0, dataSegmentEndFinder.findDataSegmentEnd(code))
    }
}