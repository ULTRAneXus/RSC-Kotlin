package backEnd

import astComponents.argument.LiteralArgument
import astComponents.argument.VariableArgument
import astComponents.component.*
import astComponents.operator.ArithmeticOperator
import astComponents.operator.PrintOperator

class CodeGenerator {

    fun generateCode(rootComponent: RootComponent): List<String> {
        val ast = rootComponent.ast
        val code = mutableListOf<String>()
        for (line in getDataSegmentCode(rootComponent.dataTable)) code.add(line)
        for (component in ast.indices) {
            when (ast[component]) {
                is CommentComponent -> code.add(getCommentCode(ast[component] as CommentComponent))
                is PrintComponent -> code.add(getPrintCode(ast[component] as PrintComponent))
                is ArithmeticComponent -> code.add(getArithmeticCode(ast[component] as ArithmeticComponent))
                is BranchComponent -> {}
                is LoopComponent -> {}
            }
        }
        return code
    }

    private fun getDataSegmentCode(data: Map<String, Int>): List<String> {
        val output = mutableListOf<String>()
        for (line in data) {
            output.add("${line.key} = ${line.value}")
        }
        return output
    }

    private fun getCommentCode(component: CommentComponent): String {
        return "#${component.content}"
    }

    private fun getPrintCode(component: PrintComponent): String {
        var output = "print("
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

    private fun getArithmeticCode(component: ArithmeticComponent): String {
        val operator = when (component.operator) {
            ArithmeticOperator.ADD -> "+"
            ArithmeticOperator.SUBTRACT -> "-"
            ArithmeticOperator.MULTIPLY -> "*"
            ArithmeticOperator.DIVIDE -> "/"
            ArithmeticOperator.MODULO -> "%"
        }
        return "${component.target.name}=${component.arguments[0].getVal()}$operator${component.arguments[1].getVal()}"
    }
}