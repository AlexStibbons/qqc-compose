package com.stibbons.qqc_compose.presentation

import android.os.Parcelable
import com.stibbons.qqc_compose.R
import com.stibbons.qqc_compose.domain.MsgItemDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class MsgItemPresentation(
    val id: Int,
    val isReceived: Boolean,
    val text: Int,
    val isDone: Boolean = false
): Parcelable

internal fun MsgItemDomain.toPresentation() = MsgItemPresentation(
    this.ordinal,
    isReceived = this.ordinal.isReceived,
    msgText[this.ordinal] ?: R.string.error,
    this.ordinal == -1

)

internal val msgText = mapOf(
    -1 to R.string.complete,
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
    return if (this == -1) false else this % 2 != 0
}
