package com.abrahamlay.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.abrahamlay.base.constant.Constants
import com.abrahamlay.base.subscriber.BaseViewModel
import com.abrahamlay.base.subscriber.DefaultSubscriber
import com.abrahamlay.base.subscriber.ResultState
import com.abrahamlay.domain.entities.DetailMovieModel
import com.abrahamlay.domain.interactors.GetDetailMovie

/**
 * Created by Abraham Lay on 09/06/20.
 */
class DetailViewModel(repositoryImpl: GetDetailMovie) : BaseViewModel() {
    private var movieId: Int = 28
    private val mutableRepo = MutableLiveData<ResultState<DetailMovieModel>>()
    private val triggerFetch = MutableLiveData<Boolean>()
    val detailData: LiveData<ResultState<DetailMovieModel>> =
        Transformations.switchMap(triggerFetch) {
            fetchDetail(repositoryImpl)
            return@switchMap mutableRepo
        }

    private fun fetchDetail(repositoryImpl: GetDetailMovie) {
        repositoryImpl.execute(object : DefaultSubscriber<DetailMovieModel>() {

            override fun onError(error: ResultState<DetailMovieModel>) {
                mutableRepo.postValue(error)
            }

            override fun onSuccess(data: ResultState<DetailMovieModel>) {
                mutableRepo.postValue(data)
            }
        }, GetDetailMovie.Params(Constants.API_KEY, movieId))
    }

    fun refreshDetail(movieId: Int) {
        this.movieId = movieId
        triggerFetch.value = true
    }
}