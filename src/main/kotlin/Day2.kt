import java.io.File
import kotlin.math.abs
import kotlin.math.sign

fun List<Int>.isSafe(): Boolean {
    val diffs = zipWithNext().map { it.first - it.second }
    val signs = diffs.map { it.sign }.toSet()
    return signs.size == 1 && diffs.all { abs(it) in 1..3 }
}

fun List<Int>.isSafe2(): Boolean = indices.any { index ->
    this.toMutableList().also { it.removeAt(index) }.isSafe()
}

fun main() {
    val input = File("input/day2.txt").readLines().map { it.split(" ").map { it.toInt() } }
    println(input.filter { it.isSafe() }.size) // 252
    println(input.filter { it.isSafe2() }.size) // 324
}