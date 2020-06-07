package com.example.sub2kotlinexpert.view.teamdetail

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sub2kotlinexpert.R
import com.example.sub2kotlinexpert.data.db.FavoriteTeamHelper
import com.example.sub2kotlinexpert.entity.TeamDetail
import com.example.sub2kotlinexpert.view.favorite.FavoriteActivity
import com.example.sub2kotlinexpert.viewmodel.teamdetail.TeamDetailViewModel
import kotlinx.android.synthetic.main.activity_team_detail.*
import com.example.sub2kotlinexpert.data.db.DatabaseContract.TeamColumns.Companion as dcTeam

class TeamDetailActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val EXTRA_ID_LEAGUE = "extra_id_league"
    }

    private lateinit var idTeam: String
    private lateinit var viewModel: TeamDetailViewModel
    private lateinit var teamDetail: TeamDetail
    private lateinit var dbHelper : FavoriteTeamHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        (supportActionBar as ActionBar).apply {
            setDisplayHomeAsUpEnabled(true)
            title = ""
        }

        idTeam = intent.getStringExtra(EXTRA_ID_LEAGUE) as String
        viewModel = ViewModelProvider(this, TeamDetailViewModel.viewModelProvider).get(TeamDetailViewModel::class.java)

        dbHelper = FavoriteTeamHelper.getInstance(applicationContext)
        dbHelper.open()

        setupModel()
        btn_add_team_favorite.setOnClickListener(this)
        onRestart()
    }

    override fun onRestart() {
        super.onRestart()
        if (!dbHelper.isOpen()){
            dbHelper = FavoriteTeamHelper.getInstance(applicationContext)
            dbHelper.open()
        }
        val cursor = dbHelper.queryById(idTeam)
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

    private fun setupModel(){
        viewModel.setTeamDetail(idTeam)
        viewModel.getTeamDetail.observe(this, Observer {
            teamDetail = it
            setupUI()
        })

        viewModel.isViewLoading.observe(this, isViewLoadingObserver)
        viewModel.onMessageError.observe(this, isOnMessageErrorObserver)
        viewModel.isEmptyList.observe(this, isEmptyListObserver)
    }

    private fun setupUI(){
        Glide.with(this)
            .load(teamDetail.logo)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_photo_camera_black_24dp)
                    .error(R.drawable.ic_photo_camera_black_24dp)
                    .override(300,300))
            .into(iv_team_logo)
        tv_team_name.text = teamDetail.name
        tv_formed_year.text = teamDetail.formedYear
        tv_league.text = teamDetail.league
        tv_alternative_name.text = teamDetail.alternativeName
        tv_stadium.text = teamDetail.stadium
        tv_location.text = teamDetail.location
        tv_country.text = teamDetail.country
        tv_desc.text = teamDetail.desc

    }


    private val isViewLoadingObserver = Observer<Boolean> {
        val visibility = if (it) View.VISIBLE else View.GONE
        pb_team_detail.visibility = visibility
    }

    private val isOnMessageErrorObserver = Observer<String> {

    }

    private val isEmptyListObserver = Observer<Boolean> {
        Toast.makeText(this, "No Data", Toast.LENGTH_LONG).show()
    }




    override fun onClick(v: View) {
        if (v.id == R.id.btn_add_team_favorite){
            if(btn_add_team_favorite.text == getString(R.string.txt_add_team_favorite)){
                val values = ContentValues().apply {
                    put(dcTeam.ID_TEAM, teamDetail.idTeam)
                    put(dcTeam.LOGO, teamDetail.logo)
                    put(dcTeam.NAME, teamDetail.name)
                    put(dcTeam.FORMED_YEAR, teamDetail.formedYear)
                    put(dcTeam.STADIUM, teamDetail.stadium)
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
                val result = dbHelper.deleteById(teamDetail.idTeam)
                if (result > 0) {
                    buttonFavoriteType(false)
                }else
                    buttonFavoriteType(true)
            }
        }
    }

    private fun buttonFavoriteType(type: Boolean){
        if (type){
            btn_add_team_favorite.setBackgroundColor(resources.getColor(R.color.delete_favorite,theme))
            btn_add_team_favorite.text = resources.getText(R.string.txt_delete_from_favorite)
            btn_add_team_favorite.icon = resources.getDrawable(R.drawable.ic_remove,theme)
        }else{
            btn_add_team_favorite.setBackgroundColor(resources.getColor(R.color.add_favorite,theme))
            btn_add_team_favorite.text = resources.getText(R.string.txt_add_team_favorite)
            btn_add_team_favorite.icon = resources.getDrawable(R.drawable.ic_add,theme)
        }
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
