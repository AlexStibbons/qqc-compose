package com.stibbons.qqc_compose.domain

import com.stibbons.qqc_compose.data.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

internal class FetchData(
    private val repository: Repository
): UseCase<Unit, Flow<ItemDomain>>() {
    override suspend fun run(input: Unit?): Flow<ItemDomain> {
        return repository.fetchData()
            .map {result ->
                ItemDomain(result)
            }.flowOn(dispatcher)
    }
}

data class ItemDomain(
    val ordinal: Int
)


