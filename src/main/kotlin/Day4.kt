import java.awt.Point
import java.io.File

operator fun Point.plus(other: Point): Point = Point(x + other.x, y + other.y)

fun List<Point>.getWord(input: List<String>) = mapNotNull { input.getOrNull(it.x)?.getOrNull(it.y) }.joinToString("")

fun part1(input: List<String>): Int {
    val vectors = listOf(Point(0, 1), Point(1, 0), Point(1, 1), Point(1, -1))

    fun searchFrom(p: Point, v: Point): Boolean {
        val word = listOf(p, p + v, p + v + v, p + v + v + v).getWord(input)
        return word == "XMAS" || word == "SAMX"
    }

    return input.indices.flatMap { x -> input[x].indices.map { y -> Point(x, y) } }
        .sumOf { p -> vectors.count { v -> searchFrom(p, v) } }
}

fun part2(input: List<String>): Int {
    fun check(p: Point): Boolean {
        val word1 = listOf(p + Point(-1, -1), p, p + Point(1, 1)).getWord(input)
        val word2 = listOf(p + Point(1, -1), p, p + Point(-1, 1)).getWord(input)
        return (word1 == "MAS" || word1 == "SAM") && (word2 == "MAS" || word2 == "SAM")
    }
    return input.indices.flatMap { x -> input[x].indices.map { y -> x to y } }
        .count { (x, y) -> check(Point(x, y)) }
}

fun main() {
    val input = File("input/day4.txt").readLines()
    println(part1(input)) // 2434
    println(part2(input)) // 1835
}