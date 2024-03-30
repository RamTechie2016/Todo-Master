package com.ramarajan.todomastercompose.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ramarajan.todomastercompose.common.dispatcher.DispatcherProvider
import com.ramarajan.todomastercompose.common.networkhelper.NetworkHelper
import com.ramarajan.todomastercompose.common.utils.Constants
import com.ramarajan.todomastercompose.common.utils.NoInternetException
import com.ramarajan.todomastercompose.data.local.entity.TodoEntity
import com.ramarajan.todomastercompose.data.repository.TodoRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoPagingSource @Inject constructor(
    private val todoRepository: TodoRepository,
    private val networkHelper: NetworkHelper,
    private val dispatcherProvider: DispatcherProvider
) : PagingSource<Int, TodoEntity>() {

    override fun getRefreshKey(state: PagingState<Int, TodoEntity>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TodoEntity> {
        val page = params.key ?: 1
        lateinit var loadResult: LoadResult<Int, TodoEntity>

        withContext(dispatcherProvider.io) {
            kotlin.runCatching {
                if (!networkHelper.isNetworkConnected()) {
                    if (page == Constants.DEFAULT_PAGE_NUM) {
                        val todos = todoRepository.getTodoFromLocal()
                        loadResult = LoadResult.Page(
                            data = todos,
                            prevKey = page.minus(1),
                            nextKey = if (todos.isEmpty()) null else page.plus(1)
                        )
                    } else {
                        throw NoInternetException()
                    }
                } else {
                    val todos : List<TodoEntity> = todoRepository.getAllTodo(page)!!
                    loadResult = LoadResult.Page(
                        data = todos,
                        prevKey = if (page == 1) null else page.minus(1),
                        nextKey = if (todos.isEmpty()) null else page.plus(1)
                    )
                }
            }.onFailure {
                loadResult = LoadResult.Error(it)
            }
        }
        return loadResult

    }
}