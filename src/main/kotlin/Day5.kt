private const val MIN_COUNT_CROSSING = 2
data class Point(val x: Int, val y: Int)

class Segment(val start: Point, val end: Point) {

    private fun checkPoints(): Segment {
        if (start.x < end.x) return this
        if (start.x == end.x && start.y < end.y) return this
        return Segment(end, start)
    }

    fun getPoints(): List<Point> = with(checkPoints()) {
        if (start.x == end.x) {
            return (start.y..end.y).map { Point(start.x, it) }
        }
        if (start.y == end.y) {
            return (start.x..end.x).map { Point(it, start.y) }
        }

        val distanceX = end.x - start.x
        val distanceY = end.y - start.y
        val direction = when {
            distanceY > 0 -> 1
            distanceY < 0 -> -1
            else -> 0
        }
        return (0..distanceX).map { delta ->
            Point(start.x + delta, start.y + direction * delta)
        }
    }
}

private fun getSegments(data: List<String>) = data.map {
    val (start, end) = it.split("->")
    val (x1, y1) = start.split(",")
    val (x2, y2) = end.split(",")
    Segment(
        Point(x1.trim().toInt(), y1.trim().toInt()),
        Point(x2.trim().toInt(), y2.trim().toInt())
    )
}

fun main() {
    val data = readInputFile("day5")
    val segments = getSegments(data)

    fun countCrossing(segments: List<Segment>): Int {
        val count = mutableMapOf<Point, Int>()
        for (line in segments) {
            for (point in line.getPoints()) {
                count[point] = (count[point] ?: 0) + 1
            }
        }
        return count.filter { it.value >= MIN_COUNT_CROSSING }.count()
    }

    fun part1(): Int {
        val filtered = segments.filter { it.start.x == it.end.x || it.start.y == it.end.y }
        return countCrossing(filtered)
    }

    fun part2(): Int {
        return countCrossing(segments)
    }
    println("Result part1: ${part1()}")
    println("Result part2: ${part2()}")
}






