package com.example.sub2kotlinexpert.view.favorite

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.sub2kotlinexpert.R
import com.example.sub2kotlinexpert.adapter.SectionPagerFavoriteAdapter
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        (supportActionBar as ActionBar).apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.txt_favorite)
        }

        val sectionPagerFavoriteAdapter =
            SectionPagerFavoriteAdapter(
                this,
                supportFragmentManager
            )
        view_pager_favorite.adapter = sectionPagerFavoriteAdapter
        tab_favorite.setupWithViewPager(view_pager_favorite)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
