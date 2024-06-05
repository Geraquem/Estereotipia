package com.mmfsin.whoami.domain.interfaces

import com.mmfsin.whoami.domain.models.Deck

interface IDeckRepository {
    /** System decks */
    fun getSystemDecks(): List<Deck>

    /** Custom decks */
    fun getCustomDecks(): List<Deck>
    fun createDeck(name: String, cards: List<String>)
    fun editCustomDeckNameById(id: String, name: String)
    fun deleteCustomDeck(id: String)

    /** Common */
    fun getDeckById(id: String): Deck?
}