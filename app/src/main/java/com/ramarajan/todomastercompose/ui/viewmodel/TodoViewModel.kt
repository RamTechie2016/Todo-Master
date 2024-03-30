package com.ramarajan.todomastercompose.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.ramarajan.todomastercompose.common.dispatcher.DispatcherProvider
import com.ramarajan.todomastercompose.common.networkhelper.NetworkHelper
import com.ramarajan.todomastercompose.common.utils.Constants
import com.ramarajan.todomastercompose.data.local.entity.TodoEntity
import com.ramarajan.todomastercompose.data.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val todoRepository: TodoRepository,
    private val pager: Pager<Int, TodoEntity>,
    private val dispatcherProvider: DispatcherProvider,
    private val networkHelper: NetworkHelper,
) : ViewModel() {
    private var pageNum = Constants.DEFAULT_PAGE_NUM
    private val _todoItemPaging = MutableStateFlow<PagingData<TodoEntity>>(PagingData.empty())
    val todoItemPaging: StateFlow<PagingData<TodoEntity>> = _todoItemPaging
    private val _todoEntity = MutableStateFlow<TodoEntity?>(null)
    val todoItemData: StateFlow<TodoEntity?> = _todoEntity
    init {
        fetchTodos()
    }

    fun fetchTodos() {
        viewModelScope.launch {
            pager.flow.cachedIn(viewModelScope).map {
                it.filter {todoEntity ->
                    todoEntity.todo?.isNotEmpty() == true
                }
            }.collect{
                _todoItemPaging.value = it
            }
        }
    }

  fun getTodoById(todoId : Int){
      CoroutineScope(Dispatchers.IO).launch {
          _todoEntity.value = todoRepository.getTodoById(todoId)
          println("Entity : "+_todoEntity.value )
      }
  }

}