import java.io.File
import kotlin.math.abs


fun part1(): Long {
    val input = File("input/day9.txt").readText().toList().map { it.digitToInt() }
    val memory = input.flatMapIndexed { i, n ->
        if (i % 2 == 0) (0 until n).map { i / 2 } else (0 until n).map { -1 }
    }.toMutableList()
    var i = 0
    var j = memory.size - 1
    while (i < memory.size && j >= 0 && i < j) {
        if (memory[i] != -1) {
            i++
        } else if (memory[j] == -1) {
            j--
        } else {
            memory[i++] = memory[j]
            memory[j--] = -1
        }
    }
    return memory.mapIndexed { i, v -> if (v < 0L) 0L else i.toLong() * v.toLong() }.sum()
}

sealed interface Block {
    var size: Int
}

data class FileBlock(val id: Long, override var size: Int) : Block
data class FreeBlock(override var size: Int) : Block

fun part2(): Long {
    val allocations = File("input/day9.txt").readText().toList().mapIndexed { i, d ->
        if (i % 2 == 0) FileBlock(i / 2L, d.digitToInt()) else FreeBlock(d.digitToInt())
    }.toMutableList()
    var j = allocations.size - 1
    while (j >= 0) {
        if (allocations[j] !is FileBlock) {
            j--
            continue
        }
        val i = allocations.indexOfFirst { it is FreeBlock && it.size >= allocations[j].size }
        if (i < j && i > -1) {
            allocations[i].size -= allocations[j].size
            allocations.add(i, allocations[j])
            allocations[j + 1] = FreeBlock(allocations[j + 1].size)
        }
        j--
    }

    val memory = allocations
        .filter { it is FileBlock || it.size > 0 }.toMutableList()
        .flatMap { block -> (1..block.size).map { if (block is FileBlock) block.id else -1 } }
    val checksum: Long = memory.mapIndexed { i, v -> if (v < 0L) 0L else i.toLong() * v }.sum()
    return checksum
}

fun main() {
    println(part1()) // 6310675819476
    println(part2()) // 6335972980679
}