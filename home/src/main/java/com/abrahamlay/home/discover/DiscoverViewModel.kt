package com.abrahamlay.home.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.abrahamlay.base.constant.Constants
import com.abrahamlay.base.state.NetworkState
import com.abrahamlay.base.subscriber.BaseViewModel
import com.abrahamlay.base.subscriber.DefaultSubscriber
import com.abrahamlay.base.subscriber.ResultState
import com.abrahamlay.domain.entities.MovieModel
import com.abrahamlay.domain.interactors.GetDiscoverMoviesByGenre
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by Abraham Lay on 09/06/20.
 */
class DiscoverViewModel(private val repositoryImpl: GetDiscoverMoviesByGenre) : BaseViewModel(),
    DiscoverDataSourceDelegate<MovieModel> {
    private var genreId: Int = 28

    private var executor: Executor = Executors.newFixedThreadPool(5)

    var productLiveData: LiveData<PagedList<MovieModel>> = MutableLiveData()

    var networkState: LiveData<NetworkState> = MutableLiveData()


    private var map: HashMap<String, Any> by HashMap<String, Any>()

    init {
        map = HashMap()
    }

    private fun fetchMovie() {
        val factory = DiscoverDataSourceFactory(genreId, INITIAL_PAGE, this)
        networkState = Transformations.switchMap(
            factory.mutableLiveData
        ) { dataSource -> dataSource.networkState }

        val pagedListConfig: PagedList.Config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(LIMIT_ITEM)
            .setEnablePlaceholders(false)
            .setPageSize(LIMIT_ITEM)
            .build()

        productLiveData =
            LivePagedListBuilder(factory, pagedListConfig)
                .setFetchExecutor(executor)
                .build()
    }

    fun refreshMovie(genreId: Int) {
        map[GetDiscoverMoviesByGenre.Params.PAGE_KEY] = INITIAL_PAGE
        map[GetDiscoverMoviesByGenre.Params.GENRE_KEY] = genreId
        this.genreId = genreId
        fetchMovie()
    }

    companion object {
        private const val INITIAL_PAGE = 1
        private const val LIMIT_ITEM = 20
    }

    override fun getDiscoverMovieByPageData(
        genreId: Int,
        pagePosition: Int,
        onResult: (List<MovieModel>) -> Unit,
        onErrorRequest: (Throwable?) -> Unit
    ) {
        map[GetDiscoverMoviesByGenre.Params.PAGE_KEY] = pagePosition
        map[GetDiscoverMoviesByGenre.Params.GENRE_KEY] = genreId
        repositoryImpl.execute(object : DefaultSubscriber<List<MovieModel>>() {
            override fun onError(error: ResultState<List<MovieModel>>) {
                if (error is ResultState.Error) {
                    onErrorRequest(error.throwable)
                }
            }

            override fun onSuccess(data: ResultState<List<MovieModel>>) {
                if (data is ResultState.Success && data.data.isNotEmpty()) {
                    onResult(data.data)
                }
            }
        }, GetDiscoverMoviesByGenre.Params(Constants.API_KEY, map))
    }
}