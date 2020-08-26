package com.abrahamlay.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abrahamlay.base.constant.Constants
import com.abrahamlay.base.subscriber.BaseViewModel
import com.abrahamlay.base.subscriber.ResultState
import com.abrahamlay.domain.entities.DetailMovieModel
import com.abrahamlay.domain.interactors.GetDetailMovie
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Abraham Lay on 09/06/20.
 */
class DetailViewModel @Inject constructor(val repositoryImpl: GetDetailMovie) : BaseViewModel() {
    private var movieId: Int = 28
    private val mutableRepo = MutableLiveData<ResultState<DetailMovieModel>>()
    val detailData: LiveData<ResultState<DetailMovieModel>> = mutableRepo


    init {
        refreshDetail()
    }

    private suspend fun fetchDetail() {
        repositoryImpl.addParam(GetDetailMovie.Params(Constants.API_KEY, movieId))
            .execute(viewModelScope)
            .toResult()
            .run(mutableRepo::postValue)
    }

    fun refreshDetail() {
        viewModelScope.launch {
            fetchDetail()
        }
    }
}