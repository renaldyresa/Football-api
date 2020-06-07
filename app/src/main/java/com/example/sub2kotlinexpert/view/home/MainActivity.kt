package com.example.sub2kotlinexpert.view.home

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentManager
import com.example.sub2kotlinexpert.R
import com.example.sub2kotlinexpert.adapter.SectionPagerSearchAdapter
import com.example.sub2kotlinexpert.view.favorite.FavoriteActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mFragmentManager: FragmentManager
    private lateinit var mSearchFragment: SearchFragment
    private lateinit var mHomeFragment: HomeFragment
    private lateinit var sectionPagerSearchAdapter: SectionPagerSearchAdapter
    private lateinit var searchMatchFragment: SearchMatchFragment
    private lateinit var searchTeamFragment: SearchTeamFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchMatchFragment = SearchMatchFragment()
        searchTeamFragment = SearchTeamFragment()
        mFragmentManager = supportFragmentManager
        sectionPagerSearchAdapter =
            SectionPagerSearchAdapter(
                this,
                mFragmentManager,
                searchMatchFragment,
                searchTeamFragment
            )
        mSearchFragment = SearchFragment(sectionPagerSearchAdapter)
        mHomeFragment = HomeFragment()
        val fragment = mFragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName)
        if (fragment !is HomeFragment) {
            mFragmentManager
                .beginTransaction()
                .add(R.id.frame_container, mHomeFragment, HomeFragment::class.java.simpleName)
                .commit()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchMenuItem = menu.findItem(R.id.search) as MenuItem
        searchMenuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container, mSearchFragment, SearchFragment::class.java.simpleName)
                    commit()
                }
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                mFragmentManager.beginTransaction().apply {
                    add(R.id.frame_container, mHomeFragment, HomeFragment::class.java.simpleName)
                    commit()
                }
                return true
            }
        })


        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).also {
            it.setSearchableInfo(searchManager.getSearchableInfo(componentName))
            it.queryHint = resources.getString(R.string.search_hint)
            it.isIconified = true
            it.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    searchMatchFragment.getDataList(query)
                    searchTeamFragment.getDataList(query)
                    hideKeyboardFrom(this@MainActivity, it)
                    return true
                }
                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }
            })
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite_menu -> startActivity(Intent(this, FavoriteActivity::class.java))
        }
        return true
    }

    private fun hideKeyboardFrom(context: Context, view: View) {
        val imm: InputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}
