package com.example.skripsiuuu.data.database

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.skripsiuuu.data.response.DataRiwayat
import com.example.skripsiuuu.data.retrofit.ApiService
import com.example.skripsiuuu.data.utils.wrapEspressoIdlingResource

@OptIn(ExperimentalPagingApi::class)
class RiwayatRemoteMediator(
    private val database: RiwayatDatabase,
    private val apiService: ApiService,
    private val token: String
) : RemoteMediator<Int, DataRiwayat>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DataRiwayat>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        val authToken = token

        wrapEspressoIdlingResource {
            return try {
                val responseData = apiService.getRiwayat(authToken, page, state.config.pageSize).dataRiwayat
                val endOfPaginationReached = responseData.isEmpty()

                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        database.remoteKeysDao().deleteRemoteKeys()
                        database.riwayatDao().deleteAll()
                    }
                    val prevKey = if (page == 1) null else page - 1
                    val nextKey = if (endOfPaginationReached) null else page + 1
                    val keys = responseData.map {
                        RemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey)
                    }
                    database.remoteKeysDao().insertAll(keys)
                    database.riwayatDao().addRiwayat(responseData)
                }
                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            } catch (exception: Exception){
                Log.d("Remote Mediator", exception.message.toString())
                MediatorResult.Error(exception)
            }

        }

    }
    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, DataRiwayat>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeysId(data.id)
        }

    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, DataRiwayat>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, DataRiwayat>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.remoteKeysDao().getRemoteKeysId(id)
            }
        }
    }

    companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}
