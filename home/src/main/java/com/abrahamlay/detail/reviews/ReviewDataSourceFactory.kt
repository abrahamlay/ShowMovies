package com.abrahamlay.detail.reviews

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.abrahamlay.domain.entities.ReviewModel

/**
 * Created by Abraham Lay on 10/06/20.
 */
class ReviewDataSourceFactory(
    private val movieId: Int,
    private val page: Int,
    private val delegate: ReviewDataSourceDelegate<ReviewModel>
) : DataSource.Factory<Int, ReviewModel>() {
    private lateinit var dataSource: ReviewPageKeyedListDataSource
    var mutableLiveData: MutableLiveData<ReviewPageKeyedListDataSource> = MutableLiveData()

    override fun create(): DataSource<Int, ReviewModel> {
        dataSource = ReviewPageKeyedListDataSource(movieId, page, delegate)
        mutableLiveData.postValue(dataSource)
        return dataSource
    }
}