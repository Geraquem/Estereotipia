package com.mmfsin.whoami.presentation.dashboard.viepager.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mmfsin.whoami.presentation.dashboard.cards.CardsFragment
import com.mmfsin.whoami.presentation.dashboard.questions.QuestionsFragment
import com.mmfsin.whoami.presentation.dashboard.viepager.interfaces.IViewPagerListener
import com.mmfsin.whoami.presentation.models.DeckType

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    val deckId: String,
    val deckType: DeckType,
    private val selectedCardId: String,
    val listener: IViewPagerListener
) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CardsFragment(deckId, deckType, selectedCardId)
            else -> QuestionsFragment(selectedCardId, listener)
        }
    }
}