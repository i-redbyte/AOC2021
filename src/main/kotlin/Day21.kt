typealias Cube = Array<Array<Array<Array<WorldCopy?>>>>

data class WorldCopy(var w1: Long, var w2: Long) {
    companion object {
        var cube: Cube? = null

        fun find(p1: Int, p2: Int, s1: Int, s2: Int): WorldCopy {
            cube?.get(p1)?.get(p2)?.get(s1)?.get(s2)?.let { return it }
            val c = WorldCopy(0, 0)
            for (d1 in 1..3) for (d2 in 1..3) for (d3 in 1..3) {
                val p1n = (p1 + d1 + d2 + d3 - 1) % 10 + 1
                val s1n = s1 + p1n
                if (s1n >= 21) {
                    c.w1++
                } else {
                    val cn = find(p2, p1n, s2, s1n)
                    c.w1 += cn.w2
                    c.w2 += cn.w1
                }
            }
            cube?.get(p1)?.get(p2)?.get(s1)?.set(s2, c)
            return c
        }
    }
}

fun main() {
    val data = readInputFile("day21")
    val players = IntArray(2)
    for (i in 0..1) {
        players[i] = data[i].removePrefix("Player ${i + 1} starting position: ").toInt()
    }

    fun part1(): Long {
        val p = players.clone()
        var rolls = 0L
        val s = IntArray(2)
        var i = 0
        var d = 1
        fun next(): Int {
            rolls++
            return d.also { d = d % 100 + 1 }
        }
        while (s[i] < 1000) {
            p[i] = (p[i] + next() + next() + next() - 1) % 10 + 1
            s[i] += p[i]
            if (s[i] >= 1000) {
                return s[1 - i] * rolls
            }
            i = (i + 1) % 2
        }
        return i.toLong()
    }

    fun part2(): Long {
        WorldCopy.cube = Cube(11) { Array(11) { Array(21) { arrayOfNulls(21) } } }
        val result = WorldCopy.find(players[0], players[1], 0, 0)
        return maxOf(result.w1, result.w2)
    }

    println("Result part1: ${part1()}")
    println("Result part2: ${part2()}")
}
