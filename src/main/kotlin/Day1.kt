fun main() {
    // Part 1
    val data: List<Int> = readInputFile("day1")
        .map { it.toInt() }

    val part1Result = data.windowed(2)
        .filter { it.last() > it.first() }
        .map { it.last() }
    println(part1Result.size)

    // Part 2
    val part2Result: List<Int> = data.windowed(3)
        .map { it.sum() }
        .windowed(2)
        .filter { it.last() > it.first() }
        .map { it.last() }

    println(part2Result.size)
}