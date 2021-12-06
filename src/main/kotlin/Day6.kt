fun main() {
    val data = readInputFile("day6")
    val lines = data.joinToString("")
        .split(",")

    fun part1(): Long {
        val initialData = LongArray(9) { 0 }
        lines.map { it.toInt() }
            .forEach { initialData[it]++ }
        return calculateLanternfish(80, initialData)
    }

    fun part2(): Long {
        val initialData = LongArray(9) { 0 }
        lines.map { it.toLong() }
            .forEach { initialData[it.toInt()] += 1L }
        return calculateLanternfish(256, initialData)
    }

    println("Result part1: ${part1()}")
    println("Result part2: ${part2()}")
}

fun calculateLanternfish(n: Int, d: LongArray): Long {
    var data = d
    var next = LongArray(9) { 0 }
    var tmp: LongArray
    for (i in 1..n) {
        next[8] = data[0]
        next[7] = data[8]
        next[6] = data[0] + data[7]
        next[5] = data[6]
        next[4] = data[5]
        next[3] = data[4]
        next[2] = data[3]
        next[1] = data[2]
        next[0] = data[1]
        tmp = next
        next = data
        data = tmp
    }
    return data.sum()
}
