package com.abrahamlay.home.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.abrahamlay.base.constant.Constants
import com.abrahamlay.base.presentation.BaseViewModel
import com.abrahamlay.base.state.NetworkState
import com.abrahamlay.domain.entities.MovieModel
import com.abrahamlay.domain.interactors.GetDiscoverMoviesByGenre
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

/**
 * Created by Abraham Lay on 09/06/20.
 */
class DiscoverViewModel :
    BaseViewModel,
    DiscoverDataSourceDelegate<MovieModel> {

    private var repositoryImpl: GetDiscoverMoviesByGenre

    @Inject
    constructor(repositoryImpl: GetDiscoverMoviesByGenre) : super(null) {
        this.repositoryImpl = repositoryImpl
    }

    constructor(repositoryImpl: GetDiscoverMoviesByGenre, testScope: CoroutineScope?) : super(
        testScope
    ) {
        this.repositoryImpl = repositoryImpl
    }

    private var genreId: Int = 28

    private var executor: Executor = Executors.newFixedThreadPool(5)

    var productLiveData: LiveData<PagedList<MovieModel>> = MutableLiveData()

    var errorLiveData: MutableLiveData<Throwable> = MutableLiveData()

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
        coroutineScope.launch {
            fetchDiscoverMovieByPageData(genreId, pagePosition, onResult, onErrorRequest)
        }
    }

    private suspend fun fetchDiscoverMovieByPageData(
        genreId: Int,
        pagePosition: Int,
        onResult: (List<MovieModel>) -> Unit,
        onErrorRequest: (Throwable?) -> Unit
    ) {
        map[GetDiscoverMoviesByGenre.Params.PAGE_KEY] = pagePosition
        map[GetDiscoverMoviesByGenre.Params.GENRE_KEY] = genreId
        repositoryImpl.addParam(GetDiscoverMoviesByGenre.Params(Constants.API_KEY, map))
            .execute(coroutineScope)
            .toPaginationResult(
                { success ->
                    if (success.isNotEmpty()) {
                        onResult(success)
                    } else {
                        onErrorRequest(Throwable("There is no more item"))
                    }
                }, { error ->
                    if (pagePosition == INITIAL_PAGE) {
                        errorLiveData.value = error
                    } else {
                        onErrorRequest(error)
                    }
                }
            )
    }
}