package com.stibbons.qqc_compose.domain

val <T> T.exhaustive: T
    get() = this
internal data class MsgItemDomain(
    val ordinal: Int
)
