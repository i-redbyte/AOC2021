import java.util.*

fun main() {
    val data = readInputFile("day10")
    fun part1(): Int {
        return data.sumOf { it.findSyntaxErrorScore() }
    }

    fun part2(): Long {
        return data
            .mapNotNull { it.findCompletionScore() }
            .filter { it != 0L }
            .sorted()
            .let { it[it.size / 2] }
    }
    println("Result part1: ${part1()}")

    println("Result part2: ${part2()}")
}

fun String.findSyntaxErrorScore(): Int {
    val stack = LinkedList<Char>()
    var i = 0
    do {
        when (val c = this[i]) {
            '(', '[', '{', '<' -> stack.push(c)
            ')' -> {
                val top = stack.pop()
                if (top != '(') return score(c)
            }
            ']' -> {
                val top = stack.pop()
                if (top != '[') return score(c)
            }
            '}' -> {
                val top = stack.pop()
                if (top != '{') return score(c)
            }
            '>' -> {
                val top = stack.pop()
                if (top != '<') return score(c)
            }
        }
        i++
    } while (stack.isNotEmpty() && i < length)
    return 0
}

fun String.findCompletionScore(): Long? {
    val stack = LinkedList<Char>()
    var i = 0
    do {
        when (val c = this[i]) {
            '(', '[', '{', '<' -> stack.push(c)
            ')' -> {
                val top = stack.pop()
                if (top != '(') return null
            }
            ']' -> {
                val top = stack.pop()
                if (top != '[') return null
            }
            '}' -> {
                val top = stack.pop()
                if (top != '{') return null
            }
            '>' -> {
                val top = stack.pop()
                if (top != '<') return null
            }
        }
        i++
    } while (stack.isNotEmpty() && i < length)

    return stack
        .fold(0L) { acc, char ->
            (acc * 5) + when (char) {
                '(' -> 1
                '[' -> 2
                '{' -> 3
                '<' -> 4
                else -> 0
            }
        }
}

fun score(char: Char): Int = when(char) {
    ')' -> 3
    ']' -> 57
    '}' -> 1197
    '>' -> 25137
    else -> 0
}
