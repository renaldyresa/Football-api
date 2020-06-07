package com.example.sub2kotlinexpert.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.sub2kotlinexpert.R
import com.example.sub2kotlinexpert.adapter.SectionPagerSearchAdapter
import com.google.android.material.tabs.TabLayout

class SearchFragment( pagerSearchAdapter: SectionPagerSearchAdapter
) : Fragment() {


    private var sectionPagerSearchAdapter = pagerSearchAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val viewPager = view.findViewById(R.id.view_pager_search) as ViewPager
        val tabLayout = view.findViewById(R.id.tab_search) as TabLayout
        viewPager.adapter = sectionPagerSearchAdapter
        tabLayout.setupWithViewPager(viewPager)
        return view
    }


}
