import java.io.File
import kotlin.math.abs

fun main() {
    val input = File("input/day9.txt").readText().toList().map { it.digitToInt() }
    val memory = input.flatMapIndexed { i, n ->
        if (i % 2 == 0) {
            (0 until n).map { i / 2 }
        } else {
            (0 until n).map { -1 }
        }
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
    val checksum: Long = memory.mapIndexed { i, v -> if (v < 0L) 0L else i.toLong() * v.toLong() }.sum()
    // 1368861652 - overflow :(
    println(checksum) // 6310675819476
}