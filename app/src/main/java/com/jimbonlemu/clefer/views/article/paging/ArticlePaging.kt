package com.jimbonlemu.clefer.views.article.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jimbonlemu.clefer.source.remote.RemoteDataSource
import com.jimbonlemu.clefer.source.remote.response.DataItemItem

class ArticlePaging(private val remoteDataSource: RemoteDataSource) : PagingSource<Int, DataItemItem>() {
    private companion object {
        const val INITIAL_PAGE_INDEX = 1
        const val TAG = "ArticlePaging"
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItemItem> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            Log.d(TAG, "Loading page: $page")
            val response = remoteDataSource.getAllArticles(page, params.loadSize)

            if (response.isSuccessful) {
                val nestedListArticles = response.body()?.data ?: emptyList()
                // Flattening the list of lists
                val listArticles = nestedListArticles.flatten()
                Log.d(TAG, "Data size: ${listArticles.size}")
                LoadResult.Page(
                    data = listArticles,
                    prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                    nextKey = if (listArticles.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(Throwable("Failed to load data"))
            }
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DataItemItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
