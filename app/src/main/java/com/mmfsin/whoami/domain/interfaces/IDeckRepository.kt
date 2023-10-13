package com.mmfsin.whoami.domain.interfaces

import com.mmfsin.whoami.domain.models.Deck
import com.mmfsin.whoami.domain.models.MyDeck

interface IDeckRepository {
    fun getDecks(): List<Deck>?
    fun getDeckById(id: String): Deck?

    fun createDeck(deck: MyDeck)
    fun getMyDecks(): List<MyDeck>
    fun getMyDeckById(id: String): MyDeck?
    fun deleteMyDeck(id: String)
}