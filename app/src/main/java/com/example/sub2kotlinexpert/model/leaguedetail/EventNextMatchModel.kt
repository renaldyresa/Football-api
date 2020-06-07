package com.example.sub2kotlinexpert.model.leaguedetail

import com.example.sub2kotlinexpert.data.OperationCallback
import com.example.sub2kotlinexpert.data.api.ApiClient
import com.example.sub2kotlinexpert.data.api.EventResponse
import com.example.sub2kotlinexpert.entity.Match
import com.example.sub2kotlinexpert.helper.Helper
import com.example.sub2kotlinexpert.model.DataSource
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EventNextMatchModel: DataSource<ArrayList<Match>> {

    private var call: Call<EventResponse>?= null


    override fun retrieveData(id: String, callback: OperationCallback<ArrayList<Match>>) {
        call = ApiClient.INSTANCE.getNextMatch(id)
        call?.enqueue(object : Callback<EventResponse>{
            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                callback.onError(t.message as String)
            }

            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                response.body().let {
                    if (response.isSuccessful && (it != null)){
                        val listMatch = ArrayList<Match>()
                        val list = JSONArray(it.events)
                        for(i in 0 until list.length()){
                            val match = list.getJSONObject(i)
                            if (match.getString("strSport") != "Soccer"){
                                continue
                            }
                            listMatch.add(Helper.convertEventToMatch(match))
                            if (i > 5)
                                break
                        }
                        callback.onSuccess(listMatch)
                    }
                }

            }
        })
    }

    override fun cancel() {
        call?.cancel()
    }

}