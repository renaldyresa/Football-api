package com.example.sub2kotlinexpert.data

interface OperationCallback<T> {
    fun onSuccess(data: T?)
    fun onError(error:String)
}