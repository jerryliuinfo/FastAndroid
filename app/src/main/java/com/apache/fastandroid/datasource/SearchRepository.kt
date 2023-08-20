package com.apache.fastandroid.datasource


class SuspendSearchRepository(
        local: SuspendSearchLocalDataSource,
        remote: SuspendSearchRemoteDataSource
) : SuspendComicRepository<String>(local, remote)
