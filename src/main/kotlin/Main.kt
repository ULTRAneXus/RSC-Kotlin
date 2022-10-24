import astComponents.component.RootComponent
import backEnd.CodeGenerator
import frontEnd.ASTBuilder
import frontEnd.DataTableBuilder
import helper.PythonScriptRunner
import io.FileReader
import io.FileWriter

fun main(args: Array<String>) {
    println("start")
    //build data table
    val dataTableBuilder = DataTableBuilder()
    val dataTable = dataTableBuilder.buildDataTable(
        listOf( //test data segment
            listOf("EE", "reee"), listOf("EEE", "reaaaeae"), listOf("EEEE", "rar")
        )
    )
    //initialise rootComponent and astBuilder
    val rootComponent = RootComponent(dataTable)
    val astBuilder = ASTBuilder(rootComponent)
    //read file
    val fileReader = FileReader()
    val input = fileReader.getContent("src/main/rageScript/input/test.rage")
    //slice lines into strings
    val slicedInput = mutableListOf<MutableList<String>>()
    for (line in input) {
        slicedInput.add((line.split(" ") as MutableList<String>))
        slicedInput.last().forEach { it.trim() }
    }
    //build ast
    astBuilder.buildAbstractSyntaxTree(slicedInput)
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

//OLD "TESTS"

//    val dataTableBuilder = DataTableBuilder()
//    val dataTable = dataTableBuilder.buildDataTable(
//        listOf( //test data segment
//            listOf("EE", "reee"), listOf("EEE", "reaaaeae"), listOf("EEEE", "rar")
//        )
//    )
//    val rootComponent = RootComponent(dataTable)
//    val astBuilder = ASTBuilder(rootComponent)
//    astBuilder.buildAbstractSyntaxTree(
//        listOf( //test code segment
//            listOf("AA", "the content", "and stuff"),
//            listOf("AAA", "reee", "rer"),
//            listOf("AAAA", "ree", "reaae"),
//            listOf("AAAAA", "EE", "reee", "re"),
//            listOf("AAAAAA", "EEE", "EEE", "EE"),
//            listOf("AAAAAAA", "EEEE", "rea", "reaa"),
//            listOf("AAAAAAAA", "EE", "EE", "rea"),
//            listOf("AAAAAAAAA", "EEE", "EE", "ree"),
//            listOf("OOO", "EEE", "EE"), //==
//                listOf("OOOO", "EEE", "EE"), //!=
//                    listOf("OOOOOOOOO", "EEE", "EE"), //>=0
//                        listOf("OOOOOOOOOO", "EEE", "EE"), //<=0
//                        listOf("OO"),
//                        listOf("AAA", "EEE"),
//                        listOf("AAA", "EEE"),
//                        listOf("AAA", "EEE"),
//                    listOf("OO"),
//                    listOf("AAA", "EEE"),
//                    listOf("AAA", "EEE"),
//                listOf("OO"),
//                listOf("AAA", "EEE"),
//            listOf("OO"),
//            listOf("AA", "I sure do hope it worky :)"),
//            listOf("YYY", "EEE", "EEEE"),
//                listOf("AA", "1st if body"),
//                listOf("YYYYYYYY", "EEE", "EEEE"),
//                    listOf("AA", "2nd if body"),
//                listOf("Y"),
//                    listOf("AA", "2nd else body"),
//                listOf("YY"),
//            listOf("Y"),
//                listOf("AA", "1st else body"),
//            listOf("YY"),
//            listOf("AA", "pls be worky :)")
//        )
//    )