package com.example.sub2kotlinexpert.view.matchdetail

import android.content.ContentValues
import android.content.Intent

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sub2kotlinexpert.R
import com.example.sub2kotlinexpert.data.db.DatabaseContract.MatchColumns.Companion.AWAY_LOGO
import com.example.sub2kotlinexpert.data.db.DatabaseContract.MatchColumns.Companion.AWAY_TEAM
import com.example.sub2kotlinexpert.data.db.DatabaseContract.MatchColumns.Companion.DATE
import com.example.sub2kotlinexpert.data.db.DatabaseContract.MatchColumns.Companion.HOME_LOGO
import com.example.sub2kotlinexpert.data.db.DatabaseContract.MatchColumns.Companion.HOME_TEAM
import com.example.sub2kotlinexpert.data.db.DatabaseContract.MatchColumns.Companion.ID_AWAY_TEAM
import com.example.sub2kotlinexpert.data.db.DatabaseContract.MatchColumns.Companion.ID_HOME_TEAM
import com.example.sub2kotlinexpert.data.db.DatabaseContract.MatchColumns.Companion.ID_MATCH
import com.example.sub2kotlinexpert.data.db.DatabaseContract.MatchColumns.Companion.LEAGUE
import com.example.sub2kotlinexpert.data.db.DatabaseContract.MatchColumns.Companion.TIME
import com.example.sub2kotlinexpert.data.db.FavoriteMatchHelper
import com.example.sub2kotlinexpert.entity.FavoriteMatch
import com.example.sub2kotlinexpert.entity.Match
import com.example.sub2kotlinexpert.helper.Helper
import com.example.sub2kotlinexpert.view.favorite.FavoriteActivity
import com.example.sub2kotlinexpert.viewmodel.matchdetail.DetailMatchViewModel
import kotlinx.android.synthetic.main.activity_match_detail.*


class MatchDetailActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val EXTRA_MATCH = "extra_match"
        const val EXTRA_ACTIVITY= "extra_activity"
        const val EXTRA_FAVORITE_MATCH  = "extra_favorite_match"
    }

    private lateinit var match: Match
    private lateinit var dbHelper : FavoriteMatchHelper
    private lateinit var matchModel: DetailMatchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)

        (supportActionBar as ActionBar).apply {
            setDisplayHomeAsUpEnabled(true)
            title = ""
        }

        matchModel = ViewModelProvider(this, DetailMatchViewModel.viewModelProvider).get(
            DetailMatchViewModel::class.java)

        dbHelper = FavoriteMatchHelper.getInstance(applicationContext)
        dbHelper.open()

        val activityName = intent.getStringExtra(EXTRA_ACTIVITY) as String
        if (activityName == FavoriteActivity::class.java.simpleName){
            val favoriteMatch = intent.getParcelableExtra(EXTRA_FAVORITE_MATCH) as FavoriteMatch
            matchModel.setDetailMatch(favoriteMatch.id, favoriteMatch.homeLogo, favoriteMatch.awayLogo, null)

        }else{
            val matchIntent = intent.getParcelableExtra(EXTRA_MATCH) as Match
            matchModel.setDetailMatch(match = matchIntent)
        }

        setupViewModel()
        btn_add_favorite.setOnClickListener(this)
    }

    private fun setupViewModel(){
        matchModel.getDetailMatch().observe(this, androidx.lifecycle.Observer {
            match = it
            if(it.homeLogo.isEmpty())
                matchModel.getLogo(it.idHomeTeam, 0)
            if (it.awayLogo.isEmpty())
                matchModel.getLogo(it.idAwayTeam, 1)
            loadView()
        })
        matchModel.isViewLoading.observe(this, isViewLoadingObserver)
        matchModel.onMessageError.observe(this, isOnMessageErrorObserver)
        matchModel.isEmptyList.observe(this, isEmptyListObserver)
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        val visibility = if (it) View.VISIBLE else View.GONE
        pb_detail_match.visibility = visibility
    }

    private val isOnMessageErrorObserver = Observer<String> {
        Toast.makeText(this, "Failed Load data", Toast.LENGTH_LONG).show()
    }

    private val isEmptyListObserver = Observer<Boolean> {
        Toast.makeText(this, "No Data", Toast.LENGTH_LONG).show()
    }

    override fun onRestart() {
        super.onRestart()
        if (!dbHelper.isOpen()){
            dbHelper = FavoriteMatchHelper.getInstance(applicationContext)
            dbHelper.open()
        }
        val cursor = dbHelper.queryById(match.id)
        buttonFavoriteType(cursor.count > 0)
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


    private fun loadView(){
        tv_d_league_name.text = match.league
        tv_d_match_round.text = resources.getString(R.string.txt_round, match.round.toInt())
        val date = match.date.split("-").reversed().joinToString("-")
        val dateTime = Helper.setTime("$date ${match.time}")
        tv_d_date_time.text = dateTime
        loadHomeTeam()
        loadAwayTeam()

        val cursor = dbHelper.queryById(match.id)
        if (cursor.count > 0){
            buttonFavoriteType(true)
        }else{
            buttonFavoriteType(false)
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_add_favorite){
            if(btn_add_favorite.text == getString(R.string.txt_add_favorite_match)){
                val values = ContentValues().apply {
                    put(ID_MATCH, match.id)
                    put(LEAGUE, match.league)
                    put(ID_HOME_TEAM, match.idHomeTeam)
                    put(HOME_TEAM, match.homeTeam)
                    put(HOME_LOGO, match.homeLogo)
                    put(ID_AWAY_TEAM, match.idAwayTeam)
                    put(AWAY_TEAM, match.awayTeam)
                    put(AWAY_LOGO, match.awayLogo)
                    put(DATE, match.date)
                    put(TIME, match.time)
                }
                if (!dbHelper.isOpen())
                    dbHelper.open()
                val  result =  dbHelper.insert(values)
                if (result > 0) {
                    buttonFavoriteType(true)
                }else
                    buttonFavoriteType(false)
            }else{

                if (!dbHelper.isOpen())
                    dbHelper.open()
                val result = dbHelper.deleteById(match.id)
                if (result > 0) {
                    buttonFavoriteType(false)
                }else
                    buttonFavoriteType(true)
            }
        }
    }

    private fun buttonFavoriteType(type: Boolean){
        if (type){
            btn_add_favorite.setBackgroundColor(resources.getColor(R.color.delete_favorite,theme))
            btn_add_favorite.text = resources.getText(R.string.txt_delete_from_favorite)
            btn_add_favorite.icon = resources.getDrawable(R.drawable.ic_remove,theme)
        }else{
            btn_add_favorite.setBackgroundColor(resources.getColor(R.color.add_favorite,theme))
            btn_add_favorite.text = resources.getText(R.string.txt_add_favorite_match)
            btn_add_favorite.icon = resources.getDrawable(R.drawable.ic_add,theme)
        }
    }

    private fun loadHomeTeam(){
        Glide.with(this)
            .load(match.homeLogo)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.ic_photo_camera_black_24dp)
                .error(R.drawable.ic_photo_camera_black_24dp)
                .override(100,100))
            .into(iv_home_logo)

        tv_home_team_name.text = match.homeTeam
        tv_home_score.text = match.homeScore

        Glide.with(this)
            .load(match.homeLogo)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_photo_camera_black_24dp)
                    .error(R.drawable.ic_photo_camera_black_24dp)
                    .override(100,100))
            .into(iv_d_home_logo)
        tv_d_home_name.text = match.homeTeam
        tv_d_home_score.text = match.homeScore
        tv_d_home_shot.text = match.homeShot
        tv_d_home_yellow_card.text = match.homeYellowCard
        tv_d_home_red_card.text = match.homeRedCard
    }

    private fun loadAwayTeam(){
        Glide.with(this)
            .load(match.awayLogo)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_photo_camera_black_24dp)
                    .error(R.drawable.ic_photo_camera_black_24dp)
                    .override(100,100))
            .into(iv_away_logo)

        tv_away_team_name.text = match.awayTeam
        tv_away_score.text = match.awayScore

        Glide.with(this)
            .load(match.awayLogo)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_photo_camera_black_24dp)
                    .error(R.drawable.ic_photo_camera_black_24dp)
                    .override(100,100))
            .into(iv_d_away_logo)

        tv_d_away_name.text = match.awayTeam
        tv_d_away_score.text = match.awayScore
        tv_d_away_shot.text = match.awayShot
        tv_d_away_yellow_card.text = match.awayYellowCard
        tv_d_away_red_card.text = match.awayRedCard

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close()
    }

}
