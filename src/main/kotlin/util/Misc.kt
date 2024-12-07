package util

fun <T> T.print(): Unit = println(this)
fun <T> T.printed(): T = also { println(this) }
