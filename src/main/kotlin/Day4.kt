const val SIZE = 5


typealias BingoGame = Pair<List<Int>, MutableList<Board>>

private fun make(lines: List<String>): BingoGame {
    val numbers = lines[0].split(",").map { it.toInt() }
    val boards = mutableListOf<Board>()

    var line = 2
    while (line < lines.size) {
        boards.add(
            Board.create(
                lines[line],
                lines[line + 1],
                lines[line + 2],
                lines[line + 3],
                lines[line + 4]
            )
        )
        line += 6  // space
    }
    return numbers to boards
}

fun main() {
    val data = readInputFile("day4")
    fun part1(): Int {
        val (calls, boards) = make(data)

        var result = 0
        for (num in calls) {
            val victor = boards.find { it.suchMeaning(num) }
            if (victor != null) {
                result = victor.getScores(num)
                break
            }
        }
        return result
    }

    fun part2(): Int {
        val (calls, boards) = make(data)

        var result = 0
        for ((i, num) in calls.withIndex()) {
            val victors = boards.filter { it.suchMeaning(num) }
            victors.forEach { boards.remove(it) }
            if (boards.size == 0 || i == calls.size - 1) {
                result = victors.last().getScores(num)
                break
            }
        }
        return result
    }

    println("Part1 ${part1()}")
    println("Part2 ${part2()}")
}

class Board(numbers: List<Int>) {
    private val rows = Array(SIZE) {
        mutableSetOf(
            numbers[SIZE * it],
            numbers[SIZE * it + 1],
            numbers[SIZE * it + 2],
            numbers[SIZE * it + 3],
            numbers[SIZE * it + 4]
        )
    }
    private val cols = Array(SIZE) {
        mutableSetOf(
            numbers[it],
            numbers[it + SIZE],
            numbers[it + 10],
            numbers[it + 15],
            numbers[it + 20]
        )
    }

    fun suchMeaning(value: Int): Boolean {
        rows.forEach { it.remove(value) }
        cols.forEach { it.remove(value) }
        return rows.any { it.isEmpty() } || cols.any { it.isEmpty() }
    }

    fun getScores(num: Int): Int {
        return rows.flatMap { it.toList() }.sum() * num
    }

    companion object {
        fun create(vararg raw: String): Board =
            Board(raw.joinToString(" ").trim().split("\\s+".toRegex()).map { it.toInt() })
    }
}


