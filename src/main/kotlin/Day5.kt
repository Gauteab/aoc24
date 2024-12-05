import java.io.File

fun main() {
    val lines = File("input/day5.txt").readLines()
    val rules = mutableMapOf<Int, MutableList<Int>>()
    lines.takeWhile { it != "" }.forEach {
        val (a, b) = it.split("|").map(String::toInt)
        rules.getOrPut(a) { mutableListOf() }.add(b)
    }
    val updates = lines.dropWhile { it != "" }.drop(1).map { it.split(",").map(String::toInt) }

    fun isOrdered(update: List<Int>): Boolean {
        val seen = mutableListOf<Int>()
        return update.reversed().none { n ->
            (n in seen).also { seen.addAll(rules[n] ?: emptyList()) }
        }
    }

    // part1
    updates.filter { isOrdered(it) }
        .sumOf { it[it.size / 2] }
        .also { println(it) } // 6612

    fun sortUpdate(update: List<Int>): List<Int> {
        val result = mutableListOf<Int>()
        update.forEachIndexed { i, n ->
            val violation = rules[n]?.mapNotNull { result.indexOf(it).takeIf { it != -1 } }?.minOrNull()
            result.add(violation ?: i, n)
        }
        return result
    }

    // part2
    updates.filter { !isOrdered(it) }
        .map { sortUpdate(it) }
        .sumOf { it[it.size / 2] }
        .also { println(it) } // 4944
}