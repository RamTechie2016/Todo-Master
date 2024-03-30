package com.ramarajan.todomastercompose.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ramarajan.todomastercompose.R
import com.ramarajan.todomastercompose.common.utils.NoInternetException
import com.ramarajan.todomastercompose.data.local.entity.TodoEntity
import com.ramarajan.todomastercompose.ui.common.ShowError
import com.ramarajan.todomastercompose.ui.common.ShowLoading
import com.ramarajan.todomastercompose.ui.common.getRandomLightBackgroundColor
import com.ramarajan.todomastercompose.ui.viewmodel.TodoViewModel


@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    todoViewModel: TodoViewModel = hiltViewModel(),
    onEditClicked: (Int) -> Unit
) {
    val pagingResponse = todoViewModel.todoItemPaging.collectAsLazyPagingItems()

    var isRefreshing by rememberSaveable { mutableStateOf(false) }


    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            todoViewModel.fetchTodos()
        }
    )
    val showDialog = remember { mutableStateOf(false) }
    val todo = remember { mutableStateOf<TodoEntity?>(null) }


    if (showDialog.value)
        TodoDetailModal(value = todo.value, setShowDialog = {
            showDialog.value = it
        }) {
            onEditClicked.invoke(it)
        }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todo Master") },
                actions = {
                    IconButton(onClick = { /* Do something */ }) {
                        Image(
                            painter = painterResource(R.drawable.add),
                            contentDescription = "Local Drawable",

                            )
                    }
                }
            )
        },
    ) {
        Box(
            modifier = Modifier.padding(it)
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
        ) {
            when (pagingResponse.loadState.refresh) {

                is LoadState.Loading -> {
                    if (!isRefreshing)
                        ShowLoading()
                }

                is LoadState.Error -> {
                    isRefreshing = false
                    var errorText = stringResource(id = R.string.app_name)
                    if ((pagingResponse.loadState.refresh as LoadState.Error).error is NoInternetException) {
                        errorText = stringResource(id = R.string.app_name)
                    }
                    ShowError(
                        text = errorText,
                        retryEnabled = true
                    ) {
                        todoViewModel.fetchTodos()
                    }
                }

                else -> {
                    isRefreshing = false

                    TodoPagingAppend(pagingResponse) {
                        showDialog.value = true
                        todo.value = it
                    }

                }
            }
            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }

    }
}


@Composable
private fun TodoPagingAppend(
    pagingResponse: LazyPagingItems<TodoEntity>,
    newsClicked: (TodoEntity) -> Unit,
) {

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(pagingResponse.itemCount) {
                if (pagingResponse[it] != null) {
                    TodoItem(pagingResponse[it]!!) { article ->
                        newsClicked(article)
                    }
                }
            }
            pagingResponse.apply {
                when (loadState.append) {
                    is LoadState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.align(Alignment.Center),
                                    strokeWidth = 1.dp
                                )
                            }
                        }
                    }

                    is LoadState.Error -> {
                        item {
                            ShowError(
                                text = stringResource(id = R.string.app_name),
                                retryEnabled = true
                            ) {
                                pagingResponse.retry()
                            }
                        }
                    }

                    else -> {}
                }
            }
        }
        , modifier = Modifier.fillMaxSize()
    )
//    LazyColumn {
//        items(pagingResponse.itemCount) {
//            if (pagingResponse[it] != null) {
//                TodoItem(pagingResponse[it]!!) { article ->
//                    newsClicked(article)
//                }
//            }
//        }
//        pagingResponse.apply {
//            when (loadState.append) {
//                is LoadState.Loading -> {
//                    item {
//                        Box(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(8.dp),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            CircularProgressIndicator(
//                                modifier = Modifier.align(Alignment.Center),
//                                strokeWidth = 1.dp
//                            )
//                        }
//                    }
//                }
//
//                is LoadState.Error -> {
//                    item {
//                        ShowError(
//                            text = stringResource(id = R.string.app_name),
//                            retryEnabled = true
//                        ) {
//                            pagingResponse.retry()
//                        }
//                    }
//                }
//
//                else -> {}
//            }
//        }
//    }
}

@Composable
fun TodoItem(article: TodoEntity, onItemClick: (TodoEntity) -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(4.dp)
        .clickable {
            onItemClick(article)
        }) {
        Row() {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(getRandomLightBackgroundColor()),
                verticalArrangement = Arrangement.Center
            ) {
                article.todo?.let {
                    Text(
                        text = it,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if(article.completed == true) {
                            Icon(
                                imageVector = Icons.Rounded.CheckCircle,
                                modifier = Modifier.padding(6.dp),
                                contentDescription = "status"
                            )
                        }else{
                            Icon(
                                imageVector = Icons.Outlined.CheckCircle,
                                modifier = Modifier.padding(6.dp),
                                contentDescription = "status"
                            )
                        }

                        Icon(
                            imageVector = Icons.Rounded.Delete,
                            modifier = Modifier.padding(6.dp),
                            contentDescription = "status"
                        )
                    }
                }

            }
        }
    }
}
