package com.example.sub2kotlinexpert.view.favorite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.sub2kotlinexpert.R
import com.example.sub2kotlinexpert.adapter.FavoriteMatchAdapter
import com.example.sub2kotlinexpert.adapter.OnItemClickCallback
import com.example.sub2kotlinexpert.data.db.FavoriteMatchHelper
import com.example.sub2kotlinexpert.entity.FavoriteMatch
import com.example.sub2kotlinexpert.helper.Helper
import com.example.sub2kotlinexpert.view.matchdetail.MatchDetailActivity
import kotlinx.android.synthetic.main.fragment_match_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class MatchFavoriteFragment(context: Context) : Fragment() {

    private val mContext = context
    private lateinit var adapter : FavoriteMatchAdapter
    private lateinit var dbHelper : FavoriteMatchHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dbHelper = FavoriteMatchHelper.getInstance(mContext)
        dbHelper.open()
        return inflater.inflate(R.layout.fragment_match_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showRecyclerList()
        loadFavoriteMatchAsync()
    }

    override fun onResume() {
        super.onResume()
        loadFavoriteMatchAsync()
    }

    private fun loadFavoriteMatchAsync(){
        GlobalScope.launch(Dispatchers.Main){
            pb_fm_favorite_match.visibility = View.VISIBLE
            val deferredMatch = async(Dispatchers.IO){
                val cursor = dbHelper.queryAll()
                Helper.mapCursorToArrayListMatch(cursor)
            }

            val listMatch = deferredMatch.await()
            pb_fm_favorite_match.visibility = View.GONE
            if (listMatch.size > 0){
                adapter.setListData(listMatch)
            }else{
                adapter.setListData(ArrayList())
                tv_info.text = resources.getString(R.string.txt_no_data)
            }
        }
    }

    private fun showRecyclerList(){
        rv_favorite_match.layoutManager = LinearLayoutManager(activity)
        rv_favorite_match.setHasFixedSize(true)
        adapter = FavoriteMatchAdapter()
        rv_favorite_match.adapter = adapter
        adapter.setOnButtonDeleteClickCallback(object : FavoriteMatchAdapter.OnButtonDeleteClickCallback {
            override fun onButtonDeleteClicked(id: String, position: Int) {
                deleteSelectedItem(id, position)
            }
        })
        adapter.setOnItemClickCallback(object : OnItemClickCallback<FavoriteMatch> {
            override fun onItemClicked(data: FavoriteMatch) {
                val intent = Intent(activity, MatchDetailActivity::class.java)
                intent.putExtra(MatchDetailActivity.EXTRA_ACTIVITY, FavoriteActivity::class.java.simpleName)
                intent.putExtra(MatchDetailActivity.EXTRA_FAVORITE_MATCH, data)
                startActivity(intent)
            }
        })
    }

    private fun deleteSelectedItem(id_match: String, position: Int) {
        val result = dbHelper.deleteById(id_match)
        if (result > 0){
            adapter.removeItem(position)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (dbHelper.isOpen())
            dbHelper.close()

    }



}
