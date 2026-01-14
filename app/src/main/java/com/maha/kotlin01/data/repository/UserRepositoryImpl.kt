package com.maha.kotlin01.data.repository

import com.maha.kotlin01.data.remote.api.RandomUserApi
import com.maha.kotlin01.data.remote.dto.RandomUserDto
import com.maha.kotlin01.domain.model.User
import com.maha.kotlin01.domain.repository.UserRepository
import java.text.SimpleDateFormat
import java.util.Locale

class UserRepositoryImpl(
    private val api: RandomUserApi
) : UserRepository {

    override suspend fun getRandomUser(): Result<User> {
        return try {
            val response = api.getRandomUser()
            val userDto = response.results.firstOrNull()

            if (userDto != null) {
                Result.success(userDto.toUser())
            } else {
                Result.failure(Exception("No user data received"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun RandomUserDto.toUser(): User {
        val fullName = "${name.first} ${name.last}"
        val dateOfBirth = formatDate(dob.date)

        return User(
            id = login.uuid,
            fullName = fullName,
            dateOfBirth = dateOfBirth,
            avatarUrl = picture.large
        )
    }

    private fun formatDate(isoDate: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            val date = inputFormat.parse(isoDate)
            date?.let { outputFormat.format(it) } ?: isoDate
        } catch (e: Exception) {
            isoDate
        }
    }
}
