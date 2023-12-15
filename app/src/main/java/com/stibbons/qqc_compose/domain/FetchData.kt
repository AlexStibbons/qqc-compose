package com.stibbons.qqc_compose.domain

import com.stibbons.qqc_compose.data.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
internal class FetchData(
    private val repository: Repository,
    private val coroutineDispatcher: CoroutineDispatcher
): UseCase<Unit, Flow<MsgItemDomain>>(coroutineDispatcher) {
    override suspend fun run(input: Unit?): Flow<MsgItemDomain> {
        return repository.fetchData()
            .map {result ->
                MsgItemDomain(result)
            }
            .flowOn(coroutineDispatcher)
    }
}

