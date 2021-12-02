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
        when (d.first) {
            "up" -> {
//                position.depth -= d.second //first part
                position.aim -= d.second
            }
            "down" -> {
//                position.depth += d.second //first part
                position.aim += d.second
            }
            "forward" -> {
                position.horizontal += d.second
                position.depth += d.second * position.aim
            }
        }

    }
    println("Result: ${position.getPosition()}")
}