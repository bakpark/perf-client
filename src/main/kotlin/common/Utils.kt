package common

import java.time.Duration
import java.time.LocalDateTime
import java.util.*

fun String.addUUID(len: Int): String {
    return this + UUID.randomUUID().toString().substring(0, len)
}

fun randomInt(upperBound: Int): Int {
    return Random().nextInt(upperBound)
}

fun LocalDateTime.minusNanos(ot: LocalDateTime): Long {
    return Duration.between(ot, this).toNanos()
}