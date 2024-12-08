package day8

import java.awt.Point
import java.io.File
import kotlin.math.abs

operator fun Point.plus(other: Point): Point = Point(x + other.x, y + other.y)
operator fun Point.minus(other: Point): Point = Point(x - other.x, y - other.y)
operator fun List<List<Char>>.get(p: Point) = getOrNull(p.y)?.getOrNull(p.x)


fun main() {
    val input = File("input/day8.txt").readLines().map { it.toList() }
    val groups = input.flatMapIndexed { y, row -> row.mapIndexed { x, c -> c to Point(x, y) } }.groupBy { it.first }.filter { it.key !in ".#" }
    val antinodes = mutableSetOf<Point>()
    fun addAlongVector(start : Point, v:Point) {
        var p = start
        while (input[p] != null) {
            antinodes.add(p)
            p += v
        }
    }
    groups.forEach { (k, antennas) ->
        antennas.forEach { (_, a) ->
            antennas.forEach { (_, b) ->
                if (a != b) {
                    val v = a - b
                    val (l, r) = if (a + v == b) (a to b) else (b to a)
                    addAlongVector(l, Point(0,0) - v)
                    addAlongVector(r, v)
                }
            }
        }
    }
    println(antinodes.filter { input[it] != null }.size)
}