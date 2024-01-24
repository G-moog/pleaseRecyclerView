package com.example.myapplication.retrofit.data

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class PostRepository {

    fun findAll() = flow{

        // 로딩
        emit(Result.Loading())

        val response = true
        // 서버
        if(response){
            emit(Result.Success("성공했습니다."))
        } else {
            emit(Result.Fail("실패했습니다."))
        }
            // 성공

            // 실패

    }.catch{
        // 에러

        emit(Result.Error(it))
    }




}