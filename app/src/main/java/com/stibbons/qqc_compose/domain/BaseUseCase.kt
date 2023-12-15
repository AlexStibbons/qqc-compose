package com.stibbons.qqc_compose.domain

import kotlinx.coroutines.CoroutineDispatcher
abstract class UseCase<InputType, ReturnType>(
   protected val dispatcher: CoroutineDispatcher
) where InputType : Any? {
    abstract suspend fun run(input: InputType? = null): ReturnType
}