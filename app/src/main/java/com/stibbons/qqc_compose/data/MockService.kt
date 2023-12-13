package com.stibbons.qqc_compose.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class SomeService {

    suspend fun fetchData(): Flow<Int> = flow {
        for (i in 1..10) {
            delay(1000L)
            emit(i)
        }
    }
}