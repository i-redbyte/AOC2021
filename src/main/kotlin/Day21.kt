fun main() {
    val data = readInputFile("day21")
    fun part1(): Long {
        val p = IntArray(2)
        for (i in 0..1) {
            p[i] = data[i].removePrefix("Player ${i + 1} starting position: ").toInt()
        }
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
    fun part2(): Int {
        return 0
    }

    println("Result part1: ${part1()}")
    println("Result part2: ${part2()}")
}
