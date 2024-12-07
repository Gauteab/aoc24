import util.print
import java.io.File
import kotlin.time.measureTime

fun List<Long>.isMatchFrom(target: Long, current: Long, i: Int, ops: List<(Long, Long) -> Long>): Boolean {
    if (i == size) return current == target
    return ops.any { op -> isMatchFrom(target, op(current, this[i]), i + 1, ops) }
}

fun main() = measureTime {
    val input = File("input/day7.txt").readLines()
        .map { it.split(":").map { it.trim().split(" ") }.let { it[0][0].toLong() to it[1].map(String::toLong) } }
    val ops = listOf<(Long, Long) -> Long>(Long::plus, Long::times)
    val part1 = input.sumOf { (target, nums) -> target.takeIf { nums.isMatchFrom(target, nums[0], 1, ops) } ?: 0 }
    val ops2 = ops + listOf { a, b -> "$a$b".toLong() }
    val part2 = input.sumOf { (target, nums) -> target.takeIf { nums.isMatchFrom(target, nums[0], 1, ops2) } ?: 0 }
    println(part1) // 267566105056
    println(part2) // 116094961956019
}.print()