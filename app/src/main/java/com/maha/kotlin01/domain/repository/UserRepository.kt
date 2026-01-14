package com.maha.kotlin01.domain.repository

import com.maha.kotlin01.domain.model.User

interface UserRepository {
    suspend fun getRandomUser(): Result<User>
}