package frontEnd

import astComponents.argument.Argument
import astComponents.argument.LiteralArgument
import astComponents.argument.VariableArgument
import astComponents.component.*
import astComponents.operator.ArithmeticOperator
import astComponents.operator.PrintOperator
import astComponents.operator.RelationalOperator

/**
 * Class that builds an abstract syntax tree of the code in the code segment
 * @property rootComponent RootComponent holds the AST
 */
class ASTBuilder(private val rootComponent: RootComponent) {

    /**
     * Builds an AST from code and saves it the root component, assumes the input is lexically correct
     * @param input List<List<String>> code that will be used to build an AST
     */
    fun buildAbstractSyntaxTree(input: List<List<String>>) {
        val ast = buildAST(input)
        for (component in ast) {
            rootComponent.ast.add(component)
        }
    }

    /**
     * Builds an AST from code, assumes the input is lexically correct
     * @param input List<List<String>> code that will be used to build an AST
     * @return List<Component> AST
     */
    private fun buildAST(input: List<List<String>>): List<Component> {
        val ast = mutableListOf<Component>()
        var skipsLeft = 0 //acts as a way to jump forward in for loop
        for (linePointer in input.indices) {
            if (skipsLeft > 0) { //has to jump to next line?
                skipsLeft--
            } else {
                when (input[linePointer][0]) {
                    "AA" -> ast.add(getCommentComponent(input[linePointer])) //comment
                    "AAA", "AAAA" -> ast.add(getPrintComponent(input[linePointer])) //print
                    "AAAAA", "AAAAAA", "AAAAAAA", "AAAAAAAA", "AAAAAAAAA" -> //arithmetic
                        ast.add(getArithmeticComponent(input[linePointer]))

                    "OOO", "OOOO", "OOOOO", "OOOOOO", "OOOOOOO", "OOOOOOOO", "OOOOOOOOO", "OOOOOOOOOO" -> //loop
                        skipsLeft = addLoopComponent(input, linePointer, ast)

                    "YYY", "YYYY", "YYYYY", "YYYYYY", "YYYYYYY", "YYYYYYYY", "YYYYYYYYY", "YYYYYYYYYY" -> //branch
                        skipsLeft = addBranchComponent(input, linePointer, ast)
                }
            }
        }
        return ast
    }

    /**
     * Uses [content] to build a [CommentComponent]
     * @param content List<String>
     * @return CommentComponent finished component
     */
    private fun getCommentComponent(content: List<String>): CommentComponent {
        return CommentComponent(content.drop(1).joinToString(separator = " "))
    }

    /**
     * Uses [content] to build a [PrintComponent]
     * @param content List<String>
     * @return PrintComponent finished component
     */
    private fun getPrintComponent(content: List<String>): PrintComponent {
        val printComponent = PrintComponent(
            when (content[0]) {
                "AAA" -> PrintOperator.LITERAL
                else -> PrintOperator.INTERPRETED
            }
        )
        for (argument in content.drop(1)) {
            printComponent.arguments.add(toArgument(argument))
        }
        return printComponent
    }

    /**
     * Uses [content] to build a [ArithmeticComponent]
     * @param content List<String>
     * @return ArithmeticComponent finished component
     */
    private fun getArithmeticComponent(content: List<String>): ArithmeticComponent {
        val arithmeticComponent = ArithmeticComponent(
            when (content[0]) {
                "AAAAA" -> ArithmeticOperator.ADD
                "AAAAAA" -> ArithmeticOperator.SUBTRACT
                "AAAAAAA" -> ArithmeticOperator.MULTIPLY
                "AAAAAAAA" -> ArithmeticOperator.DIVIDE
                else -> ArithmeticOperator.MODULO
            }
        )
        arithmeticComponent.target = VariableArgument(content[1])
        for (argument in content.drop(2)) {
            arithmeticComponent.arguments.add(toArgument(argument))
        }
        return arithmeticComponent
    }

    /**
     * Uses [input] to build a [BranchComponent]
     * @param input List<List<String>> the code containing the branch
     * @param linePointer Int the starting position of the branch
     * @param ast MutableList<Component> the AST object
     * @return Int amount of jumps necessary to reach end of branch
     */
    private fun addBranchComponent(input: List<List<String>>, linePointer: Int, ast: MutableList<Component>): Int {
        val branchComponent = BranchComponent(
            when (input[linePointer][0]) {
                "YYY", "YYYYYYY" -> RelationalOperator.EQUALS
                "YYYY", "YYYYYYYY" -> RelationalOperator.NOT_EQUALS
                "YYYYY", "YYYYYYYYY" -> RelationalOperator.GREATER_EQUALS
                else -> RelationalOperator.LESSER_EQUALS
            }
        )
        branchComponent.relationalArguments = extractRelationalArguments(input[linePointer])
        //find the closing "YY" and potential else "Y" for the current loop
        var branchEndPointer = 0
        var branchElsePointer = 0
        var additionalOpenBranches = 0
        for (line in linePointer + 1 until input.size) {
            if (input[line][0] == "YY") { //found closing statement
                if (additionalOpenBranches == 0) { //does closing statement belong to current branch?
                    branchEndPointer = line //end of current branch
                    break
                } else additionalOpenBranches-- //closing statement doesnt belong to current loop
            } else if (input[line][0] == "Y" && additionalOpenBranches == 0) { //does found else statement belong to current branch?
                branchElsePointer = line //branch else statement
            } else if (input[line][0].first() == 'Y' && input[line].size > 2) additionalOpenBranches++ //found branch opening statement
        }
        //fill component with new AST(s)
        if (branchElsePointer != 0) {
            branchComponent.ifBody = buildAST(input.drop(linePointer + 1).dropLast(input.size - branchElsePointer))
            branchComponent.elseBody =
                buildAST(input.drop(branchElsePointer + 1).dropLast(input.size - branchEndPointer))
        } else {
            branchComponent.ifBody = buildAST(input.drop(linePointer + 1).dropLast(input.size - branchEndPointer))
        }
        ast.add(branchComponent)
        return branchEndPointer - linePointer //the size of the loop except the loop opening statement
    }

    /**
     * Uses [input] to build a [LoopComponent]
     * @param input List<List<String>> the code containing the loop
     * @param linePointer Int the starting position of the loop
     * @param ast MutableList<Component> the AST object
     * @return Int amount of jumps necessary to reach end of loop
     */
    private fun addLoopComponent(input: List<List<String>>, linePointer: Int, ast: MutableList<Component>): Int {
        val loopComponent = LoopComponent(
            when (input[linePointer][0]) {
                "OOO", "OOOOOOO" -> RelationalOperator.EQUALS
                "OOOO", "OOOOOOOO" -> RelationalOperator.NOT_EQUALS
                "OOOOO", "OOOOOOOOO" -> RelationalOperator.GREATER_EQUALS
                else -> RelationalOperator.LESSER_EQUALS
            }
        )
        loopComponent.relationalArguments = extractRelationalArguments(input[linePointer])
        //find the closing "OO" for the current loop
        var loopEndPointer = 0
        var additionalOpenLoops = 0
        for (line in linePointer + 1 until input.size) {
            if (input[line][0] == "OO") { //found closing statement
                if (additionalOpenLoops == 0) { //does closing statement belong to current loop?
                    loopEndPointer = line //end of current loop
                    break
                } else additionalOpenLoops-- //closing statement doesnt belong to current loop
            } else if (input[line][0].first() == 'O') additionalOpenLoops++ //found loop opening statement
        }
        //fill body with new AST
        loopComponent.body = buildAST(input.drop(linePointer + 1).dropLast(input.size - loopEndPointer))
        ast.add(loopComponent)
        return loopEndPointer - linePointer //the size of the loop except the loop opening statement
    }

    /**
     * Extracts the relational arguments from a loop or branch statement
     * @param line List<String> the statement
     * @return Pair<Argument, Argument> the extracted arguments
     */
    private fun extractRelationalArguments(line: List<String>): Pair<Argument, Argument> {
        return if (line[0].length < 7) { //is second argument not 0?
            Pair(toArgument(line[1]), toArgument(line[2]))
        } else { //second argument is 0
            Pair(toArgument(line[1]), LiteralArgument("ra"))
        }
    }

    /**
     * Converts a string into its related Argument, assumes lexical correctness
     * @param input String to be converted
     * @return Argument of [input]
     */
    private fun toArgument(input: String): Argument {
        return when (input.first()) {
            'r' -> LiteralArgument(input)
            else -> VariableArgument(input)
        }
    }
}