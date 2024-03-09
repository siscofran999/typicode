package com.sisco.typicode.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sisco.typicode.data.source.AppRepositoryImpl
import com.sisco.typicode.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val appRepositoryImpl: AppRepositoryImpl) :
    ViewModel() {

    val getPhotos = appRepositoryImpl.getPhotos().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        DataState.Loading
    )

}