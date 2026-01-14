package com.maha.kotlin01.domain.usecase

import com.maha.kotlin01.domain.model.User
import com.maha.kotlin01.domain.repository.UserRepository

class GetRandomUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): Result<User> {
        return repository.getRandomUser()
    }
}
