import java.io.File

fun readInputFile(name: String) = File("src/main/kotlin", "$name.in").readLines()
