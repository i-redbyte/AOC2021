import java.util.*

const val START = "start"
const val END = "end"
val ALPHABET = 'a'..'z'
fun makeGraph(data: List<String>): HashMap<String, HashSet<String>> {
    val graph = HashMap<String, HashSet<String>>()
    for (s in data) {
        val (x, y) = s.split("-")
        graph.getOrPut(x) { HashSet() }.add(y)
        graph.getOrPut(y) { HashSet() }.add(x)
    }
    return graph
}

fun main() {
    val graph = makeGraph(readInputFile("day12"))

    fun part1(): Int {
        var result = 0
        val vs = HashSet<String>()
        fun search(s: String) {
            if (s == END) {
                result++
                return
            }
            for (b in graph[s]!!) {
                if (b == START) continue
                val small = b[0] in ALPHABET
                if (small) {
                    if (b in vs) continue
                    vs += b
                }
                search(b)
                if (small) vs -= b
            }
        }
        search(START)
        return result
    }

    fun part2(): Int {
        var result = 0
        val vs = HashSet<String>()
        fun search(s: String, vt: Boolean) {
            if (s == END) {
                result++
                return
            }
            for (b in graph[s]!!) {
                if (b == START) continue
                val small = b[0] in ALPHABET
                var nvt = vt
                if (small) {
                    if (b in vs) {
                        if (vt) continue
                        nvt = true
                    } else {
                        vs += b
                    }
                }
                search(b, nvt)
                if (small && nvt == vt) vs -= b
            }
        }
        search(START, false)
        return result
    }
    println("Result part1: ${part1()}")

    println("Result part2: ${part2()}")
}