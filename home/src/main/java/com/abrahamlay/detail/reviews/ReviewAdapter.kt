package com.abrahamlay.detail.reviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abrahamlay.base.state.NetworkState
import com.abrahamlay.domain.entities.ReviewModel
import com.abrahamlay.home.R
import com.abrahamlay.home.databinding.ItemLoadingBinding
import com.abrahamlay.home.databinding.ItemReviewsBinding

class ReviewAdapter : PagedListAdapter<ReviewModel, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val itemReviewsBinding =
            ItemReviewsBinding.bind(inflater.inflate(R.layout.item_reviews, viewGroup, false))
        val bindingLoading =
            ItemLoadingBinding.bind(inflater.inflate(R.layout.item_loading, viewGroup, false))


        return if (viewType == TYPE_PROGRESS)
            NetworkStateItemViewHolder(bindingLoading.root)
        else ReviewItemViewHolder(itemReviewsBinding)
    }

    private var networkState: NetworkState? = null

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, pos: Int) {
        if (viewHolder is ReviewItemViewHolder) {
            bindReview(viewHolder, pos)
        } else {
            (viewHolder as NetworkStateItemViewHolder).bindView(networkState)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            TYPE_PROGRESS
        } else {
            TYPE_ITEM
        }
    }

    private fun bindReview(
        viewHolder: ReviewItemViewHolder,
        pos: Int
    ) {
        viewHolder.itemMovieBinding.reviewModel = getItem(pos)
    }


    inner class ReviewItemViewHolder(val itemMovieBinding: ItemReviewsBinding) :
        RecyclerView.ViewHolder(itemMovieBinding.root) {

    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val previousExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val newExtraRow = hasExtraRow()
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(itemCount)
            } else {
                notifyItemInserted(itemCount)
            }
        } else if (newExtraRow && previousState !== newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState !== NetworkState.LOADED
    }

    inner class NetworkStateItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val progressBar = itemView.findViewById<View>(R.id.pbLoading)
        fun bindView(networkState: NetworkState?) {
            if (networkState != null && networkState.status === NetworkState.Status.RUNNING) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }

            if (networkState != null && networkState.status === NetworkState.Status.FAILED) {
                Toast.makeText(itemView.context, networkState.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val TYPE_PROGRESS = 0
        private const val TYPE_ITEM = 1

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ReviewModel>() {
            override fun areItemsTheSame(
                oldProduct: ReviewModel,
                newProduct: ReviewModel
            ): Boolean {
                return oldProduct == newProduct
            }

            override fun areContentsTheSame(
                oldProduct: ReviewModel,
                newProduct: ReviewModel
            ): Boolean {
                return oldProduct == newProduct
            }
        }

    }
}
