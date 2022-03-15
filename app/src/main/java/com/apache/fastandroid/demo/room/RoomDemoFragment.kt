package com.apache.fastandroid.demo.room

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentJetpackRoomBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicInteger
import kotlin.random.Random

/**
 * Created by Jerry on 2021/3/17.
 */
class RoomDemoFragment: BaseVBFragment<FragmentJetpackRoomBinding>(FragmentJetpackRoomBinding::inflate) {

    private lateinit var plantDao: AccountDao
    private lateinit var mAdapter: PlantAdapter
    private val atomicInteger = AtomicInteger()

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        plantDao = AccountDatabase.getInstance()
        mAdapter = PlantAdapter()
        viewBinding.recyclerView.apply {
            adapter = mAdapter
        }
        viewBinding.btnInsert.setOnClickListener {
            operation {
                val plants = Array<Account>(10){
                    val id = atomicInteger.addAndGet(1)
                    Account("name:$id", "desc:$id", id.toLong(), (id).toLong())
                }
                plantDao.insert(plants.toList())
            }
        }
        viewBinding.btnInsertNoQuery.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO){
                    val id = atomicInteger.addAndGet(1)
                    val plant = Account("name:$id", "desc:${id.toLong()}", id.toLong(), id.toLong())
                    plantDao.insert(plant)
                }
            }
        }


        viewBinding.btnDelete.setOnClickListener {
            operation {
                plantDao.delete(Account("内容2", "", 2))
            }
        }

        viewBinding.btnDeleteAll.setOnClickListener {
            operation {
                plantDao.deleteAll()
            }
        }


        viewBinding.btnModify.setOnClickListener {
            operation {
                val plant = Account(id = 1, name = "内容2", description =  "内容:${Random.nextInt(10)}")
                plantDao.update(plant)
            }

        }
        viewBinding.btnQueryByCondition.setOnClickListener {
            lifecycleScope.launch {
                val queryList = withContext(Dispatchers.IO){
                    return@withContext plantDao.queryByAge(5)
                }
                mAdapter.setNewData(queryList)
            }
        }
        viewBinding.btnQueryAll.setOnClickListener {

            operation {

            }
        }



        plantDao.queryAllLiveData().observe(this){
            Logger.d("queryAllLiveData onChange:${it}")
            updateView(it)
        }


    }

    private fun operation(block:() -> Unit){
        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                block.invoke()
            }
            queryAll()
        }

    }

    private suspend fun queryAll(){
        val plants = withContext(Dispatchers.IO){
            plantDao.queryAll()
        }
        updateView(plants)
    }


    private fun  updateView(list:List<Account>){
        mAdapter.setNewData(list)
    }

    private class PlantAdapter:BaseQuickAdapter<Account,BaseViewHolder>(R.layout.item_plant){
        override fun convert(helper: BaseViewHolder, item: Account) {
            helper.setText(R.id.tv_id, item.id.toString())
            helper.setText(R.id.tv_name, item.name)
            helper.setText(R.id.tv_desc, item.description)
        }


    }
}