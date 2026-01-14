package com.maha.kotlin01.presentation.home

import com.maha.kotlin01.domain.model.User

data class HomeState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)