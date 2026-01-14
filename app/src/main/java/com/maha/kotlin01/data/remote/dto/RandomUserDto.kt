package com.maha.kotlin01.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RandomUserResponse(
    val results: List<RandomUserDto>
)

data class RandomUserDto(
    val login: Login,
    val name: Name,
    val dob: Dob,
    val picture: Picture
)

data class Login(
    val uuid: String
)

data class Name(
    val first: String,
    val last: String
)

data class Dob(
    val date: String,
    @SerializedName("age") val age: Int
)

data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String
)
