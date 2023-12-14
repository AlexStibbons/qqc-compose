package com.stibbons.qqc_compose.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
abstract class UseCase<InputType, ReturnType>(
   protected val dispatcher: CoroutineDispatcher = Dispatchers.IO
) where InputType : Any? {
    abstract suspend fun run(input: InputType? = null): ReturnType
}