package day6

import java.awt.Point
import java.io.File

operator fun Point.plus(other: Point): Point = Point(x + other.x, y + other.y)
fun Point.rotate() = Point(y, -x)
operator fun List<String>.get(p: Point) = this[p.y][p.x]
fun direction(guard: Char) = when (guard) {
    '<' -> Point(-1, 0)
    '>' -> Point(1, 0)
    'v' -> Point(0, -1)
    '^' -> Point(0, 1)
    else -> error("invalid guard")
}

fun part1(grid: List<String>): Int {
    val points = grid.flatMapIndexed { y, row -> row.mapIndexed { x, _ -> Point(x, y) } }
    var position = points.find { grid[it] in listOf('<', '>', '^', 'v') }!!
    val visited = mutableSetOf<Point>()
    var currentDirection = direction(grid[position])
    while (true) {
        visited += position
        val ahead = position + currentDirection
        if (grid.getOrNull(ahead.y)?.getOrNull(ahead.x) == null) return visited.size
        if (grid[ahead] == '#') currentDirection = currentDirection.rotate()
        position += currentDirection
    }
}

fun wouldCycle(startPosition: Point, startDirection: Point, newObstacle: Point, grid: List<String>): Boolean {
    var position = startPosition
    var currentDirection = startDirection
    val visited = mutableSetOf<Pair<Point, Point>>()
    while (true) {
        if ((position to currentDirection) in visited) return true
        visited.add(position to currentDirection)
        val ahead = position + currentDirection
        if (grid.getOrNull(ahead.y)?.getOrNull(ahead.x) == null) return false
        if (grid[ahead] == '#' || ahead == newObstacle) {
            currentDirection = currentDirection.rotate()
            continue
        }
        position += currentDirection
    }
}

fun part2(grid: List<String>): Int {
    val points = grid.flatMapIndexed { y, row -> row.mapIndexed { x, _ -> Point(x, y) } }
    val start = points.find { grid[it] in listOf('<', '>', '^', 'v') }!!
    var position = start
    var currentDirection = direction(grid[position])
    val obstructionSites = mutableSetOf<Point>()
    while (true) {
        val ahead = position + currentDirection
        if (grid.getOrNull(ahead.y)?.getOrNull(ahead.x) == null) return obstructionSites.size
        if (grid[ahead] == '#') {
            currentDirection = currentDirection.rotate()
            continue
        }
        if (ahead != start && wouldCycle(position, currentDirection, newObstacle = ahead, grid = grid)) {
            obstructionSites.add(ahead)
        }
        position += currentDirection
    }
}


fun main() {
    val gridSample = File("input/day6sample.txt").readLines().reversed()
    part1(gridSample).also { println(it) }
    part2(gridSample).also { println(it) }
    val grid = File("input/day6.txt").readLines().reversed()
    println(part1(grid)) // 5080
    println(part2(grid)) // 1294, 1439 too low, 3442, 3237, 3236, 2034, 1983, 2042, 1990, 1993, 1990! wrong :(
    // 2040?
}