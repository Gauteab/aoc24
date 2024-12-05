operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> = Pair(first + other.first, second + other.second)
fun main() = java.io.File("input/day4.txt").readLines().let { input ->
    input.indices.flatMap { x -> input[x].indices.map { y -> Pair(x, y) } }.count { p ->
        val word1 = listOf(p + Pair(-1, -1), p, p + Pair(1, 1)).mapNotNull { input.getOrNull(it.first)?.getOrNull(it.second) }.joinToString("")
        val word2 = listOf(p + Pair(1, -1), p, p + Pair(-1, 1)).mapNotNull { input.getOrNull(it.first)?.getOrNull(it.second) }.joinToString("")
        (word1 == "MAS" || word1 == "SAM") && (word2 == "MAS" || word2 == "SAM")
    }}.let(::println)