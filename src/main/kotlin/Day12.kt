package day12

import util.print
import java.awt.Point
import java.io.File

data class Tile(val x: Int, val y: Int, val value: Char)

val grid = File("input/day12.txt").readLines()
    .mapIndexed { y, row -> row.mapIndexed { x, c -> Tile(x, y, c) } }

fun Tile.neighbours() =
    listOf(
        grid.getOrNull(y)?.getOrNull(x - 1),
        grid.getOrNull(y)?.getOrNull(x + 1),
        grid.getOrNull(y + 1)?.getOrNull(x),
        grid.getOrNull(y - 1)?.getOrNull(x),
    )

fun main() {
    val seen = mutableSetOf<Tile>()

    fun findRegion(tile: Tile, region: MutableSet<Tile>) {
        region.add(tile)
        tile.neighbours().filterNotNull().filter { it !in region && it.value == tile.value }.forEach {
            findRegion(it, region)
        }
    }

    fun price(region: Set<Tile>) =
        region.sumOf { tile ->
            tile.neighbours().count { it == null || it.value != tile.value }
        } * region.size

    fun price2(region: Set<Tile>): Int {
        val fencesLeft = mutableListOf<Point>()
        val fencesRight = mutableListOf<Point>()
        val fencesUp = mutableListOf<Point>()
        val fencesDown = mutableListOf<Point>()
        region.forEach { tile ->
            with(tile) {
                val left = grid.getOrNull(y)?.getOrNull(x - 1)
                val right = grid.getOrNull(y)?.getOrNull(x + 1)
                val up = grid.getOrNull(y + 1)?.getOrNull(x)
                val down = grid.getOrNull(y - 1)?.getOrNull(x)
                if (left?.value != value) fencesLeft.add(Point(x, y))
                if (right?.value != value) fencesRight.add(Point(x, y))
                if (up?.value != value) fencesUp.add(Point(x, y))
                if (down?.value != value) fencesDown.add(Point(x, y))
            }
        }
        val l = fencesUp.toList().groupBy { it.y }.mapValues { (_, row) ->
            val adjacent = row.sortedBy { it.x }.sortedBy { it.y }.zipWithNext().count { (a, b) -> (b.x - a.x == 1) }
            row.size - adjacent
        }.values.sum()
        val r = fencesDown.toList().groupBy { it.y }.mapValues { (_, row) ->
            val adjacent = row.sortedBy { it.x }.sortedBy { it.y }.zipWithNext().count { (a, b) -> (b.x - a.x == 1) }
            row.size - adjacent
        }.values.sum()
        val u = fencesLeft.toList().groupBy { it.x }.mapValues { (_, col) ->
            val adjacent = col.sortedBy { it.y }.zipWithNext().count { (a, b) -> (b.y - a.y == 1) }
            col.size - adjacent
        }.values.sum()
        val d = fencesRight.toList().groupBy { it.x }.mapValues { (_, col) ->
            val adjacent = col.sortedBy { it.y }.zipWithNext().count { (a, b) -> (b.y - a.y == 1) }
            col.size - adjacent
        }.values.sum()
        return region.size * (d + u + r + l)
    }

    grid.flatten().sumOf { tile ->
        if (tile !in seen) {
            val region = mutableSetOf<Tile>()
            findRegion(tile, region)
            seen.addAll(region)
            price2(region)
        } else {
            0
        }
    }.print() // 841934

}