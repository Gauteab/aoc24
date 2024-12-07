package a

import java.io.File

fun List<Long>.isMatchFrom(target: Long, current: Long?, i: Int): Boolean {
    if (i == size) return current == target
    return isMatchFrom(target, (current ?: 1) * this[i], i + 1)
            || isMatchFrom(target, (current ?: 0) + this[i], i + 1)
}

fun List<Long>.isMatchFrom2(target: Long, current: Long?, i: Int): Boolean {
    if (i == size) return current == target
    return isMatchFrom2(target, (current ?: 1) * this[i], i + 1)
            || isMatchFrom2(target, (current ?: 0) + this[i], i + 1)
            || isMatchFrom2(target, ((current?.toString() ?: "") + this[i].toString()).toLong(), i + 1)
}

fun main() {
    val input = File("input/day7.txt").readLines()
        .map { it.split(":").map { it.trim().split(" ") }.let { it[0][0].toLong() to it[1].map(String::toLong) } }
    input.filter { (target, numbers) -> numbers.isMatchFrom(target, null, 0) }
        .sumOf { it.first }
        .also { println(it) } // 267566105056
    input.filter { (target, numbers) -> numbers.isMatchFrom2(target, null, 0) }
        .sumOf { it.first }
        .also { println(it) } // 116094961956019
}