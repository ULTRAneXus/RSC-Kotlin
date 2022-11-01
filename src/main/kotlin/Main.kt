import astComponents.component.RootComponent
import backEnd.CodeGenerator
import frontEnd.ASTBuilder
import frontEnd.DataTableBuilder
import helper.DataSegmentEndFinder
import helper.PythonScriptRunner
import io.FileReader
import io.FileWriter

fun main(args: Array<String>) {
    println("starting")

    //read file
    val fileReader = FileReader()
    val input = fileReader.getContent("src/main/rageScript/input/test.rage")

    //slice lines into strings
    val slicedInput = mutableListOf<List<String>>()
    for (line in input) {
        slicedInput.add((line.split(" ")))
        slicedInput.last().forEach { it.trim() }
    }

    //splits input into data and code segment
    val dataSegmentEndFinder = DataSegmentEndFinder()
    val dataSegmentEnd = dataSegmentEndFinder.findDataSegmentEnd(slicedInput)
    val dataSegment = slicedInput.dropLast(slicedInput.size - dataSegmentEnd)
    val codeSegment = slicedInput.drop(dataSegmentEnd + 1)

    //build data table
    //TODO read the actual file instead
    val dataTableBuilder = DataTableBuilder()
    val dataTable = dataTableBuilder.buildDataTable(dataSegment)

    //initialise rootComponent and astBuilder
    val rootComponent = RootComponent(dataTable)
    val astBuilder = ASTBuilder(rootComponent)

    //build ast
    astBuilder.buildAbstractSyntaxTree(codeSegment)

    //generate output code
    val codeGenerator = CodeGenerator()
    val output = codeGenerator.generateCode(rootComponent)

    //write output to file
    val fileWriter = FileWriter()
    fileWriter.writeContentToFile("src/main/rageScript/output/test.py", output)

    //execute output
    val pythonScriptRunner = PythonScriptRunner()
    pythonScriptRunner.runPythonScript("src/main/rageScript/output/test.py")

    println("done")
}
