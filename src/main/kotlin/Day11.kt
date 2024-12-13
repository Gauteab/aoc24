package day11

import util.printed
import java.io.File

fun main() {
    var list = File("input/day11.txt").readLines()[0].split(' ').map { it.toLong() }
    fun Long.applyRule() = when {
        this == 0L -> listOf(1L)
        this.toString().length % 2 == 0 -> {
            val s = toString()
            listOf(s.take(s.length / 2).toLong(), s.drop(s.length / 2).toLong())
        }

        else -> listOf(this * 2024)
    }
    repeat(25) {
        list = list.flatMap { it.applyRule() }
    }
    list.size.printed()
}