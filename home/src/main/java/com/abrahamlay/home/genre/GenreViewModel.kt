package com.abrahamlay.home.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abrahamlay.base.constant.Constants
import com.abrahamlay.base.subscriber.BaseViewModel
import com.abrahamlay.base.subscriber.ResultState
import com.abrahamlay.domain.entities.GenreModel
import com.abrahamlay.domain.interactors.GetGenresInteractor
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Abraham Lay on 09/06/20.
 */
class GenreViewModel @Inject constructor(val repositoryImpl: GetGenresInteractor) :
    BaseViewModel() {
    private val mutableRepo = MutableLiveData<ResultState<List<GenreModel>>>()
    val genreData: LiveData<ResultState<List<GenreModel>>> = mutableRepo

    init {
        refreshGenre()
    }

    private suspend fun fetchGenre() {
        repositoryImpl.addParam(GetGenresInteractor.Params(Constants.API_KEY))
            .execute(viewModelScope)
            .toResult()
            .run(mutableRepo::postValue)
    }

    fun refreshGenre() {
        viewModelScope.launch {
            fetchGenre()
        }
    }
}