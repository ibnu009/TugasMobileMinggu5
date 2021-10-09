package com.ibnu.submission2jetpack.data.source.remote.status.people

import com.ibnu.submission2jetpack.data.source.model.People

interface ResponseStatusPeople {
    fun onSuccess(list: List<People>)
    fun onFailed(message: String)
}