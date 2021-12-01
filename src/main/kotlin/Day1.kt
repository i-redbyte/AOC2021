fun main() {
    // Part 1
    val result: List<Int> = readInputFile("day1")
        .map { it.toInt() }
        .windowed(2)
        .filter { it[1] > it[0] }
        .map { it[1] }
    println(result.size)
}