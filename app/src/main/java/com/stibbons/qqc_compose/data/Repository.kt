package com.stibbons.qqc_compose.data

import kotlinx.coroutines.flow.Flow

internal interface Repository {
    suspend fun fetchData(): Flow<Int>
}

internal class RepoImpl(
    private val someService: SomeService
) : Repository {
    override suspend fun fetchData(): Flow<Int> = someService.fetchData()
}