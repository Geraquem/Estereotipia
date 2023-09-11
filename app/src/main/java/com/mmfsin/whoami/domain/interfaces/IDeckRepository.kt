package com.mmfsin.whoami.domain.interfaces

import com.mmfsin.whoami.domain.models.Deck

interface IDeckRepository {
    suspend fun getDecks(): List<Deck>
    suspend fun getDeckById(id: String): Deck?
}