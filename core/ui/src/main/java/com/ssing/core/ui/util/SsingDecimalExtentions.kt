package com.ssing.core.ui.util

import java.text.DecimalFormat

fun Int.DecimalFormatter(): String {
    return DecimalFormat("#,###").format(this)
}