package com.arctouch.codechallenge.base.extensions

import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT_US = "yyyy-MM-dd"
private const val DATE_FORMAT_BR = "dd/MM/yyyy"

fun localeBrazil() = Locale("pt", "BR")

fun String?.dateFromUsToBr(): String {
    this?.let {
        return try {
            val date = SimpleDateFormat(DATE_FORMAT_US, localeBrazil()).parse(it)
            SimpleDateFormat(DATE_FORMAT_BR, localeBrazil()).format(date)
        } catch (e: Exception) {
            this
        }
    }
    return ""
}