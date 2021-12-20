fun main() {
    val data = readInputFile("day17")
    val (xr, yr) = data.first().removePrefix("target area: ").split(", ")
    val (x1, x2) = xr.removePrefix("x=").split("..").map { it.toInt() }
    val (y1, y2) = yr.removePrefix("y=").split("..").map { it.toInt() }
    fun part1(): Int {
        var result = 0
        for (vx0 in 1..1000) for (vy0 in 0..1000) {
            var vx = vx0
            var vy = vy0
            var x = 0
            var y = 0
            var maxY = 0
            var ok = false
            while (x <= x2 && y >= y1) {
                x += vx
                y += vy
                maxY = maxOf(maxY, y)
                if (x in x1..x2 && y in y1..y2) {
                    ok = true
                    break
                }
                if (vx > 0){
                    vx--
                }
                vy--
            }
            if (ok) result = maxOf(result, maxY)
        }
        return result
    }

    fun part2(): Int {
        var result = 0
        for (wX0 in 1..1000) {
            for (wY0 in -1000..1000) {
                var wX = wX0
                var wY = wY0
                var x = 0
                var y = 0
                var ok = false
                while (x <= x2 && y >= y1) {
                    x += wX
                    y += wY
                    if (x in x1..x2 && y in y1..y2) {
                        ok = true
                        break
                    }
                    if (wX > 0){
                        wX--
                    }
                    wY--
                }
                if (ok) {
                    result++
                }
            }
        }
        return result
    }
    println("Result part1: ${part1()}")
    println("Result part2: ${part2()}")
}
