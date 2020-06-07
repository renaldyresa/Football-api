package com.example.sub2kotlinexpert.model.teamdetail

import com.example.sub2kotlinexpert.data.OperationCallback
import com.example.sub2kotlinexpert.data.api.ApiClient
import com.example.sub2kotlinexpert.data.api.TeamResponse
import com.example.sub2kotlinexpert.entity.TeamDetail
import com.example.sub2kotlinexpert.helper.Helper
import com.example.sub2kotlinexpert.model.DataSource
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamDetailModel: DataSource<TeamDetail> {

    private var call: Call<TeamResponse>? = null

    override fun retrieveData(id: String, callback: OperationCallback<TeamDetail>) {
        call = ApiClient.INSTANCE.getTeamDetail(id)
        call?.enqueue(object : Callback<TeamResponse>{
            override fun onFailure(call: Call<TeamResponse>, t: Throwable) {
                callback.onError(t.message.toString())
            }

            override fun onResponse(call: Call<TeamResponse>, response: Response<TeamResponse>) {
                response.body().let {
                    if (response.isSuccessful && it != null){
                        val dt = JSONArray(it.teams)
                        val team = dt.getJSONObject(0)
                        val data = Helper.convertDetailTeam(team)
                        callback.onSuccess(data)
                    }
                }
            }
        })
    }

    override fun cancel() {
        call?.cancel()
    }

}