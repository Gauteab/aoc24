package day11

import util.print

fun Long.applyRule() = when {
    this == 0L -> listOf(1L)
    this.toString().length % 2 == 0 -> {
        val s = toString()
        listOf(s.take(s.length / 2).toLong(), s.drop(s.length / 2).toLong())
    }

    else -> listOf(this * 2024)
}

fun main() {
    var list = listOf(0L)
    val cache = mutableMapOf<Int, List<Long>>()
    repeat(40) {
        list = list.flatMap { it.applyRule() }
        cache[it + 1] = list
    }
    println("cache built")

//    val input = File("input/day11.txt").readLines()[0].split(' ').map { it.toLong() }
    val input = listOf(125L, 17L)
    fun countStones(value: Long, count: Long, blinksLeft: Int): Long {
        if (blinksLeft == 0) return count
        return when {
            value == 0L -> {
                if (blinksLeft <= cache.size)
                    cache[blinksLeft]!!.size.toLong() + count - 1
                else
                    countStones(1L, count, blinksLeft - 1)
            }

            value.toString().length % 2 == 0 -> {
                val s = value.toString()
                val a = s.take(s.length / 2).toLong()
                val b = s.drop(s.length / 2).toLong()
                val newCount = countStones(a, count + 1, blinksLeft - 1)
                countStones(b, newCount, blinksLeft - 1)
            }

            else -> {
                countStones(value * 2024, count, blinksLeft - 1)
            }
        }
    }
    input.sumOf {
        println(it)
        countStones(it, 1, 75)
    }.print()
//
    println()

}