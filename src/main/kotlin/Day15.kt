import java.util.*

fun main() {
    val data = readInputFile("day15").map { it.toCharArray().map { it.digitToInt() } }

    fun part1(): Int {
        val n = data.size
        val m = data.first().size
        val matrix = Array(n) { IntArray(m) { Int.MAX_VALUE } }
        val v = Array(n) { BooleanArray(m) }
        fun relax(i: Int, j: Int, x: Int) {
            if (i !in 0 until n || j !in 0 until m) return
            matrix[i][j] = minOf(matrix[i][j], x + data[i][j])
        }
        matrix[0][0] = 0
        while (!v[n - 1][m - 1]) {
            var mx = Int.MAX_VALUE
            var mi = -1
            var mj = -1
            for (i in 0 until n) for (j in 0 until m) {
                if (!v[i][j] && matrix[i][j] < mx) {
                    mx = matrix[i][j]
                    mi = i
                    mj = j
                }
            }
            v[mi][mj] = true
            relax(mi - 1, mj, mx)
            relax(mi + 1, mj, mx)
            relax(mi, mj - 1, mx)
            relax(mi, mj + 1, mx)
        }
        return matrix[n - 1][m - 1]
    }

    fun part2(): Int {
        val n0 = data.size
        val m0 = data[0].size
        val n = 5 * n0
        val m = 5 * m0
        val a = Array(n) { i ->
            IntArray(m) { j ->
                val k = i / n0 + j / m0
                (data[i % n0][j % m0] + k - 1) % 9 + 1
            }
        }
        val d = Array(n) { IntArray(m) { Int.MAX_VALUE } }

        data class Pos(val i: Int, val j: Int, val x: Int)

        val v = Array(n) { BooleanArray(m) }
        val q = PriorityQueue(compareBy(Pos::x))
        fun relax(i: Int, j: Int, x: Int) {
            if (i !in 0 until n || j !in 0 until m || v[i][j]) return
            val xx = x + a[i][j]
            if (xx < d[i][j]) {
                d[i][j] = xx
                q += Pos(i, j, xx)
            }
        }
        d[0][0] = 0
        q.add(Pos(0, 0, 0))
        while (!v[n - 1][m - 1]) {
            val (i, j, x) = q.remove()
            if (v[i][j]) continue
            v[i][j] = true
            relax(i - 1, j, x)
            relax(i + 1, j, x)
            relax(i, j - 1, x)
            relax(i, j + 1, x)
        }
        return d[n - 1][m - 1]
    }
    println("Result part1: ${part1()}")
    println("Result part2: ${part2()}")
}