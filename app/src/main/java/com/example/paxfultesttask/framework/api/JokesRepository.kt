package com.example.paxfultesttask.framework.api

import com.example.paxfultesttask.core.domain.Joke
import org.koin.dsl.module

val jokesRepositoryModule = module {
    factory { JokesRepository() }
}

class JokesRepository {
    companion object {
        var client: IcndbApiService = IcndbApiFactory.getInstance()
        suspend fun getJokes(): List<Joke> = client.getJokes().value
        suspend fun getJokesBySurnameAndName(name: String, lastName: String): List<Joke> =
            client.getJokeByNameSurname(name,lastName).value
    }

}