import java.io.File
import kotlin.math.abs

fun main() {
    val input = File("input/day1.txt").readLines().map { line -> line.split("   ").let { it[0].toInt() to it[1].toInt() } }.unzip()
    val diffScore = input.first.sorted().zip(input.second.sorted()).sumOf { abs(it.first - it.second) }
    println(diffScore) // 1580061

    val rightCounts = input.second.groupingBy { it }.eachCount()
    val similarityScore = input.first.sumOf { left -> left * (rightCounts[left] ?: 0)  }
    println(similarityScore) // 23046913
}