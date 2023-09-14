package com.mmfsin.whoami.presentation.dashboard.viepager.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mmfsin.whoami.presentation.dashboard.cards.CardsFragment
import com.mmfsin.whoami.presentation.dashboard.questions.QuestionsFragment

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    val deckId: String,
    val selectedCardId: String
) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CardsFragment(deckId, selectedCardId)
            else -> QuestionsFragment(selectedCardId)
        }
    }

//    private fun getFragment(deckId: String): Fragment {
//        val fragment = CategoriesByLanguageFragment()
//        val bundle = Bundle().apply {
//            putString(LANGUAGE, language.name.lowercase())
//        }
//        fragment.arguments = bundle
//        return fragment
//    }
}