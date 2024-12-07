import util.orderOf10
import util.print
import java.io.File
import kotlin.time.measureTime

fun List<Long>.isMatchFrom(target: Long, current: Long, i: Int): Boolean {
    if (i == size) return current == target
    return isMatchFrom(target, current * this[i], i + 1) ||
            isMatchFrom(target, current + this[i], i + 1)
}

fun List<Long>.isMatchFrom2(target: Long, current: Long, i: Int): Boolean {
    if (i == size) return current == target
    return isMatchFrom2(target, current * this[i], i + 1) ||
            isMatchFrom2(target, current + this[i], i + 1) ||
            isMatchFrom2(target, current * this[i].orderOf10() + this[i], i + 1)
}

fun main() = measureTime {
    val input = File("input/day7.txt").readLines()
        .map { it.split(":").map { it.trim().split(" ") }.let { it[0][0].toLong() to it[1].map(String::toLong) } }
    val part1 = input.sumOf { (target, nums) -> target.takeIf { nums.isMatchFrom(target, nums[0], 1) } ?: 0 }
    val part2 = input.sumOf { (target, nums) -> target.takeIf { nums.isMatchFrom2(target, nums[0], 1) } ?: 0 }
    println(part1) // 267566105056
    println(part2) // 116094961956019
}.print() // 55ms