fun main() {
    val data = readInputFile("day14")
    val rules = data.drop(2)
        .map { it.split(" -> ") }
        .associateBy({ it[0] }, { it[1] })

    fun part1(): Int {
        var polymerTemplate = data.first()

        repeat(10) {
            polymerTemplate = buildString {
                for (i in 0 until polymerTemplate.length - 1) {
                    val a = polymerTemplate.substring(i, i + 2)
                    append(polymerTemplate[i])
                    append(rules[a]!!)
                }
                append(polymerTemplate.last())
            }
        }
        val values = polymerTemplate.groupingBy { it }.eachCount().map { it.value }
        val min = values.minOrNull() ?: 0
        val max = values.maxOrNull() ?: 0
        println("$values $max $min")
        return max - min
    }

    fun part2(): Long {
        val polymerTemplate = data.first()
        var pairInsertion = polymerTemplate
            .windowed(2)
            .groupingBy { it }
            .eachCount()
            .mapValues { it.value.toLong() }

        repeat(40) {
            pairInsertion = pairInsertion.flatMap { (a, c) ->
                val b = rules[a]!!
                listOf("${a[0]}$b" to c, "$b${a[1]}" to c)
            }.groupingBy { it.first }.fold(0L) { a, e -> a + e.second }
        }
        val values = pairInsertion.toList().flatMap { (p, c) -> listOf(p[0] to c, p[1] to c) }.groupingBy { it.first }
            .fold(0L) { a, e -> a + e.second }.toMutableMap()
        values[polymerTemplate.first()] = values[polymerTemplate.first()]!! + 1
        values[polymerTemplate.last()] = values[polymerTemplate.last()]!! + 1
        val halfVal = values.map { it.value / 2 }
        val min = halfVal.minOrNull() ?: 0
        val max = halfVal.maxOrNull() ?: 0
        println("$values $max $min")
        return max - min
    }
    println("Result part1: ${part1()}")
    println("Result part2: ${part2()}")

}
