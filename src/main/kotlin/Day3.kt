import java.io.File

fun main() {
    val input = File("input/day3.txt").readText()
    val matches = """mul\((\d+),(\d+)\)""".toRegex().findAll(input)
    val part1 = matches.sumOf { it.groups[1]!!.value.toInt() * it.groups[2]!!.value.toInt() }
    println(part1) // 184122457

    var `do` = true
    val part2 = """(do\(\)|don't\(\)|mul\((\d+),(\d+)\))""".toRegex().findAll(input).sumOf {
        when (it.groups[1]!!.value) {
            "do()" -> 0.also { `do` = true }
            "don't()" -> 0.also { `do` = false }
            else -> {
                if (`do`) it.groups[2]!!.value.toInt() * it.groups[3]!!.value.toInt() else 0
            }
        }
    }
    println(part2) // 107862689

}
