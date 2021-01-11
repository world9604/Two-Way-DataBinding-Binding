package com.example.twowaydatabindingtest

import android.annotation.SuppressLint
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.twowaydatabindingtest.databinding.ActivityMakeChatRoomBinding

class MainActivity : AppCompatActivity() {

    private lateinit var vm: MakeChatRoomViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vm = setUpBinding()
    }

    private fun setUpBinding(): MakeChatRoomViewModel {
        return (DataBindingUtil.setContentView(this, R.layout.activity_make_chat_room)
                as ActivityMakeChatRoomBinding)
                .apply {
                    lifecycleOwner = this@MainActivity
                    vm = setUpViewModel()
                }.run {
                    vm!!
                }
    }

    private fun setUpViewModel(): MakeChatRoomViewModel {
        return obtainViewModel().apply {
//            chatRoomTag.observe(this@MakeChatRoomActivity, logIsAllowedSearch2)
            isAllowedSearch.observe(this@MainActivity, test)
        }
    }

    private val test: Observer<Boolean>
        = Observer { isAllowed ->
        Log.d("test@123", "isAllowed : ${isAllowed}")
    }

    private fun obtainViewModel(): MakeChatRoomViewModel = obtainViewModel(MakeChatRoomViewModel::class.java)

    fun <T : ViewModel> AppCompatActivity.obtainViewModel(viewModelClass: Class<T>) =
            ViewModelProvider(viewModelStore, ViewModelFactory.getInstance(application)).get(viewModelClass)

    class ViewModelFactory private constructor(
//        private val companyRepository: CompanyRepository
    ) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>) =
                with(modelClass) {
                    when {
                       isAssignableFrom(MakeChatRoomViewModel::class.java) ->
                            MakeChatRoomViewModel()
                        else ->
                            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                    }
                } as T

        companion object {
            @SuppressLint("StaticFieldLeak")
            @Volatile private var INSTANCE: ViewModelFactory? = null

            fun getInstance(application: Application) =
                    INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                        INSTANCE ?: ViewModelFactory(
//                            Injection.provideTasksRepository(application.applicationContext)
                        ).also { INSTANCE = it }
                    }

            @VisibleForTesting
            fun destroyInstance() {
                INSTANCE = null
            }
        }
    }
}