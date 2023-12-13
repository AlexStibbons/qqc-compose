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
        params: Params? = null,
        onResult: (ReturnType) -> Unit = {}
    ) {
        uiScope.launch {
            val result: ReturnType = run(params).flowOn(Dispatchers.IO) as ReturnType
            onResult(result)
        }
    }

    fun cancel() {
        if (mainJob.isActive)
            mainJob.cancel()
    }

}

/**
 * but will the flow be cancelled? do we have structured concurrency here??
 *
 * i guess it should?
 * if the parent job is cancelled, the children are cancelled too.
 *
 * Flows adhere to the general cooperative cancellation of coroutines.
 * As usual, flow collection can be cancelled when the flow is suspended in a
 * cancellable suspending function (like delay)
 * https://kotlinlang.org/docs/flow.html#flow-cancellation-basics
 */
