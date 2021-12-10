import java.io.File

fun readInputFile(name: String) = File("src/main/kotlin/input", "$name.in").readLines()
