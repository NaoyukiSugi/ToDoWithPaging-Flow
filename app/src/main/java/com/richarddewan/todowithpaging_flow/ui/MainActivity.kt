package com.richarddewan.todowithpaging_flow.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.richarddewan.todowithpaging_flow.R
import com.richarddewan.todowithpaging_flow.data.paging.TaskPagingSource
import com.richarddewan.todowithpaging_flow.data.remote.network.Network
import com.richarddewan.todowithpaging_flow.data.repository.TaskRepositoryImpl

class MainActivity : AppCompatActivity() {

    private val BASE_URL = "https://freeapi.rdewan.dev/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val networkService = Network.create(BASE_URL, application.cacheDir, 10 * 1024 * 1024)
        val taskPagingSource = TaskPagingSource(networkService)
        val repository = TaskRepositoryImpl(taskPagingSource)
        val viewModel = ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)

        viewModel.getTaskList()
    }

    class MainViewModelFactory(private val repositoryImpl: TaskRepositoryImpl) :
            ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(
                    TaskRepositoryImpl::class.java
            ).newInstance(repositoryImpl)
        }
    }
}
