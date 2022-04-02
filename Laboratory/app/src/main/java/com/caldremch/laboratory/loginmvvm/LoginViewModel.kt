package com.caldremch.laboratory.loginmvvm

import android.app.Application
import android.os.Looper
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 *
 * @author Caldremch
 *
 * @date 2022/1/16
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/

const val TAG = "LoginViewModel"

class UserData(val username: String,val password: String){
    override fun toString(): String {
        return "UserData(username='$username', password='$password')"
    }
}

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val repository by lazy { LoginRepository() }
    private val _loginObs = MutableLiveData<UserData>()
    val loginObs: LiveData<UserData> = _loginObs
    fun login(username: String, password: String) {
        viewModelScope.launch {
            repository.login(username, password)
                .onStart {

                }
                .catch {

                }
                .onCompletion {

                }
                .collect {
                    Log.d(TAG, "LoginViewModel 主线程: ${Thread.currentThread() == Looper.getMainLooper().thread}")
                    _loginObs.postValue(it)
                }
        }

    }

}

class LoginRepository {
    fun login(username: String, password: String): Flow<UserData> {
        return flow<UserData> {
            val userData = UserData(username, password)
            Log.d(TAG, "LoginRepository 主线程: ${Thread.currentThread() == Looper.getMainLooper().thread}")
            emit(userData)
        }.flowOn(Dispatchers.IO)
    }
}

