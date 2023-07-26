package com.mmfsin.whoami.data.repository

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mmfsin.whoami.domain.interfaces.IRealmDatabase
import com.mmfsin.whoami.domain.interfaces.IDashboardRepository
import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.utils.CARDS
import javax.inject.Inject

class DashboardRepository @Inject constructor(
    private val realmDatabase: IRealmDatabase
) : IDashboardRepository {

//    private val reference = Firebase.database.reference.child(CARDS)

    override fun getCards(): List<Card> {
        return listOf(
            Card("https://static.wikia.nocookie.net/disney/images/d/d3/Spinelli.jpg/revision/latest?cb=20110716123543&path-prefix=es", "Fulanito"),
            Card("https://cdn.resfu.com/media/img_news/captura-de-claudio-spinelli-en-una-entrevista-con-espn--captura-espn.jpg?size=1000x&lossy=1", "Menganito"),
            Card("https://m.media-amazon.com/images/I/610YY6E28CL._AC_UF894,1000_QL80_.jpg", "Carlitos"),
            Card("https://i.pinimg.com/1200x/9e/f3/f5/9ef3f58495b819a28b12415b7fa26022.jpg", "Guatemala"),
            Card("https://mymodernmet.com/wp/wp-content/uploads/2019/09/100k-ai-faces-5.jpg", "Rosita"),
            Card("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSXXUctuAZo49Abv32wO8qAS7wXhmjlWigvfg&usqp=CAU", "Chupipandi"),
        )
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

//    private fun saveCategory(category: Category) = realmDatabase.addObject { category }
}