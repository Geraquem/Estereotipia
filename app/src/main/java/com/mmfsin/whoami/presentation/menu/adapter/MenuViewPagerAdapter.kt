package com.mmfsin.whoami.presentation.menu.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mmfsin.whoami.presentation.menu.listener.IMenuListener
import com.mmfsin.whoami.presentation.menu.tabs.MenuCardsTabFragment
import com.mmfsin.whoami.presentation.menu.tabs.MenuDecksTabFragment

class MenuViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    val listener: IMenuListener
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MenuDecksTabFragment(listener)
            else -> MenuCardsTabFragment(listener)
        }
    }
}