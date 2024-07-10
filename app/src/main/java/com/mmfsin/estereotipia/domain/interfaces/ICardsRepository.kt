package com.mmfsin.estereotipia.domain.interfaces

import com.mmfsin.estereotipia.domain.models.Card

interface ICardsRepository {
    fun getCardsByDeckId(deckId: String): List<Card>?

    fun getAllCards(): List<Card>?
    fun getCardById(id: String): Card?
}