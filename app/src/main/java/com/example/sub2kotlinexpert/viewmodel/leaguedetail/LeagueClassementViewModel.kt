package com.example.sub2kotlinexpert.viewmodel.leaguedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sub2kotlinexpert.data.OperationCallback
import com.example.sub2kotlinexpert.entity.Classement
import com.example.sub2kotlinexpert.model.DataSource
import com.example.sub2kotlinexpert.model.leaguedetail.ClassementModel
import com.example.sub2kotlinexpert.model.logo.TeamLogoModel


class LeagueClassementViewModel(
    private val repository: DataSource<ArrayList<Classement>>,
    private val repositoryLogo: DataSource<String>
): ViewModel() {

    companion object {
        val viewModelProvider = ViewModelFactory(ClassementModel(), TeamLogoModel())
    }

    @Suppress("UNCHECKED_CAST")
    class ViewModelFactory(
        private val repository: DataSource<ArrayList<Classement>>,
        private val repositoryLogo: DataSource<String>
    ): ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return LeagueClassementViewModel(repository, repositoryLogo) as T
        }
    }

    private val listClassement = MutableLiveData<ArrayList<Classement>>()

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError=MutableLiveData<String>()
    val onMessageError:LiveData<String> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    private var listData = ArrayList<Classement>()



    fun setClassement(id: String){
        if (!listClassement.value.isNullOrEmpty()){
            _isViewLoading.postValue(false)
            return
        }

        _isViewLoading.postValue(true)
        repository.retrieveData(id, object : OperationCallback<ArrayList<Classement>> {
            override fun onSuccess(data: ArrayList<Classement>?) {
                _isViewLoading.postValue(false)
                if (data!=null){
                    if (data.isEmpty()){
                        _isEmptyList.postValue(true)
                    }else{
                        listData = data
                        listClassement.postValue(listData)
                        for (i in 0 until listData.size){
                            if (listData[i].logo.isEmpty())
                                getLogo(listData[i].idTeam, i)
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



    fun getClassement(): LiveData<ArrayList<Classement>> {
        return listClassement
    }

    fun getLogo(id: String?, index: Int){
        if (!id.isNullOrEmpty()){
            repositoryLogo.retrieveData(id, object : OperationCallback<String>{
                override fun onSuccess(data: String?) {
                    listData[index].logo = data ?: ""
                    listClassement.postValue(listData)
                }
                override fun onError(error: String) {
                }
            })
        }

    }

}