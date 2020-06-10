package com.abrahamlay.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abrahamlay.base.constant.Constants
import com.abrahamlay.base.state.NetworkState
import com.abrahamlay.base.util.GlideHelper
import com.abrahamlay.domain.entities.MovieModel
import com.abrahamlay.home.databinding.ItemLoadingBinding
import com.abrahamlay.home.databinding.ItemMovieBinding

/**
 * Created by Abraham Lay on 2020-06-09.
 */
class MovieAdapter : PagedListAdapter<MovieModel, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val bindingMovie =
            ItemMovieBinding.bind(inflater.inflate(R.layout.item_movie, viewGroup, false))
        val bindingLoading =
            ItemLoadingBinding.bind(inflater.inflate(R.layout.item_loading, viewGroup, false))


        return if (viewType == TYPE_PROGRESS)
            NetworkStateItemViewHolder(bindingLoading.root)
        else MovieItemViewHolder(bindingMovie)
    }

    var onClickListener: OnClickListener? = null

    private var networkState: NetworkState? = null

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, pos: Int) {
        if (viewHolder is MovieItemViewHolder) {
            bindMovie(viewHolder, pos)
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

    private fun bindMovie(
        viewHolder: MovieItemViewHolder,
        pos: Int
    ) {
        viewHolder.itemMovieBinding.movieModel = getItem(pos)
        viewHolder.setOnClickListener(View.OnClickListener {
            onClickListener?.onItemClicked(
                getItem(pos)
            )
        })
    }

    interface OnClickListener {
        fun onItemClicked(data: MovieModel?)
    }

    inner class MovieItemViewHolder(val itemMovieBinding: ItemMovieBinding) :
        RecyclerView.ViewHolder(itemMovieBinding.root) {

        fun setOnClickListener(listener: View.OnClickListener) {
            itemView.setOnClickListener(listener)
        }
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

        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, url: String?) {
            if (!url.isNullOrEmpty()) {
                val addedUrl =
                    if (!url.contains("https://") || !url.contains("http://")) String.format(
                        Constants.MOVIE_THUMBNAIL_BASE_URL_LARGE,
                        url
                    ) else url
                GlideHelper.showImage(addedUrl, view, view.context)
            }
        }

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieModel>() {
            override fun areItemsTheSame(
                oldProduct: MovieModel,
                newProduct: MovieModel
            ): Boolean {
                return oldProduct == newProduct
            }

            override fun areContentsTheSame(
                oldProduct: MovieModel,
                newProduct: MovieModel
            ): Boolean {
                return oldProduct == newProduct
            }
        }

    }
}