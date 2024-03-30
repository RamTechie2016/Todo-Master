package com.ramarajan.todomastercompose.di.module

import android.app.Application
import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.ramarajan.todomastercompose.common.dispatcher.DefaultDispatcherProvider
import com.ramarajan.todomastercompose.common.dispatcher.DispatcherProvider
import com.ramarajan.todomastercompose.common.networkhelper.NetworkHelper
import com.ramarajan.todomastercompose.common.networkhelper.NetworkHelperImpl
import com.ramarajan.todomastercompose.common.utils.Constants
import com.ramarajan.todomastercompose.data.local.db.TodoDatabase
import com.ramarajan.todomastercompose.data.local.entity.TodoEntity
import com.ramarajan.todomastercompose.data.local.service.DatabaseService
import com.ramarajan.todomastercompose.data.local.service.TodoDatabaseService
import com.ramarajan.todomastercompose.data.network.TodoApiService
import com.ramarajan.todomastercompose.di.BaseUrl
import com.ramarajan.todomastercompose.di.DbName
import com.ramarajan.todomastercompose.ui.paging.TodoPagingSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = Constants.BASE_URL

    @DbName
    @Provides
    fun provideDbName(): String = Constants.DB_NAME

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideNetworkHelper(
        @ApplicationContext context: Context
    ): NetworkHelper {
        return NetworkHelperImpl(context)
    }

    @Provides
    @Singleton
    fun provideDispatcher(): DispatcherProvider = DefaultDispatcherProvider()

    @Provides
    @Singleton
    fun provideDatabaseService(articleDatabase: TodoDatabase): DatabaseService {
        return TodoDatabaseService(articleDatabase)
    }

    @Provides
    @Singleton
    fun providePager(
        newsPagingSource: TodoPagingSource
    ): Pager<Int, TodoEntity> {
        return Pager(
            config = PagingConfig(
                Constants.DEFAULT_QUERY_PAGE_SIZE
            )
        ) {
            newsPagingSource
        }
    }

    @Singleton
    @Provides
    fun provideNetworkService(
        @BaseUrl baseUrl: String,
        gsonFactory: GsonConverterFactory,
    ): TodoApiService {
        val client = OkHttpClient
            .Builder()
            .build()

        return Retrofit
            .Builder()
            .client(client) //adding client to intercept all responses
            .baseUrl(baseUrl)
            .addConverterFactory(gsonFactory)
            .build()
            .create(TodoApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideTodoDatabase(
        application: Application,
        @DbName dbName: String
    ): TodoDatabase {
        return Room.databaseBuilder(
            application,
            TodoDatabase::class.java,
            dbName
        ).build()
    }

}
