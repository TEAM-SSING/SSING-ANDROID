package com.ssing.core.ui.util

import java.text.DecimalFormat

fun Int.toDecimalFormat(): String {
    return DecimalFormat("#,###").format(this)
}