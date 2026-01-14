package com.maha.kotlin01.di

import com.maha.kotlin01.data.remote.api.RandomUserApi
import com.maha.kotlin01.data.repository.UserRepositoryImpl
import com.maha.kotlin01.domain.repository.UserRepository
import com.maha.kotlin01.domain.usecase.GetRandomUserUseCase
import com.maha.kotlin01.presentation.home.HomeViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppModule {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(RandomUserApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val api: RandomUserApi by lazy {
        retrofit.create(RandomUserApi::class.java)
    }

    private val repository: UserRepository by lazy {
        UserRepositoryImpl(api)
    }

    private val getRandomUserUseCase: GetRandomUserUseCase by lazy {
        GetRandomUserUseCase(repository)
    }

    fun provideHomeViewModel(): HomeViewModel {
        return HomeViewModel(getRandomUserUseCase)
    }
}
