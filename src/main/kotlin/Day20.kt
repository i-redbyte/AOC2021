

fun main() {
    val data = readInputFile("day20")
    fun part1(): Int {
        val boolMatrix = data[0].map { it == '#' }.toBooleanArray()
        var image =
            Image.parse(data.subList(2, data.size)).also { it.print2() }
                .makeImage(boolMatrix).also { it.print2() }
                .makeImage(boolMatrix).also { it.print2() }

        image = image.trim()

        return image.litPixels.size
    }

    fun part2(): Int {
        val boolMatrix = data[0].map { it == '#' }.toBooleanArray()

        var image = Image.parse(data.subList(2, data.size), 150)
        repeat(25) {
            image = image.makeImage(boolMatrix).makeImage(boolMatrix)
            image = image.trim()

            image.print2()
            println()
        }

        return image.litPixels.size
    }
    println("Result part1: ${part1()}")
    println("Result part2: ${part2()}")
}

data class Pixel(val x: Int, val y: Int)
data class Image(val litPixels: Set<Pixel>, val unlitPixels: Set<Pixel>, val xRange: IntRange, val yRange: IntRange) {
    fun print() {
        yRange.forEach { y ->
            val line = xRange.map { x -> if (Pixel(x, y) in litPixels) '#' else '.' }
                .joinToString("")
            println(line)
        }
        println()
    }

    fun trim(): Image {
        var minX = Int.MAX_VALUE
        var minY = Int.MAX_VALUE
        var maxX = Int.MIN_VALUE
        var maxY = Int.MIN_VALUE
        litPixels.forEach { (x, y) ->
            minX = minOf(x, minX)
            minY = minOf(y, minY)
            maxX = maxOf(x, maxX)
            maxY = maxOf(y, maxY)
        }
        var i = 0
        var offCount = 0
        while (offCount < 2) {
            if (Pixel(minX + i, minY + i) in litPixels) {
                offCount = 0
            } else {
                offCount++
            }
            i++
        }

        val newLit = mutableSetOf<Pixel>()

        ((minY + i) until maxY - i).forEach { y ->
            ((minX + i) until maxX - i).forEach { x ->
                val pixel = Pixel(x, y)
                if (pixel in litPixels) newLit.add(pixel)
            }
        }

        return Image(newLit, emptySet(), 1..2, 2..3)
    }

    fun print2() {
        var minX = Int.MAX_VALUE
        var minY = Int.MAX_VALUE
        var maxX = Int.MIN_VALUE
        var maxY = Int.MIN_VALUE
        litPixels.forEach { (x, y) ->
            minX = minOf(x, minX)
            minY = minOf(y, minY)
            maxX = maxOf(x, maxX)
            maxY = maxOf(y, maxY)
        }
        (minY..maxY).forEach { y ->
            val line = (minX..maxX).map { x -> if (Pixel(x, y) in litPixels) '#' else '.' }
                .joinToString("")
            println(line)
        }
    }

    fun makeImage(boolMatrix: BooleanArray): Image {
        val newLit = mutableSetOf<Pixel>()

        var minX = Int.MAX_VALUE
        var minY = Int.MAX_VALUE
        var maxX = Int.MIN_VALUE
        var maxY = Int.MIN_VALUE
        litPixels.forEach { (x, y) ->
            minX = minOf(x, minX)
            minY = minOf(y, minY)
            maxX = maxOf(x, maxX)
            maxY = maxOf(y, maxY)
        }

        ((minY - 10)..(maxY + 10)).forEach { y ->
            ((minX - 10)..(maxX + 10)).forEach { x ->
                if (shouldBeLit(x, y, boolMatrix)) newLit.add(Pixel(x, y))
            }
        }
        return Image(newLit, emptySet(), 0..1, 0..1)
    }

    fun shouldBeLit(x: Int, y: Int, boolMatrix: BooleanArray): Boolean {
        var neighbors = 0

        (-1..1).forEach { j ->
            (-1..1).forEach { i ->
                val pixel = Pixel(x + i, y + j)
                if (pixel in litPixels) neighbors++
                neighbors = neighbors shl 1
            }
        }
        neighbors = neighbors ushr 1

        return boolMatrix[neighbors]
    }

    companion object {
        fun parse(lines: List<String>, padding: Int = 6): Image {
            val lit = mutableSetOf<Pixel>()
            val unlit = mutableSetOf<Pixel>()
            val height = lines.size
            val width = lines[0].length

            (0 until (height+padding)).forEach { y ->
                (0 until (width+padding)).forEach { x ->
                    unlit.add(Pixel(x, y))
                }
            }

            lines.withIndex().forEach { (y, line) ->
                line.withIndex().forEach { (x, value) ->
                    if (value == '#') {
                        val pixel = Pixel(x + (padding / 2), y + (padding / 2))
                        unlit.remove(pixel)
                        lit.add(pixel)
                    }
                }
            }

            return Image(lit, unlit, 1 until (width+padding - 1), 1 until (height+padding - 1))
        }
    }
}
