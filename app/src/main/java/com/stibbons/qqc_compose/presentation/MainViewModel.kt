package com.stibbons.qqc_compose.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stibbons.qqc_compose.domain.FetchData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class MainViewModel(
    private val fetchData: FetchData
): ViewModel() {

    private var _state = MutableLiveData<ViewState>()
    val screenState: LiveData<ViewState> get() = _state

    fun fetchData() = viewModelScope.launch {
        fetchData.run()
            .onEach { result ->
                result.toPresentation().let {
                    _state.value = ViewState.Item(it)
                }
            }
            .collect()
    }

    sealed class ViewState {
        data class Item(val data: MsgItemPresentation) : ViewState()
    }
}