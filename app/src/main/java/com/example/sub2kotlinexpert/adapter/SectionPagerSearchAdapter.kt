package com.example.sub2kotlinexpert.adapter

import android.content.Context
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.sub2kotlinexpert.R
import com.example.sub2kotlinexpert.view.home.SearchMatchFragment
import com.example.sub2kotlinexpert.view.home.SearchTeamFragment

class SectionPagerSearchAdapter(
    private val mContext: Context,
    fm: FragmentManager,
    private val fragMatch: SearchMatchFragment,
    private val fragTeam:  SearchTeamFragment
): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    private val tabTitles = intArrayOf(
        R.string.txt_match,
        R.string.txt_team
    )

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 ->{
                fragment = fragMatch
            }
            1 -> fragment = fragTeam
        }
        return fragment as Fragment
    }

    override fun getCount(): Int = tabTitles.size

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(tabTitles[position])
    }

}