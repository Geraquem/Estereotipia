package com.mmfsin.estereotipia.presentation.dashboard.whoiswho.viewpager.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mmfsin.estereotipia.domain.models.GameQuestion
import com.mmfsin.estereotipia.presentation.dashboard.whoiswho.cards.CardsFragment
import com.mmfsin.estereotipia.presentation.dashboard.whoiswho.questions.QuestionsFragment
import com.mmfsin.estereotipia.presentation.dashboard.whoiswho.viewpager.interfaces.IViewPagerListener

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    val deckId: String,
    private val selectedCardId: String,
    val listener: IViewPagerListener
) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CardsFragment(deckId, selectedCardId)
            else -> QuestionsFragment(selectedCardId, listener)
        }
    }
}