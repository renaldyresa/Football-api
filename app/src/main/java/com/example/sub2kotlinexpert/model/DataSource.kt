package com.example.sub2kotlinexpert.model

import com.example.sub2kotlinexpert.data.OperationCallback

interface DataSource<T> {

    fun retrieveData(id: String, callback: OperationCallback<T>)
    fun cancel()

}