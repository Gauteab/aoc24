package util

fun <T> T.print(): Unit = println(this)
fun <T> T.printed(): T = also { println(this) }

fun Long.orderOf10(): Int {
    var order = 1
    var num = this
    while (num != 0L) {
        num /= 10
        order *= 10
    }
    return order
}
