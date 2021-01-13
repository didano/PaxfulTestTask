package com.example.paxfultesttask.core.domain

data class JokeResponse(
    var type: String = "",
    var value: List<Joke>
)