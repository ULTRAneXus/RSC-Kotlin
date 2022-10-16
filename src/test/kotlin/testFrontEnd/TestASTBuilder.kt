package testFrontEnd

import astComponents.argument.Argument
import astComponents.argument.LiteralArgument
import astComponents.argument.VariableArgument
import astComponents.component.ArithmeticComponent
import astComponents.component.CommentComponent
import astComponents.component.PrintComponent
import astComponents.component.RootComponent
import astComponents.operator.ArithmeticOperator
import astComponents.operator.PrintOperator
import frontEnd.ASTBuilder
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestASTBuilder {

    @Test
    fun testBuildASTCommentPrint() {
        val rootComponent = RootComponent(mapOf("EE" to 1, "EEE" to 2))
        val astBuilder1 = ASTBuilder(rootComponent)
        astBuilder1.buildAST(
            listOf(
                listOf("AA", "I", "am", "a", "comment"), //comment
                listOf("AAA", "EE", "reee"), //print literal
                listOf("AAAA", "EEE", "reaa") //print interpreted
            )
        )
        //test size
        assertEquals(3, rootComponent.ast.size)
        //test comment
        assertTrue(rootComponent.ast[0] is CommentComponent)
        val tempComp1 = rootComponent.ast[0] as CommentComponent
        assertEquals("I am a comment", tempComp1.content)
        //test print literal
        assertTrue(rootComponent.ast[1] is PrintComponent)
        var tempComp2 = rootComponent.ast[1] as PrintComponent
        assertEquals(PrintOperator.LITERAL, tempComp2.operator)
        assertEquals(listOf(VariableArgument("EE"), LiteralArgument("reee")), tempComp2.arguments)
        //test print interpreted
        assertTrue(rootComponent.ast[2] is PrintComponent)
        tempComp2 = rootComponent.ast[2] as PrintComponent
        assertEquals(PrintOperator.INTERPRETED, tempComp2.operator)
        assertEquals(listOf(VariableArgument("EEE"), LiteralArgument("reaa")), tempComp2.arguments)

    }

    @Test
    fun testASTArithmetic() {
        val rootComponent = RootComponent(mapOf("EE" to 1, "EEE" to 2))
        val astBuilder2 = ASTBuilder(rootComponent)
        astBuilder2.buildAST(
            listOf(
                listOf("AAAAA", "EE", "EE", "EEE"), //add
                listOf("AAAAAA", "EEE", "ree", "reer"), //subtract
                listOf("AAAAAAA", "EE", "EE", "rea"), //multiply
                listOf("AAAAAAAA", "EEE", "reeeea", "EE"), //divide
                listOf("AAAAAAAAA", "EE", "reee", "ree") //modulo
            )
        )
        //test size
        assertEquals(rootComponent.ast.size, 5)
        //test add
        assertTrue(rootComponent.ast[0] is ArithmeticComponent)
        var tempComp = rootComponent.ast[0] as ArithmeticComponent
        assertEquals(ArithmeticOperator.ADD, tempComp.operator)
        assertEquals(VariableArgument("EE"), tempComp.target)
        assertEquals(listOf<Argument>(VariableArgument("EE"), VariableArgument("EEE")), tempComp.arguments)
        //test subtract
        assertTrue(rootComponent.ast[1] is ArithmeticComponent)
        tempComp = rootComponent.ast[1] as ArithmeticComponent
        assertEquals(ArithmeticOperator.SUBTRACT, tempComp.operator)
        assertEquals(VariableArgument("EEE"), tempComp.target)
        assertEquals(listOf<Argument>(LiteralArgument("ree"), LiteralArgument("reer")), tempComp.arguments)
        //test multiply
        assertTrue(rootComponent.ast[2] is ArithmeticComponent)
        tempComp = rootComponent.ast[2] as ArithmeticComponent
        assertEquals(ArithmeticOperator.MULTIPLY, tempComp.operator)
        assertEquals(VariableArgument("EE"), tempComp.target)
        assertEquals(listOf(VariableArgument("EE"), LiteralArgument("rea")), tempComp.arguments)
        //test divide
        assertTrue(rootComponent.ast[3] is ArithmeticComponent)
        tempComp = rootComponent.ast[3] as ArithmeticComponent
        assertEquals(ArithmeticOperator.DIVIDE, tempComp.operator)
        assertEquals(VariableArgument("EEE"), tempComp.target)
        assertEquals(listOf(LiteralArgument("reeeea"), VariableArgument("EE")), tempComp.arguments)
        //test modulo
        assertTrue(rootComponent.ast[4] is ArithmeticComponent)
        tempComp = rootComponent.ast[4] as ArithmeticComponent
        assertEquals(ArithmeticOperator.MODULO, tempComp.operator)
        assertEquals(VariableArgument("EE"), tempComp.target)
        assertEquals(listOf<Argument>(LiteralArgument("reee"), LiteralArgument("ree")), tempComp.arguments)
    }
}
