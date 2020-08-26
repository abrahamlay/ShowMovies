package com.abrahamlay.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abrahamlay.base.constant.Constants
import com.abrahamlay.base.presentation.BaseViewModel
import com.abrahamlay.base.subscriber.ResultState
import com.abrahamlay.domain.entities.DetailMovieModel
import com.abrahamlay.domain.interactors.GetDetailMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Abraham Lay on 09/06/20.
 */
class DetailViewModel :
    BaseViewModel {

    private var repositoryImpl: GetDetailMovie

    @Inject
    constructor(repositoryImpl: GetDetailMovie) : super(null) {
        this.repositoryImpl = repositoryImpl
    }

    constructor(repositoryImpl: GetDetailMovie, testScope: CoroutineScope?) : super(testScope) {
        this.repositoryImpl = repositoryImpl
    }

    private var movieId: Int = 28
    private val mutableRepo = MutableLiveData<ResultState<DetailMovieModel>>()
    val detailData: LiveData<ResultState<DetailMovieModel>> = mutableRepo
    private suspend fun fetchDetail(movieId: Int) {
        repositoryImpl.addParam(GetDetailMovie.Params(Constants.API_KEY, movieId))
            .execute(coroutineScope)
            .toResult()
            .run(mutableRepo::postValue)
    }

    fun refreshDetail(movieId: Int?) {
        GlobalScope.launch {
            fetchDetail(movieId ?: this@DetailViewModel.movieId)
        }
    }
}