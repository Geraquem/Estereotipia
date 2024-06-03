package com.mmfsin.whoami.domain.interfaces

import com.mmfsin.whoami.domain.models.Deck

interface IDeckRepository {
    /** System decks */
    fun getSystemDecks(): List<Deck>?
    fun getSystemDeckById(id: String): Deck?

    /** Custom decks */
    fun getCustomDeckById(id: String): Deck?
    fun createDeck(name: String, cards: List<String>)
    fun getCustomDecks(): List<Deck>
    fun editCustomDeckNameById(id: String, name: String)
    fun deleteCustomDeck(id: String)
}