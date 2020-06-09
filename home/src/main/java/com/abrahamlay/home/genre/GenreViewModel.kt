package com.abrahamlay.home.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.abrahamlay.base.constant.Constants
import com.abrahamlay.base.subscriber.BaseViewModel
import com.abrahamlay.base.subscriber.DefaultSubscriber
import com.abrahamlay.base.subscriber.ResultState
import com.abrahamlay.domain.entities.GenreModel
import com.abrahamlay.domain.interactors.GetGenresInteractor

/**
 * Created by Abraham Lay on 09/06/20.
 */
class GenreViewModel(repositoryImpl: GetGenresInteractor) : BaseViewModel() {
    private val mutableRepo = MutableLiveData<ResultState<List<GenreModel>>>()
    private val triggerFetch = MutableLiveData<Boolean>()
    val genreData: LiveData<ResultState<List<GenreModel>>> =
        Transformations.switchMap(triggerFetch) {
            fetchGenre(repositoryImpl)
            return@switchMap mutableRepo
        }

    init {
        refreshGenre()
    }

    private fun fetchGenre(repositoryImpl: GetGenresInteractor) {
        repositoryImpl.execute(object : DefaultSubscriber<List<GenreModel>>() {

            override fun onError(error: ResultState<List<GenreModel>>) {
                mutableRepo.postValue(error)
            }

            override fun onSuccess(data: ResultState<List<GenreModel>>) {
                mutableRepo.postValue(data)
            }
        }, GetGenresInteractor.Params(Constants.API_KEY))
    }

    fun refreshGenre() {
        triggerFetch.value = true
    }
}