package com.example.paxfultesttask.domain.api

data class ErrorResponse(
    val error_description: String // this is the translated error shown to the user directly from the API
)