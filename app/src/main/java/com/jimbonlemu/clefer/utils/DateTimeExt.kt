package com.jimbonlemu.clefer.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun String.toTime(): String {
    val formatter = DateTimeFormatter.ISO_INSTANT
    val instant = Instant.from(formatter.parse(this))
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    return DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss").format(localDateTime)
}
