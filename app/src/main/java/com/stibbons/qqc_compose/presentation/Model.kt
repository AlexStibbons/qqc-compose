package com.stibbons.qqc_compose.presentation

import com.stibbons.qqc_compose.R
import com.stibbons.qqc_compose.domain.MsgItemDomain

data class MsgItemPresentation(
    val isReceived: Boolean,
    val text: Int
)

internal fun MsgItemDomain.toPresentation() = MsgItemPresentation(
    isReceived = this.ordinal.isReceived,
    msgText[this.ordinal] ?: R.string.error
)

internal val msgText = mapOf(
    1 to R.string.one,
    2 to R.string.two,
    3 to R.string.three,
    4 to R.string.four,
    5 to R.string.five,
    6 to R.string.six,
    7 to R.string.seven,
    8 to R.string.eight,
    9 to R.string.nine,
    10 to R.string.ten
)

internal val Int.isReceived: Boolean get() {
    return this % 2 != 0
}
