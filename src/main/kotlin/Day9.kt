import java.util.*

fun main() {
    val data = readInputFile("day9")

    fun getCandidates(i: Int, heights: List<List<Int>>, j: Int): Int {
        val candidates = mutableListOf<Int>()
        if (i > 0) candidates += heights[i - 1][j]
        if (j > 0) candidates += heights[i][j - 1]
        if (i < heights.size - 1) candidates += heights[i + 1][j]
        if (j < heights[i].size - 1) candidates += heights[i][j + 1]
        return minOf(candidates.minOrNull()!!)
    }

    fun part1(): Int {
        val heights = data.map { it.map { it.digitToInt() } }
        var risks = 0

        repeat(heights.size) { i ->
            repeat(heights[i].size) { j ->
                val height = getCandidates(i, heights, j)
                if (height > heights[i][j]) risks += (heights[i][j] + 1)
            }
        }
        return risks
    }

    fun part2(): Long {
        val heights = data.map { it.map { it.digitToInt() } }

        val lowPoints = mutableListOf<Pair<Int, Int>>()
        repeat(heights.size) { i ->
            repeat(heights[i].size) { j ->
                val height = getCandidates(i, heights, j)
                if (height > heights[i][j]) lowPoints += i to j
            }
        }

        return lowPoints.map { point ->
            val visited = mutableSetOf(point)
            val queue = LinkedList<Pair<Int, Int>>().apply { add(point) }
            while (queue.isNotEmpty()) {
                val (i, j) = queue.poll()
                if (i > 0) {
                    val point = (i - 1) to j
                    if (heights[i - 1][j] < 9 && point !in visited) {
                        visited.add(point)
                        queue.add(point)
                    }
                }
                if (j > 0) {
                    val point = i to (j - 1)
                    if (heights[i][j - 1] < 9 && point !in visited) {
                        visited.add(point)
                        queue.add(point)
                    }
                }
                if (i < heights.size - 1) {
                    val point = (i + 1) to j
                    if (heights[i + 1][j] < 9 && point !in visited) {
                        visited.add(point)
                        queue.add(point)
                    }
                }
                if (j < heights[i].size - 1) {
                    val point = i to (j + 1)
                    if (heights[i][j + 1] < 9 && point !in visited) {
                        visited.add(point)
                        queue.add(point)
                    }
                }
            }
            visited.size.toLong()
        }.sortedDescending()
            .take(3)
            .fold(1L) { a, v -> a * v }
    }
    println("Result part1:: ${part1()}")

    println("Result part2: ${part2()}")
}
