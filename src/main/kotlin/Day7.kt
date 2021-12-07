import kotlin.math.abs

fun main() {
    val data = readInputFile("day7").map { it.split(",") }
    val inputs = data.flatten().map { it.toInt() }
    fun part1(): Int {
        var resultFuel = Int.MAX_VALUE
        val max = inputs.maxOf { it }
        val min = inputs.minOf { it }
        for (i in min..max) {
            resultFuel = minOf(resultFuel, inputs.sumOf { abs(i - it) })
        }
        return resultFuel
    }

    fun part2(): Int {
        var resultFuel = Int.MAX_VALUE
        val max = inputs.maxOf { it }
        val min = inputs.minOf { it }
        for (i in min..max) {
            val totalFuel = inputs.sumOf {
                val n = abs(i - it)
                ((n + 1) * n) / 2
            }
            resultFuel = minOf(resultFuel, totalFuel)
        }
        return resultFuel
    }
    println("Result part1: ${part1()}")
    println("Result part2: ${part2()}")
}