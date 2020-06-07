package com.example.sub2kotlinexpert.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sub2kotlinexpert.data.OperationCallback
import com.example.sub2kotlinexpert.entity.Team
import com.example.sub2kotlinexpert.model.DataSource
import com.example.sub2kotlinexpert.model.home.SearchTeamModel

class SearchTeamViewModel(
    private val repository: DataSource<ArrayList<Team>>
): ViewModel() {

    companion object {
        val viewModelProvider = ViewModelFactory(SearchTeamModel())
    }

    @Suppress("UNCHECKED_CAST")
    class ViewModelFactory(
        private val repository: DataSource<ArrayList<Team>>
    ): ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SearchTeamViewModel(repository) as T
        }
    }

    private val searchTeam = MutableLiveData<ArrayList<Team>>()

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError=MutableLiveData<String>()
    val onMessageError:LiveData<String> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList


    fun setSearchTeam(id: String){
        _isViewLoading.postValue(true)
        repository.retrieveData(id, object : OperationCallback<ArrayList<Team>> {
            override fun onSuccess(data: ArrayList<Team>?) {
                _isViewLoading.postValue(false)
                if (data!=null){
                    if (data.isEmpty()){
                        _isEmptyList.postValue(true)
                    }else{
                        _isEmptyList.postValue(false)
                        searchTeam.postValue(data)
                    }
                }else
                    _isEmptyList.postValue(true)
            }

            override fun onError(error: String) {
                _isViewLoading.postValue(false)
                _onMessageError.postValue("Failed")
            }
        })
    }

    fun getSearchTeam(): LiveData<ArrayList<Team>> {
        return searchTeam
    }

}