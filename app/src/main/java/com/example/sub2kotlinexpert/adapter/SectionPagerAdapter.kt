package com.example.sub2kotlinexpert.adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.sub2kotlinexpert.*
import com.example.sub2kotlinexpert.entity.League
import com.example.sub2kotlinexpert.view.leaguedetail.LeagueDetailActivity
import com.example.sub2kotlinexpert.view.leaguedetail.LeagueDetailFragment
import com.example.sub2kotlinexpert.view.leaguedetail.LeagueClassementFragment
import com.example.sub2kotlinexpert.view.leaguedetail.LeagueTeamFragment

class SectionPagerAdapter(private val mContext: Context, fm: FragmentManager, private val league: League): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        const val EXTRA_ID_LEAGUE = "extra_id_league"
    }

    @StringRes
    private val tabTitles = intArrayOf(
        R.string.txt_detail,
        R.string.txt_classement,
        R.string.txt_team
    )

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = LeagueDetailFragment()
                .apply {
                arguments = Bundle().apply {
                    putParcelable(LeagueDetailActivity.EXTRA_LEAGUE, league)
                }
            }
            1 -> fragment = LeagueClassementFragment()
                .apply {
                arguments = Bundle().apply {
                    putString(EXTRA_ID_LEAGUE, league.id.toString())
                }
            }
            2 -> fragment = LeagueTeamFragment()
                .apply {
                arguments = Bundle().apply {
                    putString(EXTRA_ID_LEAGUE, league.id.toString())
                }
            }
        }
        return fragment as Fragment
    }

    override fun getCount(): Int = tabTitles.size

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(tabTitles[position])
    }
}