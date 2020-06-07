package com.example.sub2kotlinexpert.view.leaguedetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import com.example.sub2kotlinexpert.R
import com.example.sub2kotlinexpert.adapter.SectionPagerAdapter
import com.example.sub2kotlinexpert.entity.League
import com.example.sub2kotlinexpert.view.favorite.FavoriteActivity
import kotlinx.android.synthetic.main.activity_detail_league.*

class LeagueDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_LEAGUE = "extra_league"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_league)

        (supportActionBar as ActionBar).apply {
            setDisplayHomeAsUpEnabled(true)
            title = ""
        }

        val league = intent.getParcelableExtra(EXTRA_LEAGUE) as League

        val sectionsPagerAdapter =
            SectionPagerAdapter(
                this,
                supportFragmentManager,
                league
            )
        view_pager_detail.adapter = sectionsPagerAdapter
        tab_detail_team.setupWithViewPager(view_pager_detail)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite_menu -> startActivity(Intent(this, FavoriteActivity::class.java))
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
