package com.example.sub2kotlinexpert.viewmodel.leaguedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sub2kotlinexpert.data.OperationCallback
import com.example.sub2kotlinexpert.entity.Team
import com.example.sub2kotlinexpert.model.DataSource
import com.example.sub2kotlinexpert.model.leaguedetail.TeamModel

class LeagueTeamViewModel(
    private val repository: DataSource<ArrayList<Team>>
): ViewModel(){

    companion object {
        val viewModelProvider = ViewModelFactory(TeamModel())
    }

    @Suppress("UNCHECKED_CAST")
    class ViewModelFactory(
        private val repository: DataSource<ArrayList<Team>>
    ): ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return LeagueTeamViewModel(repository) as T
        }
    }

    private val listTeam = MutableLiveData<ArrayList<Team>>()

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError=MutableLiveData<String>()
    val onMessageError: LiveData<String> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    fun setTeam(id: String){
        if (!listTeam.value.isNullOrEmpty()){
            _isViewLoading.postValue(false)
            return
        }

        _isViewLoading.postValue(true)
        repository.retrieveData(id, object : OperationCallback<ArrayList<Team>>{
            override fun onSuccess(data: ArrayList<Team>?) {
                _isViewLoading.postValue(false)
                if (data!=null){
                    if (data.isEmpty())
                        _isEmptyList.postValue(true)
                    else
                        listTeam.postValue(data)

                }
            }

            override fun onError(error: String) {
                _isViewLoading.postValue(false)
                _onMessageError.postValue("Failed")
            }
        })
    }

    fun getTeam(): LiveData<ArrayList<Team>> {
        return listTeam
    }

}