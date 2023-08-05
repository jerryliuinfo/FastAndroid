package com.apache.fastandroid.demo.blockstore

import com.google.android.gms.auth.blockstore.BlockstoreClient
import com.google.android.gms.auth.blockstore.StoreBytesData
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

/**
 * The Repository handles data operations and provides a clean API so that the rest of the app can
 * retrieve the Block Store data easily.
 */
class BlockStoreRepository  constructor(
    private val blockStoreClient: BlockstoreClient
) {

    // This function saves the authentication token to Block Store.
    suspend fun storeBytes(inputString: String) {
        val data: StoreBytesData =
            StoreBytesData.Builder().setBytes(inputString.toByteArray()).build()
        blockStoreClient.storeBytes(data).await()
    }

    // This function retrieves your Block Store data.
    suspend fun retrieveBytes(): String {
        return String(blockStoreClient.retrieveBytes().await())
    }

    // This function clears your Block Store data.
    suspend fun clearBytes() {
        storeBytes("")
    }
}