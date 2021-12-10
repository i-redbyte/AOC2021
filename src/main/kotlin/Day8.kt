private const val SHOW_1_COUNT = 2
private const val SHOW_4_COUNT = 4
private const val SHOW_7_COUNT = 3
private const val SHOW_8_COUNT = 7

private typealias Display = Pair<List<String>, List<String>>

fun main() {
    val data = readInputFile("day8")
    val initialState: List<Display> = data.map { line ->
        line.split("|").let { it[0].trim().split(" ") to it[1].trim().split(" ")}
    }

     fun part1(): Int = initialState.flatMap { it.second }.count {
        it.length in listOf(SHOW_1_COUNT, SHOW_4_COUNT, SHOW_7_COUNT, SHOW_8_COUNT)
    }

     fun part2(): Int =
        initialState.sumOf { (wires, output) ->
            val oneDisplay = wires.first { it.length == SHOW_1_COUNT }
            val fourDisplay = wires.first { it.length == SHOW_4_COUNT }
            val sevenDisplay = wires.first { it.length == SHOW_7_COUNT }
            val eightDisplay = wires.first { it.length == SHOW_8_COUNT }

            val rightSegments = oneDisplay.toCharArray()
            val middleSegments = fourDisplay.filterNot { it in sevenDisplay }.toCharArray()
            val bottomSegments = eightDisplay.filterNot { it in sevenDisplay + fourDisplay }.toCharArray()

            output.map { show ->
                when (show.length) {
                    SHOW_1_COUNT -> 1
                    SHOW_4_COUNT -> 4
                    SHOW_7_COUNT -> 7
                    SHOW_8_COUNT -> 8
                    5 -> when {
                        show.containsAll(*middleSegments) -> 5
                        show.containsAll(*bottomSegments) -> 2
                        else -> 3
                    }
                    6 -> when {
                        show.containsAll(*middleSegments, *rightSegments) -> 9
                        show.containsAll(*middleSegments) -> 6
                        else -> 0
                    }
                    else -> error("Error $show")
                }
            }.joinToString("") { it.toString() }.toInt()
        }
    println("Result part1: ${part1()}")

    println("Result part2: ${part2()}")
}

private fun String.containsAll(vararg chars: Char): Boolean = chars.all { it in this }