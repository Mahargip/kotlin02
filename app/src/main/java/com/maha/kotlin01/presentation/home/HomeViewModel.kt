package com.maha.kotlin01.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maha.kotlin01.domain.usecase.GetRandomUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getRandomUserUseCase: GetRandomUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    fun addUser() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            getRandomUserUseCase().fold(
                onSuccess = { user ->
                    _state.update { currentState ->
                        currentState.copy(
                            users = currentState.users + user,
                            isLoading = false
                        )
                    }
                },
                onFailure = { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Unknown error occurred"
                        )
                    }
                }
            )
        }
    }

    fun removeUser() {
        _state.update { currentState ->
            if (currentState.users.isNotEmpty()) {
                currentState.copy(users = currentState.users.dropLast(1))
            } else {
                currentState
            }
        }
    }
}
