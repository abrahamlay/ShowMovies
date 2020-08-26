package com.abrahamlay.home.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abrahamlay.base.constant.Constants
import com.abrahamlay.base.presentation.BaseViewModel
import com.abrahamlay.base.subscriber.ResultState
import com.abrahamlay.domain.entities.GenreModel
import com.abrahamlay.domain.interactors.GetGenresInteractor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Abraham Lay on 09/06/20.
 */
class GenreViewModel :
    BaseViewModel {

    private var repositoryImpl: GetGenresInteractor

    @Inject
    constructor(repositoryImpl: GetGenresInteractor) : super(null) {
        this.repositoryImpl = repositoryImpl
    }

    constructor(
        repositoryImpl: GetGenresInteractor,
        testScope: CoroutineScope?
    ) : super(testScope) {
        this.repositoryImpl = repositoryImpl
    }

    private val mutableRepo = MutableLiveData<ResultState<List<GenreModel>>>()
    val genreData: LiveData<ResultState<List<GenreModel>>> = mutableRepo

    init {
        refreshGenre()
    }

    private suspend fun fetchGenre() {
        repositoryImpl.addParam(GetGenresInteractor.Params(Constants.API_KEY))
            .execute(coroutineScope)
            .toResult()
            .run(mutableRepo::postValue)
    }

    fun refreshGenre() {
        GlobalScope.launch {
            fetchGenre()
        }
    }
}