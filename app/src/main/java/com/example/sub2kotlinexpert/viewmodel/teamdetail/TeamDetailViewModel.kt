package com.example.sub2kotlinexpert.viewmodel.teamdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sub2kotlinexpert.data.OperationCallback
import com.example.sub2kotlinexpert.entity.TeamDetail
import com.example.sub2kotlinexpert.model.DataSource
import com.example.sub2kotlinexpert.model.teamdetail.TeamDetailModel

class TeamDetailViewModel(
    private val repository: DataSource<TeamDetail>
) : ViewModel(){

    companion object {
        val viewModelProvider = ViewModelFactory(TeamDetailModel())
    }


    @Suppress("UNCHECKED_CAST")
    class ViewModelFactory(
        private val repository: DataSource<TeamDetail>
    ): ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TeamDetailViewModel(repository) as T
        }
    }

    private val teamDetail = MutableLiveData<TeamDetail>()
    val getTeamDetail: LiveData<TeamDetail> = teamDetail

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError= MutableLiveData<String>()
    val onMessageError: LiveData<String> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    fun setTeamDetail(id: String){
        _isViewLoading.postValue(true)
        if (id.isNotEmpty()){
            repository.retrieveData(id, object : OperationCallback<TeamDetail>{
                override fun onSuccess(data: TeamDetail?) {
                    _isViewLoading.postValue(false)
                    if (data != null) {
                        teamDetail.postValue(data)
                    }
                }

                override fun onError(error: String) {
                    _isViewLoading.postValue(false)
                    _onMessageError.postValue("failed")
                }
            })
        }
    }


}