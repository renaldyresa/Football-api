package com.example.sub2kotlinexpert.model.leaguedetail

import com.example.sub2kotlinexpert.data.OperationCallback
import com.example.sub2kotlinexpert.data.api.ApiClient
import com.example.sub2kotlinexpert.data.api.ClassementResponse
import com.example.sub2kotlinexpert.entity.Classement
import com.example.sub2kotlinexpert.helper.Helper
import com.example.sub2kotlinexpert.model.DataSource
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClassementModel: DataSource<ArrayList<Classement>> {

    private var call: Call<ClassementResponse>?= null

    override fun retrieveData(id: String, callback: OperationCallback<ArrayList<Classement>>) {
        call = ApiClient.INSTANCE.getClassementLeague(id)
        call?.enqueue(object : Callback<ClassementResponse>{
            override fun onFailure(call: Call<ClassementResponse>, t: Throwable) {
                callback.onError(t.message.toString())
            }

            override fun onResponse(call: Call<ClassementResponse>, response: Response<ClassementResponse>) {
                response.body().let {
                    if (response.isSuccessful && it != null){
                        val listTeam = ArrayList<Classement>()
                        val list = JSONArray(it.table)
                        for (i in 0 until list.length()){
                            val team = list.getJSONObject(i)
                            listTeam.add(Helper.convertTeam(team))
                        }
                        callback.onSuccess(listTeam)
                    }
                }
            }
        })
    }

    override fun cancel() {

    }
}