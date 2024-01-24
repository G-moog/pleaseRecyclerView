package com.example.myapplication.retrofit.data

sealed class Result<T>(
    val data : T? = null,
    val message : String ? = null,
    val e : Throwable? = null
){
    // 200 ~ 299
    class Success<T>(data: T?) : Result<T>(data)

    // 4, 500 에러
    class Fail<T>(message : String) : Result<T>(message = message)

    // 예외
    class Error<T>(e : Throwable) : Result<T>(e = e)

    // 로딩
    class Loading<T> : Result<T>()

    // 빈 값
    class Empty<T> :  Result<T>()

}
