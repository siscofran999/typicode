package com.sisco.typicode.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sisco.typicode.data.source.AppRepositoryImpl
import com.sisco.typicode.domain.model.Login
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class LoginViewModel @Inject constructor(private val appRepositoryImpl: AppRepositoryImpl) :
    ViewModel() {

    private var loginModel = MutableSharedFlow<Login>(replay = 1)

    fun setLogin(email: String, pass: String) {
        this.loginModel.tryEmit(Login(email, pass))
    }

    val login = this.loginModel.flatMapLatest {
        appRepositoryImpl.getUserFromEmail(it)
    }.shareIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000),
        1
    )

}