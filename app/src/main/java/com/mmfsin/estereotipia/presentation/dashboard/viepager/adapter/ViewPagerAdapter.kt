package com.mmfsin.estereotipia.presentation.dashboard.viepager.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mmfsin.estereotipia.domain.models.GameQuestion
import com.mmfsin.estereotipia.presentation.dashboard.cards.CardsFragment
import com.mmfsin.estereotipia.presentation.dashboard.questions.QuestionsFragment
import com.mmfsin.estereotipia.presentation.dashboard.viepager.interfaces.IViewPagerListener

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    val deckId: String,
    private val selectedCardId: String,
    private val questions: List<GameQuestion>,
    val listener: IViewPagerListener
) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CardsFragment(deckId, selectedCardId)
            else -> QuestionsFragment(selectedCardId, questions, listener)
        }
    }
}