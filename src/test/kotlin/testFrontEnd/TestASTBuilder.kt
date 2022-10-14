package testFrontEnd

import astComponents.argument.LiteralArgument
import astComponents.argument.VariableArgument
import astComponents.component.CommentComponent
import astComponents.component.PrintComponent
import astComponents.component.RootComponent
import astComponents.operator.PrintOperator
import frontEnd.ASTBuilder
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestASTBuilder {

    @Test
    fun testBuildAST() {
        //comment and print
        val rootComponent1 = RootComponent(mapOf("EE" to 1, "EEE" to 2))
        val astBuilder1 = ASTBuilder(rootComponent1)
        astBuilder1.buildAST(
            listOf(
                listOf("AA", "I", "am", "a", "comment"),
                listOf("AAA", "EE", "reee"),
                listOf("AAAA", "EEE", "reaa")
            )
        )
        assertEquals(rootComponent1.ast.size, 3)
        assertTrue(rootComponent1.ast[0] is CommentComponent)
        val tempComp1 = rootComponent1.ast[0] as CommentComponent
        assertEquals(tempComp1.content, "I am a comment")
        assertTrue(rootComponent1.ast[1] is PrintComponent)
        var tempComp2 = rootComponent1.ast[1] as PrintComponent
        assertEquals(tempComp2.operator, PrintOperator.LITERAL)
        assertEquals(tempComp2.arguments, listOf(VariableArgument("EE"), LiteralArgument("reee")))
        assertTrue(rootComponent1.ast[2] is PrintComponent)
        tempComp2 = rootComponent1.ast[2] as PrintComponent
        assertEquals(tempComp2.operator, PrintOperator.INTERPRETED)
        assertEquals(tempComp2.arguments, listOf(VariableArgument("EEE"), LiteralArgument("reaa")))
        //arithmetic
//        val rootComponent2 = RootComponent(mapOf("EE" to 1, "EEE" to 2))
//        val astBuilder2 = ASTBuilder(rootComponent2)
//        astBuilder2.buildAST(
//            listOf(
//                listOf()
//            )
//        )
    }
}