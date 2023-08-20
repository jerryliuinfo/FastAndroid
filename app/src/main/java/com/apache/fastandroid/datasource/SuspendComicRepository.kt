package com.apache.fastandroid.datasource

abstract class SuspendComicRepository<T>(
    private val local: SuspendComicLocalDataSource<T>,
    private val remote: SuspendComicRemoteDataSource<T>
) {
    suspend fun findByQuery(query: String): List<T> =
        local.findByQuery(query)

    private suspend fun find(query: String): List<T> =
        query.run { fetch(query) } ?: search(query)

    private suspend fun search(term: String): List<T> {
        return remote.findByTerm(term).apply {
            save(this, term)
        }
    }

    private suspend fun save(
        list: List<T>,
        query: String
    ) {
        local.insert(query, list)
    }


    private suspend fun fetch(query: String): List<T> =
        local.findByQuery(query)

}