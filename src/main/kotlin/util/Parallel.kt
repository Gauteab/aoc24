package util

import kotlin.concurrent.thread

inline fun <T, reified E> Collection<T>.mapNotNullParallel(crossinline f: (T) -> E): List<E> {
    val cores = Runtime.getRuntime().availableProcessors()
    val chunks = size / cores
    val output = Array<E?>(this.size) { null }
    chunked(chunks).flatMapIndexed { i, chunk ->
        chunk.mapIndexed { j, x ->
            thread {
                output[i * chunks + j] = f(x)
            }
        }
    }.forEach { it.join() }
    return output.mapNotNull { it }
}