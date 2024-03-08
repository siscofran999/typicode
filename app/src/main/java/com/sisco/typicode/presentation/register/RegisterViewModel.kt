package com.sisco.typicode.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sisco.typicode.data.source.AppRepositoryImpl
import com.sisco.typicode.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val appRepositoryImpl: AppRepositoryImpl) :
    ViewModel() {

    fun insertUser(user: User) {
        viewModelScope.launch { appRepositoryImpl.insertUser(user) }
    }

}