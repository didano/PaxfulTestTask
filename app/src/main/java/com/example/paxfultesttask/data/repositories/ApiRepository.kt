package com.example.paxfultesttask.data.repositories

import com.example.paxfultesttask.data.models.Joke
import com.example.paxfultesttask.domain.api.IcndbApiService

class ApiRepository(private val api: IcndbApiService) : IApiRepository {
    override suspend fun getJokes(): List<Joke> {
        val response = api.getJokes()
        if (response.isSuccessful) {
            return response.body()!!.value
        }
        return emptyList()
    }

    override suspend fun getNamedJokes(firstName: String, lastName: String): List<Joke> {
        val response = api.getNamedJokes(firstName,lastName)
        if (response.isSuccessful) {
            return response.body()!!.value
        }
        return emptyList()
    }
}