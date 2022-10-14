package testFrontEnd

import frontEnd.DataTableBuilder
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class TestDataTableBuilder {

    @Test
    fun testBuildDataTable() {
        val dataTableBuilder = DataTableBuilder()
        val map = dataTableBuilder.buildDataTable(
            listOf(
                //normal variable definition
                listOf("EE", "re"),
                //duplicate
                listOf("EEE", "reaaa"),
                listOf("EEE", "reeee")
            )
        )
        assertEquals(map["EE"], 1)
        assertNotEquals(map["EEE"], 8)
        assertEquals(map["EEE"], 15)
    }
}