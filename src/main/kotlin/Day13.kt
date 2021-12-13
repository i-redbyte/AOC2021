data class Dot(val x: Int, val y: Int)

fun main() {
    val data = readInputFile("day13")
    var dots = makeData(data)

    fun makeDots(s: String, value: Int) {
        dots = if (s.first() == 'x') {
            dots.map { p ->
                if (p.x > value) {
                    Dot(2 * value - p.x, p.y)
                } else p
            }.toSet()
        } else {
            dots.map { p ->
                if (p.y > value) {
                    Dot(p.x, 2 * value - p.y)
                } else p
            }.toSet()
        }
    }

    fun getInputData(s: String) {
        val foldAlong = "fold along "
        check(s.startsWith(foldAlong))
        val dotString = s.substring(foldAlong.length)
        makeDots(dotString, dotString.substringAfter('=').toInt())
    }

    fun part1(): Int {
        val i = data.indexOf("") + 1
        getInputData(data[i])
        return dots.size
    }

    fun part2(): Int {
        for (i in data.indexOf("") + 1 until data.size) {
            getInputData(data[i])
        }

        val charTable = Array(6) { CharArray(40) { ' ' } }
        for (dot in dots) {
            charTable[dot.y][dot.x] = '$'
        }
        for (ch in charTable) {
            println(ch.concatToString())
        }
        println()
        /** Solution part 2:
        $    $$$   $$  $$$  $$$  $$$$  $$  $$$
        $    $  $ $  $ $  $ $  $ $    $  $ $  $
        $    $  $ $    $  $ $  $ $$$  $    $$$
        $    $$$  $ $$ $$$  $$$  $    $    $  $
        $    $ $  $  $ $    $ $  $    $  $ $  $
        $$$$ $  $  $$$ $    $  $ $$$$  $$  $$$
         */
        return dots.size
    }
    println("Result part1: ${part1()}")
    println()
    println("Result part2: ${part2()}")
}

private fun makeData(data: List<String>) = data.takeWhile { it.isNotEmpty() }.map { line ->
    line.split(",").map { it.toInt() }.let { (x, y) -> Dot(x, y) }
}.toSet()
