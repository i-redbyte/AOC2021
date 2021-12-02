enum class Direction {
    up,
    down,
    forward
}

data class Position(
    var horizontal: Int = 0,
    var depth: Int = 0,
    var aim: Int = 0
) {
    fun getPosition() = horizontal * depth
}

fun main() {

    val data: List<Pair<String, Int>> = readInputFile("day2")
        .map { it.split(" ") }
        .map { it.first() to it.last().toInt() }
    val position = Position()
    for (d in data) {
        when (Direction.valueOf(d.first)) {
            Direction.up -> {
//                position.depth -= d.second //first part
                position.aim -= d.second
            }
            Direction.down -> {
//                position.depth += d.second //first part
                position.aim += d.second
            }
            Direction.forward -> {
                position.horizontal += d.second
                position.depth += d.second * position.aim
            }
        }

    }
    println("Result: ${position.getPosition()}")
}