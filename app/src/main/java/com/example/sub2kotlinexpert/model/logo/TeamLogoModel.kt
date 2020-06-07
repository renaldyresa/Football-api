package com.example.sub2kotlinexpert.model.logo


import com.example.sub2kotlinexpert.data.OperationCallback
import com.example.sub2kotlinexpert.data.api.ApiClient
import com.example.sub2kotlinexpert.data.api.TeamResponse
import com.example.sub2kotlinexpert.model.DataSource
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamLogoModel: DataSource<String> {

    private var call : Call<TeamResponse>? = null

    override fun retrieveData(id: String, callback: OperationCallback<String>) {
        call = ApiClient.INSTANCE.getTeamDetail(id)
        call?.enqueue(object : Callback<TeamResponse>{
            override fun onFailure(call: Call<TeamResponse>, t: Throwable) {
                callback.onError(t.message.toString())
            }

            override fun onResponse(call: Call<TeamResponse>, response: Response<TeamResponse>) {
                response.body().let {
                    if (response.isSuccessful && (it != null )){
                        val arrayTeam = JSONArray(it.teams)
                        val team = arrayTeam.getJSONObject(0).getString("strTeamBadge")
                        callback.onSuccess(team)
                    }
                }
            }

        })

    }

    override fun cancel() {

    }
}