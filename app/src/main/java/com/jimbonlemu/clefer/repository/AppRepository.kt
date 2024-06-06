package com.jimbonlemu.clefer.repository

import com.jimbonlemu.clefer.source.local.LocalDataSource
import com.jimbonlemu.clefer.source.remote.RemoteDataSource
import kotlinx.coroutines.flow.flow

class AppRepository (val localDataSource: LocalDataSource, val remoteDataSource: RemoteDataSource) {

}