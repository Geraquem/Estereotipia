package com.mmfsin.whoami.presentation.menu.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mmfsin.whoami.presentation.instructions.InstructionsFragment
import com.mmfsin.whoami.presentation.menu.tabs.MenuDecksTabFragment

class MenuViewPagerAdapter(
    fragmentActivity: FragmentActivity
) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MenuDecksTabFragment()
            else -> InstructionsFragment()
        }
    }
}