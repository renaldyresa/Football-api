package com.example.sub2kotlinexpert.model.matchdetail

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

class MatchDetailModel : DataSource<Match> {
    private var call : Call<EventResponse>? = null

    override fun retrieveData(id: String, callback: OperationCallback<Match>) {
        call = ApiClient.INSTANCE.getDetailMatch(id)
        call?.enqueue(object : Callback<EventResponse>{
            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                callback.onError(t.message.toString())
            }
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                response.body().let {
                    if (response.isSuccessful && it != null){
                        val list = JSONArray(it.events)
                        val match = list.getJSONObject(0)
                        val data = Helper.convertEventToMatch(match)

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