package com.example.sub2kotlinexpert.model.leaguedetail

import com.example.sub2kotlinexpert.data.OperationCallback
import com.example.sub2kotlinexpert.data.api.ApiClient
import com.example.sub2kotlinexpert.data.api.TeamResponse
import com.example.sub2kotlinexpert.entity.Team
import com.example.sub2kotlinexpert.helper.Helper
import com.example.sub2kotlinexpert.model.DataSource
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamModel: DataSource<ArrayList<Team>> {

    private var call : Call<TeamResponse>? = null

    override fun retrieveData(id: String, callback: OperationCallback<ArrayList<Team>>) {
        call = ApiClient.INSTANCE.getLeagueTeam(id)
        call?.enqueue(object : Callback<TeamResponse>{
            override fun onFailure(call: Call<TeamResponse>, t: Throwable) {
                callback.onError(t.message.toString())
            }

            override fun onResponse(call: Call<TeamResponse>, response: Response<TeamResponse>) {
                response.body().let {
                    if (response.isSuccessful && it != null){
                        val listTeam = ArrayList<Team>()
                        val list = JSONArray(it.teams)
                        for (i in 0 until list.length()){
                            val team = list.getJSONObject(i)
                            listTeam.add(Helper.convertListTeam(team))
                        }
                        callback.onSuccess(listTeam)
                    }
                }
            }
        })
    }

    override fun cancel() {
        call?.cancel()
    }
}