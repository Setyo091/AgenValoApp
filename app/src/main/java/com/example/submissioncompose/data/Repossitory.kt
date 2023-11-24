package com.example.submissioncompose.data

import com.example.submissioncompose.model.Agen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map


class Repossitory {
    private val agens = mutableListOf<Agen>()

    init {
        if (agens.isEmpty()) {
            FakeDataResource.dummyMembers.forEach {
                agens.add(it)
            }
        }
    }
    fun searchAgens(query: String): Flow<List<Agen>> {
        return getAllAgen()
            .map { favoriteAgens ->
                favoriteAgens.filter {
                    it.name.contains(query, ignoreCase = true)
                }
            }
    }

    fun getAllAgen(): Flow<List<Agen>> {
        return flowOf(agens)
    }
    fun getFavoriteAgen(): Flow<List<Agen>> {
        return getAllAgen()
            .map { agens.filter { it.isFavoriteAgen }
            }
    }

    fun getMemberById(id: Int): Flow<Agen> {
        return  flowOf(agens.first {it.id == id})
    }
    fun updateFavoriteAgen(rewardId: Int, isFavorite: Boolean): Flow<Boolean> {
        val index = agens.indexOfFirst { it.id == rewardId }
        val result = if (index >= 0) {
            val favoriteAgen = agens[index]
            agens[index] =
                favoriteAgen.copy(isFavoriteAgen = isFavorite)
            true
        } else {
            false
        }
        return flowOf(result)
    }
    companion object {
        @Volatile
        private var instance: Repossitory? = null

        fun getInstance(): Repossitory =
            instance ?: synchronized(this) {
                Repossitory().apply {
                    instance = this
                }
            }
    }
}