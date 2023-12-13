package com.stibbons.qqc_compose.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.onEach

private val MOCK_DATA = (1..10)

internal class SomeService {
    suspend fun fetchData(): Flow<Int> = MOCK_DATA.asFlow().onEach { delay(1000L) }
}