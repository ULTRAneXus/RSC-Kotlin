package testFrontEnd

import astComponents.argument.Argument
import astComponents.argument.LiteralArgument
import astComponents.argument.VariableArgument
import astComponents.component.*
import astComponents.operator.ArithmeticOperator
import astComponents.operator.PrintOperator
import astComponents.operator.RelationalOperator
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

    @Test
    fun testASTBranch() {
        val rootComponent = RootComponent(mapOf("EE" to 1, "EEE" to 2))
        val astBuilder2 = ASTBuilder(rootComponent)
        astBuilder2.buildAbstractSyntaxTree(listOf())
    }

    private fun genericTestASTBranch(
        component: Component,
        operator: RelationalOperator,
        relationalArguments: Pair<Argument, Argument>,
        ifBody: List<Component>,
        elseBody: List<Component>
    ) {
        assertTrue(component is BranchComponent)
        assertEquals(operator, component.operator)
        assertEquals(relationalArguments, component.relationalArguments)
        assertEquals(ifBody, component.ifBody)
        assertEquals(elseBody, component.elseBody)
    }

    @Test
    fun testASTLoop() {
        val rootComponent = RootComponent(mapOf("EE" to 1, "EEE" to 2))
        val astBuilder2 = ASTBuilder(rootComponent)
        astBuilder2.buildAbstractSyntaxTree(
            listOf(
                listOf("OOO", "EE", "EEE"),
                listOf("OO"),
                listOf("OOOO", "EEE", "ree"),
                listOf("AA", "comment 1"),
                listOf("OO"),
                listOf("OOOOO", "reae", "EEE"),
                listOf("OOOOOO", "reer", "raear"),
                listOf("OO"),
                listOf("OO"),
                listOf("OOOOOOO", "reer"),
                listOf("OOOOOOOO", "reer"),
                listOf("OOOOOOOOO", "reer"),
                listOf("OOOOOOOOOO", "reer"),
                listOf("AA", "comment 2"),
                listOf("OO"),
                listOf("OO"),
                listOf("OO"),
                listOf("OO"),
            )
        )
        genericTestASTLoop(
            rootComponent.ast[0],
            RelationalOperator.EQUALS,
            Pair(VariableArgument("EE"), VariableArgument("EEE")),
            listOf()
        )
        genericTestASTLoop(
            rootComponent.ast[1],
            RelationalOperator.NOT_EQUALS,
            Pair(VariableArgument("EEE"), LiteralArgument("ree")),
            listOf(CommentComponent("comment 1"))
        )
        genericTestASTLoop(
            rootComponent.ast[2],
            RelationalOperator.GREATER_EQUALS,
            Pair(LiteralArgument("reae"), VariableArgument("EEE")),
            listOf(LoopComponent(RelationalOperator.LESSER_EQUALS).apply {
                relationalArguments = Pair(VariableArgument("reer"), VariableArgument("raear"))
                body = listOf()
            })
        )
        genericTestASTLoop(
            rootComponent.ast[3],
            RelationalOperator.EQUALS,
            Pair(LiteralArgument("reer"), LiteralArgument("ra")),
            listOf(LoopComponent(RelationalOperator.NOT_EQUALS).apply {
                relationalArguments = Pair(LiteralArgument("reer"), LiteralArgument("ra"))
                body = listOf(LoopComponent(RelationalOperator.GREATER_EQUALS).apply {
                    relationalArguments = Pair(LiteralArgument("reer"), LiteralArgument("ra"))
                    body = listOf(LoopComponent(RelationalOperator.LESSER_EQUALS).apply {
                        relationalArguments = Pair(LiteralArgument("reer"), LiteralArgument("ra"))
                        body = listOf()
                    })
                })
            })
        )
    }

    private fun genericTestASTLoop(
        component: Component,
        operator: RelationalOperator,
        relationalArguments: Pair<Argument, Argument>,
        body: List<Component>
    ) {
        assertTrue(component is LoopComponent)
        assertEquals(operator, component.operator)
        assertEquals(relationalArguments, component.relationalArguments)
        assertEquals(body, component.body)
    }
}
