package com.example.submissioncompose.data

import com.example.submissioncompose.model.Agen
import kotlinx.coroutines.flow.flowOf


class Repossitory {
    private val agens = mutableListOf<Agen>()

    init {
        if (agens.isEmpty()) {
            FakeDataResource.dummyMembers.forEach {
                agens.add(it)
            }
        }
    }

    fun getAllMember(): kotlinx.coroutines.flow.Flow<List<Agen>> {
        return flowOf(agens)
    }

    fun getMemberById(id: Int): kotlinx.coroutines.flow.Flow<Agen> {
        return  flowOf(agens.first {it.id == id})
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