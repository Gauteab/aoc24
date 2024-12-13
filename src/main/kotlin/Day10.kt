package day10

import util.print
import java.io.File

data class Tile(val x: Int, val y: Int, val value: Int)

val grid = File("input/day10.txt").readLines()
    .mapIndexed { y, row -> row.mapIndexed { x, v -> Tile(x, y, if (v == '.') -1 else v.digitToInt()) } }
val starts = grid.flatten().filter { it.value == 0 }

fun Tile.neighbours() =
    listOfNotNull(
        grid.getOrNull(y)?.getOrNull(x - 1),
        grid.getOrNull(y)?.getOrNull(x + 1),
        grid.getOrNull(y + 1)?.getOrNull(x),
        grid.getOrNull(y - 1)?.getOrNull(x),
    )

fun reachableTops(tile: Tile, seen: Set<Tile>): List<Tile> {
    if (tile.value == 9) return listOf(tile)
    return tile.neighbours()
        .filter { it !in seen && it.value == tile.value + 1 }
        .flatMap { reachableTops(it, seen + setOf(tile)) }
}

fun rating(tile: Tile, seen: Set<Tile>): Int {
    if (tile.value == 9) return 1
    return tile.neighbours()
        .filter { it !in seen && it.value == tile.value + 1 }
        .sumOf { rating(it, seen + setOf(tile)) }
}

fun main() {
    starts.sumOf { reachableTops(it, emptySet()).toSet().size }.print() // 674
    starts.sumOf { rating(it, emptySet()) }.print() // 1372
}