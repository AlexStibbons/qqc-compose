package com.stibbons.qqc_compose.domain

import android.text.InputType
import com.stibbons.qqc_compose.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

internal class FetchUseCase(
    private val repository: Repository
): QueryFlowUseCase<Flow<Int>, Unit>() {
    override suspend fun run(params: Unit?): Flow<Int> {
        return repository.fetchData()
    }
}

abstract class UseCase<InputType, ReturnType> where InputType : Any? {
    abstract suspend fun execute(input: InputType? = null): ReturnType
}

internal class Fetch2(
    private val repository: Repository
): UseCase<Unit, Flow<Int>>() {
    override suspend fun execute(input: Unit?): Flow<Int> {
        return repository.fetchData().flowOn(Dispatchers.IO)
    }


}


