package day6

import util.mapNotNullParallel
import java.awt.Point
import java.io.File
import kotlin.time.measureTime

operator fun Point.plus(other: Point): Point = Point(x + other.x, y + other.y)
fun Point.rotate() = Point(y, -x)
operator fun List<String>.get(p: Point) = this[p.y][p.x]

private val directionVectors = mapOf(
    '<' to Point(-1, 0),
    '>' to Point(1, 0),
    'v' to Point(0, -1),
    '^' to Point(0, 1),
)

fun part1(grid: List<String>): Set<Point> {
    val points = grid.flatMapIndexed { y, row -> row.mapIndexed { x, _ -> Point(x, y) } }
    var position = points.find { grid[it] in directionVectors }!!
    var direction = directionVectors[grid[position]]!!
    val visited = mutableSetOf<Point>()
    while (true) {
        visited += position
        val ahead = position + direction
        if (grid.getOrNull(ahead.y)?.getOrNull(ahead.x) == null) return visited
        if (grid[ahead] == '#') direction = direction.rotate()
        position += direction
    }
}

fun wouldCycle(startPosition: Point, startDirection: Point, newObstacle: Point, grid: List<String>): Boolean {
    var position = startPosition
    var direction = startDirection
    val visited = mutableSetOf<Pair<Point, Point>>()
    while (true) {
        if ((position to direction) in visited) return true
        visited.add(position to direction)
        val ahead = position + direction
        if (grid.getOrNull(ahead.y)?.getOrNull(ahead.x) == null) return false
        if (grid[ahead] == '#' || ahead == newObstacle) {
            direction = direction.rotate()
            continue
        }
        position += direction
    }
}

fun part2(grid: List<String>, visited: Set<Point>): Int {
    val points = grid.flatMapIndexed { y, row -> row.mapIndexed { x, _ -> Point(x, y) } }
    val start = points.find { grid[it] in directionVectors }!!
    val direction = directionVectors[grid[start]]!!
    return visited.mapNotNullParallel { candidate ->
        candidate
            .takeIf { candidate != start }
            ?.takeIf { wouldCycle(start, direction, candidate, grid) }
    }.toSet().size
}

fun main() = measureTime {
    val grid = File("input/day6.txt").readLines().reversed()
    val visited = part1(grid).also { println(it.size) } // 5080
    println(part2(grid, visited)) // 1919
}.run(::println) // 750ms