package com.mmfsin.whoami.data.repository

import com.mmfsin.whoami.data.mappers.toCard
import com.mmfsin.whoami.data.mappers.toCardList
import com.mmfsin.whoami.data.models.CardDTO
import com.mmfsin.whoami.data.models.DeckDTO
import com.mmfsin.whoami.domain.interfaces.ICardsRepository
import com.mmfsin.whoami.domain.interfaces.IRealmDatabase
import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.utils.ID
import com.mmfsin.whoami.utils.getCards
import io.realm.kotlin.where
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class CardsRepository @Inject constructor(
    private val realmDatabase: IRealmDatabase
) : ICardsRepository {

    companion object {
        private var flowValue = MutableStateFlow(Pair(false, ""))
    }

    override fun getAllCards(): List<Card>? {
        val cards = realmDatabase.getObjectsFromRealm {
            where<CardDTO>().findAll()
        }
        return if (cards.isEmpty()) null else setNonDiscardedCards(cards).toCardList()
            .sortedBy { it.name }
    }

    override fun getCardsByDeckId(deckId: String): List<Card>? {
        /** check system decks */
        val systemDeck = realmDatabase.getObjectFromRealm(DeckDTO::class.java, ID, deckId)
        systemDeck?.let { d -> return getCardsByListId(d.cards.getCards()) }

        /** si no puede que sea creado por el user */
        val customDeck = realmDatabase.getObjectFromRealm(DeckDTO::class.java, ID, deckId)
        customDeck?.let { d -> return getCardsByListId(d.cards.getCards()) }

        return null
    }

    private fun getCardsByListId(ids: List<String>): List<Card> {
        val cards = mutableListOf<CardDTO>()
        ids.forEach { id ->
            val card = getCardDTO(id)
            card?.let { cards.add(it) }
        }
        return setNonDiscardedCards(cards).toCardList()
    }

    private fun setNonDiscardedCards(cards: List<CardDTO>): List<CardDTO> {
        cards.forEach { card ->
            card.discard = false
            saveCardInRealm(card)
        }
        return cards
    }

    private fun getCardDTO(id: String): CardDTO? {
        val cards = realmDatabase.getObjectsFromRealm {
            where<CardDTO>().equalTo("id", id).findAll()
        }
        return if (cards.isEmpty()) null else cards.first()
    }

    override fun getCardById(id: String): Card? {
        val card = getCardDTO(id)
        return card?.toCard()
    }

    override fun discardCard(id: String): Boolean? {
        val card = getCardDTO(id)
        card?.let {
            card.discard = !card.discard
            saveCardInRealm(card)
            flowValue.value = Pair(!flowValue.value.first, it.id)
            return card.discard
        } ?: run { return null }
    }

    override fun selectCard(id: String) {
        val card = getCardDTO(id)
        card?.let {
            flowValue.value = Pair(!flowValue.value.first, it.id)
        }
    }

    private fun saveCardInRealm(card: CardDTO) = realmDatabase.addObject { card }

    override fun observeFlow(): StateFlow<Pair<Boolean, String>> {
        return flowValue.asStateFlow()
    }
}