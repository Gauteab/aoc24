package day6

import java.awt.Point
import java.io.File
import kotlin.time.measureTimedValue

operator fun Point.plus(other: Point): Point = Point(x + other.x, y + other.y)
fun Point.rotate() = Point(y, -x)
operator fun List<String>.get(p: Point) = this[p.y][p.x]

private val directionVectors = mapOf(
    '<' to Point(-1, 0),
    '>' to Point(1, 0),
    'v' to Point(0, -1),
    '^' to Point(0, 1),
)

fun part1(grid: List<String>): Int {
    val points = grid.flatMapIndexed { y, row -> row.mapIndexed { x, _ -> Point(x, y) } }
    var position = points.find { grid[it] in directionVectors }!!
    var direction = directionVectors[grid[position]]!!
    val visited = mutableSetOf<Point>()
    while (true) {
        visited += position
        val ahead = position + direction
        if (grid.getOrNull(ahead.y)?.getOrNull(ahead.x) == null) return visited.size
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

fun part2(grid: List<String>): Int {
    val points = grid.flatMapIndexed { y, row -> row.mapIndexed { x, _ -> Point(x, y) } }
    val start  = points.find { grid[it] in directionVectors }!!
    val direction = directionVectors[grid[start]]!!
    val obstructionSites = mutableSetOf<Point>()
    points.forEach { candidate ->
        if (candidate != start && wouldCycle(start, direction, newObstacle = candidate, grid = grid)) {
            obstructionSites.add(candidate)
        }
    }
    return obstructionSites.size
}


fun main() {
    val grid = File("input/day6.txt").readLines().reversed()
    println(part1(grid)) // 5080
    println(measureTimedValue { part2(grid) }) // 1919, 3.6s
}