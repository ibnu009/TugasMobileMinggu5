package com.ibnu.submission2jetpack.webservice.jsonobject

import com.google.gson.annotations.SerializedName
import com.ibnu.submission2jetpack.data.source.model.Cast
import com.ibnu.submission2jetpack.data.source.model.Movie
import com.ibnu.submission2jetpack.data.source.model.People
import com.ibnu.submission2jetpack.data.source.model.TvShow

data class Result(
    @field:SerializedName("results")
    val result: List<Movie>,
    @field:SerializedName("total_pages")
    val totalPage: Int
)

data class ResultTvShow(
    @field:SerializedName("results")
    val result: List<TvShow>,
    @field:SerializedName("total_pages")
    val totalPage: Int
)

data class CastsResult(
    @field:SerializedName("cast")
    val listCasts: List<Cast>
)

data class ResultPeople(
    @field:SerializedName("results")
    val result: List<People>,
    @field:SerializedName("total_pages")
    val totalPage: Int
)

