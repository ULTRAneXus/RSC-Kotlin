package testFrontEnd

import astComponents.argument.Argument
import astComponents.argument.LiteralArgument
import astComponents.argument.VariableArgument
import astComponents.component.*
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
        astBuilder1.buildAbstractSyntaxTree(
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
        astBuilder2.buildAbstractSyntaxTree(
            listOf(
                listOf("AAAAA", "EE", "EE", "EEE"), //add
                listOf("AAAAAA", "EEE", "ree", "reer"), //subtract
                listOf("AAAAAAA", "EE", "EE", "rea"), //multiply
                listOf("AAAAAAAA", "EEE", "reeeea", "EE"), //divide
                listOf("AAAAAAAAA", "EE", "reee", "ree") //modulo
            )
        )
        assertEquals(rootComponent.ast.size, 5)
        genericTestASTArithmetic(
            rootComponent.ast[0],
            ArithmeticOperator.ADD,
            VariableArgument("EE"),
            listOf<Argument>(VariableArgument("EE"), VariableArgument("EEE"))
        )
        genericTestASTArithmetic(
            rootComponent.ast[1],
            ArithmeticOperator.SUBTRACT,
            VariableArgument("EEE"),
            listOf<Argument>(LiteralArgument("ree"), LiteralArgument("reer"))
        )
        genericTestASTArithmetic(
            rootComponent.ast[2],
            ArithmeticOperator.MULTIPLY,
            VariableArgument("EE"),
            listOf(VariableArgument("EE"), LiteralArgument("rea"))
        )
        genericTestASTArithmetic(
            rootComponent.ast[3],
            ArithmeticOperator.DIVIDE,
            VariableArgument("EEE"),
            listOf(LiteralArgument("reeeea"), VariableArgument("EE"))
        )
        genericTestASTArithmetic(
            rootComponent.ast[4],
            ArithmeticOperator.MODULO,
            VariableArgument("EE"),
            listOf<Argument>(LiteralArgument("reee"), LiteralArgument("ree"))
        )
    }

    private fun genericTestASTArithmetic(
        component: Component, operator: ArithmeticOperator, target: Argument, arguments: List<Argument>
    ) {
        assertTrue(component is ArithmeticComponent)
        assertEquals(operator, component.operator)
        assertEquals(target, component.target)
        assertEquals(arguments, component.arguments)
    }
}
