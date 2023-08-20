package com.apache.fastandroid.datasource

import com.apache.fastandroid.demo.room.Account
import com.apache.fastandroid.demo.room.AccountDatabase
import com.apache.fastandroid.network.api.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Jerry on 2023/8/6.
 */
class SuspendSearchRemoteDataSource(private val apiService: ApiService):SuspendComicRemoteDataSource<String> {

    //    override suspend fun findByTerm(searchTerm: String): List<String> {
//        return apiService.listReposKt(searchTerm).map {
//           it.name
//        }
//    }
    override suspend fun findByTerm(searchTerm: String): List<String> {
                return apiService.listReposKt(searchTerm).map {
           it.name
        }
    }
}

class SuspendSearchLocalDataSource(
    private val database: AccountDatabase
) : SuspendComicLocalDataSource<String> {
    override suspend fun insert(query: String, titles: List<String>) =
        database.plantDao().insert(Account(name = query,description= "", id = 1000, age = 20))


    override suspend fun findByQuery(query: String): List<String> {
        return database.plantDao().queryAll().map {
            it.name
        }
    }
}