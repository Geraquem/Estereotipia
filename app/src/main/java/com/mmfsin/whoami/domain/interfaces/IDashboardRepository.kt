package com.mmfsin.whoami.domain.interfaces

import com.mmfsin.whoami.domain.models.Card
import kotlinx.coroutines.flow.StateFlow

interface IDashboardRepository {
    fun getCards(deckId: String): List<Card>?
    fun getCardById(id: String): Card?
    fun discardCard(id: String, updateFlow: Boolean = true)
    fun observeFlow(): StateFlow<Pair<Boolean, String>>
}