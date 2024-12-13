import util.print
import util.printed
import java.io.File

data class Tile(val x: Int, val y: Int, val value: Int)

fun main() {
    val grid = File("input/day10.txt").readLines()
        .mapIndexed { y, row -> row.mapIndexed { x, v -> Tile(x, y, if (v == '.') -1 else v.digitToInt()) } }
    val starts = grid.flatten().filter { it.value == 0 }
    fun reachableTops(tile: Tile, seen: Set<Tile>): List<Tile> {
        if (tile.value == 9) return listOf(tile)
        return listOfNotNull(
            grid.getOrNull(tile.y)?.getOrNull(tile.x - 1),
            grid.getOrNull(tile.y)?.getOrNull(tile.x + 1),
            grid.getOrNull(tile.y + 1)?.getOrNull(tile.x),
            grid.getOrNull(tile.y - 1)?.getOrNull(tile.x),
        )
            .filter { it !in seen && it.value == tile.value + 1 }
            .flatMap { reachableTops(it, seen + setOf(tile)) }
    }
    starts.sumOf { reachableTops(it, emptySet()).toSet().size.printed() }.print()
}