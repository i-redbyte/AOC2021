import java.util.*

class Cavern(private val octopi: Array<IntArray>) {
    constructor(lines: List<String>) :
            this(lines.map { it.map { c -> c.digitToInt() }.toIntArray() }.toTypedArray())

    fun step(): Pair<Cavern, Int> {
        val incremented = Array(10) { IntArray(10) }

        val flashSpots = LinkedList<Pair<Int, Int>>()
        (0 until 10).forEach { i ->
            (0 until 10).forEach { j ->
                incremented[i][j] = octopi[i][j] + 1
                if (incremented[i][j] == 10) {
                    flashSpots += i to j
                }
            }
        }

        val flashedAlready = flashSpots.toMutableSet()
        while (flashSpots.isNotEmpty()) {
            val (ci, cj) = flashSpots.poll()

            (-1 until 2).forEach { i ->
                (-1 until 2).forEach inner@{ j ->
                    val point = (ci + i) to (cj + j)
                    val (pi, pj) = point
                    if (point in flashedAlready) return@inner
                    if (pi >= 0 && pj >= 0 && pi < 10 && pj < 10) {
                        incremented[pi][pj]++
                        if (incremented[pi][pj] == 10) {
                            flashedAlready += point
                            flashSpots.add(point)
                        }
                    }
                }
            }
        }

        (0 until 10).forEach { i ->
            (0 until 10).forEach { j ->
                incremented[i][j] = if (incremented[i][j] > 9) 0 else incremented[i][j]
            }
        }

        return Cavern(incremented) to flashedAlready.size
    }
}

fun main() {
    val data = readInputFile("day11")
    fun part1(): Int {
        var cavern = Cavern(data)
        var flashes = 0
        repeat(100) {
            val (newCavern, newFlashes) = cavern.step()
            cavern = newCavern
            flashes += newFlashes
        }
        return flashes
    }

    fun part2(): Int {
        var cavern = Cavern(data)
        var i = 0
        do {
            val (newCavern, newFlashes) = cavern.step()
            cavern = newCavern
            i++
        } while (newFlashes != 100)
        return i
    }
    println("Result part1: ${part1()}")

    println("Result part2: ${part2()}")
}

