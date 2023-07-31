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
        private var flowValue = MutableStateFlow(false)
    }

    //    private val reference = Firebase.database.reference.child(CARDS)

    override fun getCards(): List<Card> {
        val list = listOf(
            CardDTO(
                "1",
                "https://static.wikia.nocookie.net/disney/images/d/d3/Spinelli.jpg/revision/latest?cb=20110716123543&path-prefix=es",
                "Fulanito",
                false,
            ),
            CardDTO(
                "2",
                "https://cdn.resfu.com/media/img_news/captura-de-claudio-spinelli-en-una-entrevista-con-espn--captura-espn.jpg?size=1000x&lossy=1",
                "Menganito",
                false,
            ),
            CardDTO(
                "3",
                "https://m.media-amazon.com/images/I/610YY6E28CL._AC_UF894,1000_QL80_.jpg",
                "Carlitos",
                false,
            ),
            CardDTO(
                "4",
                "https://i.pinimg.com/1200x/9e/f3/f5/9ef3f58495b819a28b12415b7fa26022.jpg",
                "Guatemala",
                false,
            ),
            CardDTO(
                "5",
                "https://mymodernmet.com/wp/wp-content/uploads/2019/09/100k-ai-faces-5.jpg",
                "Rosita",
                false,
            ),
            CardDTO(
                "6",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSXXUctuAZo49Abv32wO8qAS7wXhmjlWigvfg&usqp=CAU",
                "Chupipandi",
                false,
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
            it.discarded = !it.discarded
            realmDatabase.addObject { it }
            flowValue.value = !flowValue.value
        }
    }

    override fun observeFlow(): StateFlow<Boolean> {
        return flowValue.asStateFlow()
    }

//    override fun getCategoriesByLanguage(language: String): List<Category> {
//        return realmDatabase.getObjectsFromRealm {
//            where<Category>().equalTo(LANGUAGE, language).findAll()
//        }.sortedBy { it.order }
//    }

//    override suspend fun getCategoriesFromFirebase(): List<Category> {
//        val latch = CountDownLatch(1)
//        val categories = mutableListOf<Category>()
//        reference.get().addOnSuccessListener {
//            for (child in it.children) {
//                child.getValue(Category::class.java)?.let { category ->
//                    categories.add(category)
//                    saveCategory(category)
//                }
//            }
//            latch.countDown()
//        }.addOnFailureListener { latch.countDown() }
//
//        withContext(Dispatchers.IO) {
//            latch.await()
//        }
//        return categories
//    }

}