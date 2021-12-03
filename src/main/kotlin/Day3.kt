fun main() {
    part1()
    println("Result part 2: ${part2(readInputFile("day3"))}")
}

fun part2(lines: List<String>): Int {
    val oxygen = lines.toMutableList()
    val scrubber = lines.toMutableList()
    for (i in 0 until lines.first().length) {
        val ox = oxygen.groupingBy { it[i] }
            .eachCount()
            .entries
            .maxWithOrNull(compareBy({ it.value }, { it.key }))!!.key
        val scrub = scrubber.groupingBy { it[i] }
            .eachCount()
            .entries
            .minWithOrNull(compareBy({ it.value }, { it.key }))!!.key
        oxygen.retainAll { it[i] == ox }
        scrubber.retainAll { it[i] == scrub }
    }
    println("oxygen = ${oxygen.single().toInt(2)} scrubber = ${scrubber.single().toInt(2)}")
    return oxygen.single().toInt(2) * scrubber.single().toInt(2)
}

private fun part1() {
    val dataMatrix: List<List<Int>> = readInputFile("day3")
        .map { it.toList() }
        .map { it.map { it.digitToInt() } }
    val n = dataMatrix.size
    val m = dataMatrix[0].size
    val h: Int = n / 2
    val map =
        mutableMapOf(
            Pair(0, 0), Pair(1, 0), Pair(2, 0), Pair(3, 0), Pair(4, 0), Pair(5, 0), Pair(6, 0),
            Pair(7, 0), Pair(8, 0), Pair(9, 0), Pair(10, 0), Pair(11, 0)
        )
    for (i in 0 until n) {
        for (j in 0 until m) {
            if (dataMatrix[i][j] == 1) {
                map[j] = map[j]!! + 1
            }
        }
    }
    var epsilon = ""
    var gamma = ""
    for ((_, v) in map) {
        if (v >= h) {
            gamma += "1"
            epsilon += "0"
        } else {
            gamma += "0"
            epsilon += "1"
        }
    }
    println("gamma =${gamma.toInt(2)} epsilon =${epsilon.toInt(2)}")
    println("Result = ${gamma.toInt(2) * epsilon.toInt(2)}")
}
