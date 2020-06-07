package com.example.sub2kotlinexpert.viewmodel.matchdetail


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sub2kotlinexpert.data.OperationCallback
import com.example.sub2kotlinexpert.entity.Match
import com.example.sub2kotlinexpert.model.DataSource
import com.example.sub2kotlinexpert.model.logo.TeamLogoModel
import com.example.sub2kotlinexpert.model.matchdetail.MatchDetailModel

class DetailMatchViewModel(
    private val repository: DataSource<Match>,
    private val repositoryLogo: DataSource<String>
): ViewModel() {

    companion object{
        val viewModelProvider = ViewModelFactory(MatchDetailModel(), TeamLogoModel())
    }

    @Suppress("UNCHECKED_CAST")
    class ViewModelFactory(
        private val repository: DataSource<Match>,
        private val repositoryLogo: DataSource<String>
    ): ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DetailMatchViewModel(repository, repositoryLogo) as T
        }
    }

    private val detailMatch = MutableLiveData<Match>()

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError= MutableLiveData<String>()
    val onMessageError: LiveData<String> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    private var detailData = Match()


    fun setDetailMatch(id: String?= "", logoHome: String = "", logoAway: String = "", match: Match?= Match()){
        _isViewLoading.postValue(true)
        if (!id.isNullOrEmpty()){
            repository.retrieveData(id, object : OperationCallback<Match> {
                override fun onSuccess(data: Match?) {
                    _isViewLoading.postValue(false)
                    if (data!=null){
                        detailData = data
                        detailData.homeLogo = logoHome
                        detailData.awayLogo = logoAway
                        detailMatch.postValue(detailData)
                    }
                }

                override fun onError(error: String) {
                    _isViewLoading.postValue(false)
                    _onMessageError.postValue("Failed")
                }
            })
        }else{
            if (match != null){
                detailData = match
                detailMatch.postValue(detailData)
            }
            _isViewLoading.postValue(false)
        }
    }

    fun getDetailMatch(): LiveData<Match> {
        return detailMatch
    }

    fun getLogo(id: String?, team: Int){
        if (!id.isNullOrEmpty()){
            repositoryLogo.retrieveData(id, object : OperationCallback<String>{
                override fun onSuccess(data: String?) {
                    when (team){
                        0 -> detailData.homeLogo = data ?: ""
                        1 -> detailData.awayLogo = data ?: ""
                    }
                    detailMatch.postValue(detailData)
                }
                override fun onError(error: String) {
                }
            })
        }

    }

}