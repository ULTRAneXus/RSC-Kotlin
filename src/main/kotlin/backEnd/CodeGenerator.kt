package backEnd

import astComponents.argument.LiteralArgument
import astComponents.argument.VariableArgument
import astComponents.component.*
import astComponents.operator.ArithmeticOperator
import astComponents.operator.PrintOperator
import astComponents.operator.RelationalOperator

/**
 * Class that generates python code represented by an ast
 */
class CodeGenerator {

    /**
     * Uses the [rootComponent] to generate python code from the data and code segment
     * @param rootComponent RootComponent containing all necessary information
     * @return List<String> output python code
     */
    fun generateCode(rootComponent: RootComponent): List<String> {
        val code = mutableListOf<String>()
        code += getDataSegmentCode(rootComponent.dataTable)
        code += getCodeSegmentCode(rootComponent.ast)
        return code
    }

    /**
     * Uses the [dataTable] to generate the python representation of the code segment
     * @param dataTable Map<String, Int> containing the data table
     * @return List<String> generated python code
     */
    private fun getDataSegmentCode(dataTable: Map<String, Int>): List<String> {
        val output = mutableListOf<String>()
        for (line in dataTable) {
            output.add("${line.key} = ${line.value}")
        }
        return output
    }

    /**
     * Uses the [ast] to generate python code, delegates the actual generation to other methods
     * @param ast List<Component> containing the internal representation of the code
     * @param indent String used for indenting the code because python
     * @return List<String> generated python code
     */
    private fun getCodeSegmentCode(ast: List<Component>, indent: String = ""): List<String> {
        val code = mutableListOf<String>()
        for (component in ast.indices) {
            when (ast[component]) {
                is CommentComponent -> code.add(getCommentCode(ast[component] as CommentComponent, indent))
                is PrintComponent -> code.add(getPrintCode(ast[component] as PrintComponent, indent))
                is ArithmeticComponent -> code.add(getArithmeticCode(ast[component] as ArithmeticComponent, indent))
                is BranchComponent -> code += getBranchCode(ast[component] as BranchComponent, indent)
                is LoopComponent -> code += getLoopCode(ast[component] as LoopComponent, indent)
            }
        }
        return code
    }

    /**
     * Uses the [component] to generate the python representation of the comment
     * @param component CommentComponent input ast component
     * @param indent String used for indenting the code
     * @return String generated python code
     */
    private fun getCommentCode(component: CommentComponent, indent: String): String {
        return "${indent}#${component.content}"
    }

    /**
     * Uses the [component] to generate the python representation of the print statement
     * @param component PrintComponent input ast component
     * @param indent String used for indenting the code
     * @return String generated python code
     */
    private fun getPrintCode(component: PrintComponent, indent: String): String {
        var output = "${indent}print("
        if (component.operator == PrintOperator.LITERAL) {
            for (argument in component.arguments) {
                if (argument is LiteralArgument) {
                    output = "${output}\"${argument.getVal()}\"+"
                } else if (argument is VariableArgument) {
                    output = "${output}str(${argument.getVal()})+"
                }
            }
        } else if (component.operator == PrintOperator.INTERPRETED) {
            for (argument in component.arguments) {
                output = "${output}chr(${argument.getVal()})+"
            }
        }
        return "${output}\"\")"
    }

    /**
     * Uses the [component] to generate the python representation of the arithmetic function
     * @param component ArithmeticComponent input ast component
     * @param indent String used for indenting the code
     * @return String generated python code
     */
    private fun getArithmeticCode(component: ArithmeticComponent, indent: String): String {
        val operator = when (component.operator) {
            ArithmeticOperator.ADD -> "+"
            ArithmeticOperator.SUBTRACT -> "-"
            ArithmeticOperator.MULTIPLY -> "*"
            ArithmeticOperator.DIVIDE -> "/"
            ArithmeticOperator.MODULO -> "%"
        }
        return "${indent}${component.target.name}=${component.arguments[0].getVal()}$operator${component.arguments[1].getVal()}"
    }

    /**
     * Uses the [component] to generate the python representation of the branch statement
     * @param component BranchComponent input ast component
     * @param indent String used for indenting the code
     * @return List<String> generated python code
     */
    private fun getBranchCode(component: BranchComponent, indent: String): List<String> {
        val output = mutableListOf<String>()
        output.add("${indent}if ${component.relationalArguments.first.getVal()} ${getRelationalOperator(component.operator)} ${component.relationalArguments.second.getVal()} :")
        output += getCodeSegmentCode(component.ifBody, "$indent  ")
        if (component.elseBody.isNotEmpty()) {
            output += "$indent  0"
            output.add("${indent}else:")
            output += getCodeSegmentCode(component.elseBody, "$indent  ")
        }
        return output + "$indent  0"
    }

    /**
     * Uses the [component] to generate the python representation of the loop statement
     * @param component LoopComponent input ast component
     * @param indent String used for indenting the code
     * @return List<String> generated python code
     */
    private fun getLoopCode(component: LoopComponent, indent: String): List<String> {
        val output = mutableListOf<String>()
        output.add("${indent}while ${component.relationalArguments.first.getVal()} ${getRelationalOperator(component.operator)} ${component.relationalArguments.second.getVal()} :")
        output += getCodeSegmentCode(component.body, "$indent  ")
        return output + "$indent  continue"
    }

    /**
     * Returns a string representation of the [RelationalOperator]
     * @param operator RelationalOperator of which the string representation is needed
     * @return String representation of the RelationalOperator
     */
    private fun getRelationalOperator(operator: RelationalOperator): String {
        return when (operator) {
            RelationalOperator.EQUALS -> "=="
            RelationalOperator.NOT_EQUALS -> "!="
            RelationalOperator.GREATER_EQUALS -> ">="
            RelationalOperator.LESSER_EQUALS -> "<="
        }
    }
}