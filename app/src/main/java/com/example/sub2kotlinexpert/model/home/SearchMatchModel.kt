package com.example.sub2kotlinexpert.model.home

import com.example.sub2kotlinexpert.data.OperationCallback
import com.example.sub2kotlinexpert.data.api.ApiClient
import com.example.sub2kotlinexpert.data.api.MatchResponse
import com.example.sub2kotlinexpert.entity.Match
import com.example.sub2kotlinexpert.helper.Helper
import com.example.sub2kotlinexpert.model.DataSource
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchMatchModel: DataSource<ArrayList<Match>> {

    private var call : Call<MatchResponse>? = null

    override fun retrieveData(id: String, callback: OperationCallback<ArrayList<Match>>) {
        call = ApiClient.INSTANCE.getSearchMatch(id)
        call?.enqueue(object : Callback<MatchResponse>{
            override fun onFailure(call: Call<MatchResponse>, t: Throwable) {
                callback.onError(t.message.toString())
            }

            override fun onResponse(call: Call<MatchResponse>, response: Response<MatchResponse>) {
                response.body().let {
                    if (response.isSuccessful && it != null){
                        val listMatch = ArrayList<Match>()
                        val list = JSONArray(it.event)
                        for(i in 0 until list.length()) {
                            val match = list.getJSONObject(i)
                            if (match.getString("strSport") != "Soccer") {
                                continue
                            }
                            listMatch.add(Helper.convertEventToMatch(match))
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