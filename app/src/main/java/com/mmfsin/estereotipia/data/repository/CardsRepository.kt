package com.mmfsin.estereotipia.data.repository

import com.mmfsin.estereotipia.data.mappers.toCard
import com.mmfsin.estereotipia.data.mappers.toCardList
import com.mmfsin.estereotipia.data.models.CardDTO
import com.mmfsin.estereotipia.data.models.DeckDTO
import com.mmfsin.estereotipia.domain.interfaces.ICardsRepository
import com.mmfsin.estereotipia.domain.interfaces.IRealmDatabase
import com.mmfsin.estereotipia.domain.models.Card
import com.mmfsin.estereotipia.utils.ID
import com.mmfsin.estereotipia.utils.getCards
import io.realm.kotlin.where
import javax.inject.Inject

class CardsRepository @Inject constructor(
    private val realmDatabase: IRealmDatabase
) : ICardsRepository {

    override fun getAllCards(): List<Card>? {
        val cards = realmDatabase.getObjectsFromRealm {
            where<CardDTO>().findAll()
        }
        return if (cards.isEmpty()) null else cards.toCardList()
            .sortedBy { it.name }
    }

    override fun getThreeRandomCards(): List<Card>? {
        return getAllCards()?.shuffled()?.take(3)
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
        return cards.toCardList()
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
}