package day8

import java.awt.Point
import java.io.File

operator fun Point.plus(other: Point): Point = Point(x + other.x, y + other.y)
operator fun Point.minus(other: Point): Point = Point(x - other.x, y - other.y)
operator fun List<List<Char>>.contains(p: Point) = getOrNull(p.y)?.getOrNull(p.x) != null

fun forEachAntennaPair(groups: Map<Char, List<Point>>, f: (Point, Point) -> Unit) {
    groups.values.forEach { antennas ->
        antennas.forEach { a ->
            antennas.forEach { b ->
                if (a != b) {
                    f(a, b)
                }
            }
        }
    }
}

fun part1(input: List<List<Char>>, groups: Map<Char, List<Point>>): Int {
    val antiNodes = mutableSetOf<Point>()
    forEachAntennaPair(groups) { a, b ->
        val v = a - b
        val (l, r) = if (a + v == b) (a to b) else (b to a)
        antiNodes.add(l - v)
        antiNodes.add(r + v)
    }
    return antiNodes.filter { it in input }.size
}


fun part2(input: List<List<Char>>, groups: Map<Char, List<Point>>): Int {
    val antiNodes = mutableSetOf<Point>()
    fun addAlongVector(start: Point, v: Point) = antiNodes.addAll(
        generateSequence(start) { it + v }.takeWhile { it in input }
    )
    forEachAntennaPair(groups) { a, b ->
        val v = a - b
        val (l, r) = if (a + v == b) (a to b) else (b to a)
        addAlongVector(l, Point(0, 0) - v)
        addAlongVector(r, v)
    }
    return antiNodes.filter { it in input }.size
}

fun main() {
    val input = File("input/day8.txt").readLines().map { it.toList() }
    val groups = input.flatMapIndexed { y, row -> row.mapIndexed { x, c -> c to Point(x, y) } }
        .groupBy { it.first }
        .mapValues { it.value.map { it.second } }
        .filter { it.key !in ".#" }
    println(part1(input, groups)) // 351
    println(part2(input, groups)) // 1259
}