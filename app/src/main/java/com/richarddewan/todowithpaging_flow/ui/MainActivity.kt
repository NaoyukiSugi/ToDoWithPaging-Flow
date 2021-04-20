package com.richarddewan.todowithpaging_flow.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.richarddewan.todowithpaging_flow.data.paging.TaskPagingSource
import com.richarddewan.todowithpaging_flow.data.remote.network.Network
import com.richarddewan.todowithpaging_flow.data.repository.TaskRepositoryImpl
import com.richarddewan.todowithpaging_flow.databinding.ActivityMainBinding
import com.richarddewan.todowithpaging_flow.ui.adapter.TaskPagingAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val BASE_URL = "https://freeapi.rdewan.dev/"
    private lateinit var taskPagingAdapter: TaskPagingAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val networkService = Network.create(BASE_URL, application.cacheDir, 10 * 1024 * 1024)
        val taskPagingSource = TaskPagingSource(networkService)
        val repository = TaskRepositoryImpl(taskPagingSource)
        viewModel =
            ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)

        taskPagingAdapter = TaskPagingAdapter()
        binding.rvTask.apply {
            layoutManager = linearLayoutManager
            adapter = taskPagingAdapter
        }

        // observe the viewmodel live data
        observers()
    }

    private fun observers() {
        lifecycleScope.launch {
            viewModel.getTaskList()
                .collectLatest {
                    taskPagingAdapter.submitData(it)
                }
        }
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
