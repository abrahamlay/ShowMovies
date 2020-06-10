package com.abrahamlay.home.discover

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.abrahamlay.domain.entities.MovieModel

/**
 * Created by Abraham Lay on 10/06/20.
 */
class DiscoverDataSourceFactory(
    private val genreId: Int,
    private val page: Int,
    private val delegate: DiscoverDataSourceDelegate<MovieModel>
) : DataSource.Factory<Int, MovieModel>() {
    private lateinit var dataSource: DiscoverMoviePageKeyedListDataSource
    var mutableLiveData: MutableLiveData<DiscoverMoviePageKeyedListDataSource> = MutableLiveData()

    override fun create(): DataSource<Int, MovieModel> {
        dataSource = DiscoverMoviePageKeyedListDataSource(genreId, page, delegate)
        mutableLiveData.postValue(dataSource)
        return dataSource
    }
}