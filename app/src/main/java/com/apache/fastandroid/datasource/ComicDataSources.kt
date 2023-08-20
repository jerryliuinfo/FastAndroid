package com.apache.fastandroid.datasource

import kotlinx.coroutines.flow.Flow

interface SuspendComicLocalDataSource<T> {
    suspend fun findByQuery(query: String): List<T>
    suspend fun insert(query: String, titles: List<T>)
}

interface SuspendComicRemoteDataSource<T> {
     suspend fun findByTerm(searchTerm: String): List<T>
}