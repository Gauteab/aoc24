import java.awt.Point
import java.io.File

operator fun Point.plus(other: Point): Point = Point(x + other.x, y + other.y)
fun List<Point>.getWord(input: List<String>) = mapNotNull { input.getOrNull(it.x)?.getOrNull(it.y) }.joinToString("")
fun List<String>.getPoints() = indices.flatMap { x -> this[x].indices.map { y -> Point(x, y) } }

fun part1(input: List<String>): Int {
    val vectors = listOf(Point(0, 1), Point(1, 0), Point(1, 1), Point(1, -1))
    return input.getPoints().sumOf { p ->
        vectors.count { v ->
            val word = listOf(p, p + v, p + v + v, p + v + v + v).getWord(input)
            word == "XMAS" || word == "SAMX"
        }
    }
}

fun part2(input: List<String>): Int {
    return input.getPoints().count { p ->
        val word1 = listOf(p + Point(-1, -1), p, p + Point(1, 1)).getWord(input)
        val word2 = listOf(p + Point(1, -1), p, p + Point(-1, 1)).getWord(input)
        (word1 == "MAS" || word1 == "SAM") && (word2 == "MAS" || word2 == "SAM")
    }
}

fun main() {
    val input = File("input/day4.txt").readLines()
    println(part1(input)) // 2434
    println(part2(input)) // 1835
}