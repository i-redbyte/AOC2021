
fun main() {
    val data = readInputFile("day18")

    fun part1(): Int {
        val sum = data
            .map { Snailfish.parse(it).populateParents() }
            .reduce { accumulator, value -> accumulator + value }
        return sum.magnitude
    }

    fun part2(): Int {
        val numbers = data.toList()

        var maxMagnitude = Int.MIN_VALUE
        numbers.forEach { a ->
            numbers.forEach inner@{ b ->
                if (a === b) return@inner
                val sfA = Snailfish.parse(a).populateParents()
                val sfB = Snailfish.parse(b).populateParents()
                val mag = (sfA + sfB).magnitude
                maxMagnitude = maxOf(maxMagnitude, mag)
            }
        }
        return maxMagnitude    }
    println("Result part1: ${part1()}")
    println("Result part2: ${part2()}")
}
sealed class Snailfish(open var parent: Compound? = null) {
    val magnitude: Int
        get() {
            return when (this) {
                is Compound -> 3 * left.magnitude + 2 * right.magnitude
                is Regular -> value
            }
        }
    val isLeft: Boolean
        get() = parent?.left === this
    val isRight: Boolean
        get() = parent?.right === this

    operator fun plus(rhs: Snailfish): Snailfish {
        val combined = Compound(this, rhs)
        this.parent = combined
        rhs.parent = combined
        combined.reduce()
        return combined
    }

    fun reduce(): Snailfish {
        do {
            var didSomething = false
            val exploder = findExploder()
            val splitter = findSplitter()
            if (exploder != null) {
                didSomething = true
                exploder.explode()
            } else if (splitter != null) {
                didSomething = true
                splitter.split()
            }
        } while (didSomething)
        return this
    }

    fun populateParents(): Snailfish {
        if (this !is Compound) return this

        left.parent = this
        right.parent = this

        left.populateParents()
        right.populateParents()

        return this
    }

    private fun findExploder(depth: Int = 0): Compound? {
        if (this !is Compound) return null
        if (depth == 4) return this

        return left.findExploder(depth + 1) ?: right.findExploder(depth + 1)
    }

    private fun findSplitter(): Regular? {
        if (this is Regular) {
            if (this.value >= 10) return this
            return null
        }

        this as Compound
        return left.findSplitter() ?: right.findSplitter()
    }

    data class Compound(
        var left: Snailfish,
        var right: Snailfish,
    ) : Snailfish() {
        fun explode() {
            val leftRegular = left as Regular
            val rightRegular = right as Regular
            findNextLeft()?.let { it.value += leftRegular.value }
            findNextRight()?.let { it.value += rightRegular.value }

            val replacement = Regular(0)
            replacement.parent = parent
            if (isLeft) parent?.left = replacement
            if (isRight) parent?.right = replacement
        }

        private fun findNextLeft(): Regular? {
            return if (isRight) {
                val parentLeft = parent?.left ?: return null
                when (parentLeft) {
                    is Regular -> parentLeft
                    is Compound -> parentLeft.deepestRight()
                }
            } else {
                var parentAsRight: Compound = parent ?: return null
                while (!parentAsRight.isRight) {
                    parentAsRight = parentAsRight.parent ?: return null
                }
                parentAsRight.findNextLeft()
            }
        }

        private fun findNextRight(): Regular? {
            return if (isLeft) {
                val parentRight = parent?.right ?: return null
                when (parentRight) {
                    is Regular -> parentRight
                    is Compound -> parentRight.deepestLeft()
                }
            } else {
                var parentAsLeft: Compound = parent ?: return null
                while (!parentAsLeft.isLeft) {
                    parentAsLeft = parentAsLeft.parent ?: return null
                }
                parentAsLeft.findNextRight()
            }
        }

        fun deepestRight(): Regular? {
            (right as? Regular)?.let { return it }
            return (right as Compound).deepestRight()
        }

        fun deepestLeft(): Regular? {
            (left as? Regular)?.let { return it }
            return (left as Compound).deepestLeft()
        }

        override fun toString(): String = "[$left, $right]"
    }

    data class Regular(var value: Int) : Snailfish() {
        fun split() {
            if (this.value < 10) return
            val replacement =
                Compound(
                    Regular(value / 2),
                    Regular(value / 2 + (if (value % 2 == 0) 0 else 1))
                )
            replacement.left.parent = replacement
            replacement.right.parent = replacement

            replacement.parent = parent
            if (isLeft) parent?.left = replacement
            if (isRight) parent?.right = replacement
        }

        override fun toString(): String = "$value"
    }

    companion object {
        fun parse(raw: String): Snailfish {
            if (raw[0] != '[') return Regular(raw.toInt())

            var counter = 0
            var i = 1
            while (counter != 0 || raw[i] != ',') {
                if (raw[i] == '[') counter++
                if (raw[i] == ']') counter--
                i++
            }

            val left = parse(raw.substring(1, i))
            val right = parse(raw.substring(i + 1, raw.length - 1))

            return Compound(left, right)
        }
    }
}