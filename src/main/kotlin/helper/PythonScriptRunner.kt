package helper

import java.io.BufferedReader

/**
 * A simple class that can execute python scripts
 */
class PythonScriptRunner {

    /**
     * Executes the python script at [path] in the current [Runtime]
     * @param path String path of the python script
     */
    fun runPythonScript(path: String) {
        val process = Runtime.getRuntime().exec("python $path")
        val pyErrorStream = BufferedReader(process.errorReader())
        val pyInputStream = BufferedReader(process.inputReader())
        pyErrorStream.lines().forEach { println(it) }
        pyInputStream.lines().forEach { println(it) }
    }
}