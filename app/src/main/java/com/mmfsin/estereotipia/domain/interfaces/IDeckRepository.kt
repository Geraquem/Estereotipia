package com.mmfsin.estereotipia.domain.interfaces

import com.mmfsin.estereotipia.domain.models.Deck

interface IDeckRepository {
    /** System decks */
    fun getSystemDecks(): List<Deck>

    /** Custom decks */
    fun getCustomDecks(): List<Deck>
    fun createDeck(name: String, cards: List<String>)
    fun editCustomDeckName(id: String, name: String)
    fun editCustomDeckCards(id: String, cards: List<String>)
    fun deleteCustomDeck(id: String)

    /** Common */
    fun getDeckById(id: String): Deck?
}