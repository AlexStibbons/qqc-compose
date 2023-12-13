package com.stibbons.qqc_compose.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stibbons.qqc_compose.domain.Fetch2
import com.stibbons.qqc_compose.domain.FetchUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal class MainViewModel(
    private val fetchData: FetchUseCase,
    private val fetchD2: Fetch2
): ViewModel() {

    private var _state = MutableLiveData<ViewState>()
    val screenState: LiveData<ViewState> get() = _state

    fun fetchData() {
        fetchData { resultFlow ->
            resultFlow
                .onEach { _state.value = ViewState.Item(it) }
                .launchIn(viewModelScope) // dumb, we're wrapping a doubly wrapped coroutine in a coroutine
        }
    }

    override fun onCleared() {
        fetchData.cancel() // serves no purpose, viewModelScope takes care of it IF it's parent to mainJob
        super.onCleared()
    }

    fun fetch2() = viewModelScope.launch {
        fetchD2.execute()
            .onEach { _state.value = ViewState.Item(it) }
            .collect()
    }

    sealed class ViewState {
        data class Item(val number: Int) : ViewState()
    }
}