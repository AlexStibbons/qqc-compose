package com.stibbons.qqc_compose.presentation

import com.stibbons.qqc_compose.domain.ItemDomain

internal data class ItemPresentation(
    val ordinal: Int
)

internal fun ItemDomain.toItemPresentation() = ItemPresentation(
    ordinal = this.ordinal
)