package com.example.paxfultesttask.domain.interactors.api

import com.example.paxfultesttask.data.models.Joke
import com.example.paxfultesttask.data.repositories.IApiRepository

class ApiInteractor(val repository:IApiRepository):IApiInteractor {

    override suspend fun getAllJokes(): List<Joke> {
        return  repository.getJokes()
    }

    override suspend fun getAllNamedJokes(firstName: String, lastName: String): List<Joke> {
        if(firstName.isEmpty()&& lastName.isEmpty()){
            return repository.getNamedJokes("Chuck","Norris")
        } else if (firstName.isEmpty()){
            return repository.getNamedJokes("Chuck",lastName)
        } else if (lastName.isEmpty()) {
            return repository.getNamedJokes(firstName,"Norris")
        } else {
            return repository.getNamedJokes(firstName,lastName)
        }
    }
}