package com.stibbons.qqc_compose.domain

import com.stibbons.qqc_compose.data.Repository
import kotlinx.coroutines.flow.Flow

internal class FetchUseCase(
    val repository: Repository
): QueryFlowUseCase<Flow<Int>, Unit>() {


    override suspend fun run(params: Unit?): Flow<Int> {
        return repository.fetchData()
    }


}


