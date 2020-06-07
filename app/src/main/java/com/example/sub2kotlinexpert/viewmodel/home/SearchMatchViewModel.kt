package com.example.sub2kotlinexpert.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sub2kotlinexpert.data.OperationCallback
import com.example.sub2kotlinexpert.entity.Match
import com.example.sub2kotlinexpert.model.DataSource
import com.example.sub2kotlinexpert.model.home.SearchMatchModel
import com.example.sub2kotlinexpert.model.logo.TeamLogoModel


class SearchMatchViewModel(
    private val repository: DataSource<ArrayList<Match>>,
    private val repositoryLogo: DataSource<String>
) : ViewModel(){

    companion object {
        val viewModelProvider = ViewModelFactory(SearchMatchModel(), TeamLogoModel())
    }

    @Suppress("UNCHECKED_CAST")
    class ViewModelFactory(
        private val repository: DataSource<ArrayList<Match>>,
        private val repositoryLogo: DataSource<String>
    ): ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SearchMatchViewModel(repository, repositoryLogo) as T
        }
    }

    private val searchMatch = MutableLiveData<ArrayList<Match>>()

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError=MutableLiveData<String>()
    val onMessageError:LiveData<String> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    private var listData  = ArrayList<Match>()


    fun setSearchMatch(id: String){
        _isViewLoading.postValue(true)
        repository.retrieveData(id, object : OperationCallback<ArrayList<Match>> {
            override fun onSuccess(data: ArrayList<Match>?) {
                _isViewLoading.postValue(false)
                if (data!=null){
                    if (data.isEmpty()){
                        _isEmptyList.postValue(true)
                    }else{
                        listData = data
                        _isEmptyList.postValue(false)
                        searchMatch.postValue(listData)
                        for (i in 0 until listData.size){
                            if (listData[i].homeLogo.isEmpty())
                                getLogo(listData[i].idHomeTeam, i, 0)
                            if (listData[i].awayLogo.isEmpty())
                                getLogo(listData[i].idAwayTeam, i, 1)
                        }
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

    fun getSearchMatch(): LiveData<ArrayList<Match>> {
        return searchMatch
    }

    fun getLogo(id: String?, index: Int, team: Int){
        if (!id.isNullOrEmpty()){
            repositoryLogo.retrieveData(id, object : OperationCallback<String>{
                override fun onSuccess(data: String?) {
                    when (team){
                        0 -> listData[index].homeLogo = data ?: ""
                        1 -> listData[index].awayLogo = data ?: ""
                    }
                    searchMatch.postValue(listData)
                }

                override fun onError(error: String) {

                }
            })
        }

    }

}