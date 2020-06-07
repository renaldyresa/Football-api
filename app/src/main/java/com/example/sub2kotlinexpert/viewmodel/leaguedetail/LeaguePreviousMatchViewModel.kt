package com.example.sub2kotlinexpert.viewmodel.leaguedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sub2kotlinexpert.data.OperationCallback
import com.example.sub2kotlinexpert.entity.Match
import com.example.sub2kotlinexpert.model.DataSource
import com.example.sub2kotlinexpert.model.leaguedetail.EventPreviousMatchModel
import com.example.sub2kotlinexpert.model.logo.TeamLogoModel

class LeaguePreviousMatchViewModel(
    private val repository: DataSource<ArrayList<Match>>,
    private val repositoryLogo : DataSource<String>
): ViewModel() {

    companion object {
        val viewModelProvider = ViewModelFactory(EventPreviousMatchModel(), TeamLogoModel())
    }


    @Suppress("UNCHECKED_CAST")
    class ViewModelFactory(
        private val repository: DataSource<ArrayList<Match>>,
        private val repositoryLogo: DataSource<String>
    ): ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return LeaguePreviousMatchViewModel(repository, repositoryLogo) as T
        }
    }


    private val previousMatch = MutableLiveData<ArrayList<Match>>()

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError=MutableLiveData<String>()
    val onMessageError:LiveData<String> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    private var listData = ArrayList<Match>()

    private var listMatch  = ArrayList<Match>()


    fun setEventPreviousMatch(id: String){
        if (listMatch.size != 0){
            _isViewLoading.postValue(false)
            return
        }

        _isViewLoading.postValue(true)
        repository.retrieveData(id, object : OperationCallback<ArrayList<Match>> {
            override fun onSuccess(data: ArrayList<Match>?) {
                _isViewLoading.postValue(false)
                if (data!=null){
                    if (data.isEmpty()){
                        _isEmptyList.postValue(true)
                    }else{
                        listData = data
                        previousMatch.postValue(listData)
                        for (i in 0 until listData.size){
                            if (listData[i].homeLogo.isEmpty())
                                getLogo(listData[i].idHomeTeam, i, 0)
                            if (listData[i].awayLogo.isEmpty())
                                getLogo(listData[i].idAwayTeam, i, 1)
                        }
                    }
                }
            }

            override fun onError(error: String) {
                _isViewLoading.postValue(false)
                _onMessageError.postValue("Failed")
            }
        })
    }

    fun getEventPreviousMatch(): LiveData<ArrayList<Match>> {
        return previousMatch
    }

    fun getLogo(id: String?, index: Int, team: Int){
        if (!id.isNullOrEmpty()){
            repositoryLogo.retrieveData(id, object : OperationCallback<String>{
                override fun onSuccess(data: String?) {
                    when (team){
                        0 -> listData[index].homeLogo = data ?: ""
                        1 -> listData[index].awayLogo = data ?: ""
                    }
                    previousMatch.postValue(listData)
                }
                override fun onError(error: String) {
                }
            })
        }

    }

}