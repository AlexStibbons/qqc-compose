package com.stibbons.qqc_compose.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

abstract class QueryFlowUseCase<out ReturnType, in Params> where ReturnType : Flow<Any> {
    private val mainJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + mainJob)
    abstract suspend fun run(params: Params?): ReturnType

    operator fun invoke(
        params: Params? = null
    ) {
        uiScope.launch { run(params).flowOn(Dispatchers.IO) }
    }

    fun cancel() {
        if (mainJob.isActive)
            mainJob.cancel()
    }

    /**
     * but will the flow be cancelled? do we have structured concurrency here??
     */

}
