package com.mmfsin.estereotipia.presentation.dashboard.phrases.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mmfsin.estereotipia.presentation.dashboard.phrases.phrases.PhrasesFragment
import com.mmfsin.estereotipia.presentation.dashboard.whoiswho.cards.CardsFragment

class ViewPagerPhrasesAdapter(
    fragmentActivity: FragmentActivity,
    val deckId: String,
    private val selectedCardId: String,
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CardsFragment(deckId, selectedCardId)
            else -> PhrasesFragment(selectedCardId)
        }
    }
}