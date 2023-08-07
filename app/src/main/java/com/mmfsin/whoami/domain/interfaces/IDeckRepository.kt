package com.mmfsin.whoami.domain.interfaces

import com.mmfsin.whoami.domain.models.Deck

interface IDeckRepository {
    fun getTwoPlayerMode(): Boolean
    fun saveTwoPlayerMode(activated: Boolean)
    suspend fun getDecks(): List<Deck>
    suspend fun getDeckById(id: String): Deck?
}