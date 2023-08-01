package com.mmfsin.whoami.data.repository

import com.mmfsin.whoami.data.mappers.toCard
import com.mmfsin.whoami.data.mappers.toCardList
import com.mmfsin.whoami.data.models.CardDTO
import com.mmfsin.whoami.domain.interfaces.IDashboardRepository
import com.mmfsin.whoami.domain.interfaces.IRealmDatabase
import com.mmfsin.whoami.domain.models.Card
import io.realm.kotlin.where
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class DashboardRepository @Inject constructor(
    private val realmDatabase: IRealmDatabase
) : IDashboardRepository {

    companion object {
        private var flowValue = MutableStateFlow(Pair(false, ""))
    }

    //    private val reference = Firebase.database.reference.child(CARDS)


    override suspend fun getCards(deckId: String): List<Card> {
        val cards = realmDatabase.getObjectsFromRealm {
            where<CardDTO>().equalTo("deckId", deckId).findAll()
        }
        return if (cards.isEmpty()) getCardsFromFirebase(deckId)
        else cards.toCardList()
    }

    private fun getCardsFromFirebase(deckId: String): List<Card> {
        val list = listOf(
            CardDTO(
                "1",
                "deckId",
                "https://static.wikia.nocookie.net/disney/images/d/d3/Spinelli.jpg/revision/latest?cb=20110716123543&path-prefix=es",
                "Fulanito"
            ),
            CardDTO(
                "2",
                "deckId",
                "https://cdn.resfu.com/media/img_news/captura-de-claudio-spinelli-en-una-entrevista-con-espn--captura-espn.jpg?size=1000x&lossy=1",
                "Menganito"
            ),
            CardDTO(
                "3",
                "deckId",
                "https://m.media-amazon.com/images/I/610YY6E28CL._AC_UF894,1000_QL80_.jpg",
                "Carlitos"
            ),
            CardDTO(
                "4",
                "deckId",
                "https://i.pinimg.com/1200x/9e/f3/f5/9ef3f58495b819a28b12415b7fa26022.jpg",
                "Guatemala"
            ),
            CardDTO(
                "5",
                "deckId",
                "https://mymodernmet.com/wp/wp-content/uploads/2019/09/100k-ai-faces-5.jpg",
                "Rosita"
            ),
            CardDTO(
                "6",
                "deckId",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSXXUctuAZo49Abv32wO8qAS7wXhmjlWigvfg&usqp=CAU",
                "Chupipandi"
            ),
        )
        list.forEach { card -> saveCardInRealm(card) }
        return list.toCardList()
    }

    private fun saveCardInRealm(card: CardDTO) = realmDatabase.addObject { card }

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

    override fun discardCard(id: String) {
        val card = getCardDTO(id)
        card?.let {
            flowValue.value = Pair(!flowValue.value.first, it.id)
        }
    }

    override fun observeFlow(): StateFlow<Pair<Boolean, String>> {
        return flowValue.asStateFlow()
    }
}