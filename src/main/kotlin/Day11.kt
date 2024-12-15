package day11

import util.print
import java.io.File

fun Long.applyRule() = when {
    this == 0L -> listOf(1L)
    this.toString().length % 2 == 0 -> {
        val s = toString()
        listOf(s.take(s.length / 2).toLong(), s.drop(s.length / 2).toLong())
    }

    else -> listOf(this * 2024)
}

fun main() {
    val cache = (0..9).map { mutableMapOf<Int, List<Long>>() }
    val cacheSize = 37
    (0..9).forEach { i ->
        var list = listOf(i.toLong())
        repeat(cacheSize) {
            list = list.flatMap { it.applyRule() }
            cache[i][it + 1] = list
        }
        println("cache $i built")
    }

    val input = File("input/day11.txt").readLines()[0].split(' ').map { it.toLong() }
    fun countStones(value: Long, count: Long, blinksLeft: Int): Long {
        if (blinksLeft == 0) return count
        if (value < 10L && blinksLeft <= cacheSize) {
            return cache[value.toInt()][blinksLeft]!!.size.toLong() + count - 1
        }

        return if (value == 0L) countStones(1L, count, blinksLeft - 1)
        else if (value.toString().length % 2 == 0) {
            val s = value.toString()
            val a = s.take(s.length / 2).toLong()
            val b = s.drop(s.length / 2).toLong()
            val newCount = countStones(a, count + 1, blinksLeft - 1)
            countStones(b, newCount, blinksLeft - 1)
        } else {
            countStones(value * 2024, count, blinksLeft - 1)
        }
    }

    input.sumOf {
        countStones(it, 1, 75)
    }.print() // 220566831337810

}