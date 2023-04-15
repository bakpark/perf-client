package common

import java.util.*

fun String.addUUID(len: Int): String {
    return this + UUID.randomUUID().toString().substring(0, len)
}

fun randomInt(upperBound: Int): Int {
    return Random().nextInt(upperBound)
}