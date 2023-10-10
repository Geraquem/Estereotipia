package com.mmfsin.whoami.domain.interfaces

import com.mmfsin.whoami.domain.models.Deck
import com.mmfsin.whoami.domain.models.MyDeck

interface IDeckRepository {
    fun getDecks(): List<Deck>?
    fun getDeckById(id: String): Deck?

    fun createDeck(deck: MyDeck)
}