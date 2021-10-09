package com.ibnu.submission2jetpack.utils

import com.ibnu.submission2jetpack.model.DataDummyMovie
import com.ibnu.submission2jetpack.model.DataDummyPeople
import com.ibnu.submission2jetpack.model.DataDummyTv

class DataDummy {

    fun dataMovie(): List<DataDummyMovie> {
        val movieData = ArrayList<DataDummyMovie>()
        movieData.add(
            DataDummyMovie(
                "238",
                "Gabriel's Inferno Part III",
                "1972-03-14",
                "Released",
                "9.0",
                "485",
                "The final part of the film adaption of the erotic romance novel Gabriel's Inferno written by an anonymous Canadian author under the pen name Sylvain Reynard."
            )
        )
        return movieData
    }

    fun dataTVShow(): List<DataDummyTv> {
        val tvShowData = ArrayList<DataDummyTv>()
        tvShowData.add(
            DataDummyTv(
                "100",
                "I Am Not an Animal",
                "2004-05-10",
                "9.4",
                "566",
                "I Am Not An Animal is an animated comedy series about the only six talking animals in the world, whose cosseted existence in a vivisection unit is turned upside down when they are liberated by animal rights activists."
            )
        )
        return tvShowData
    }

    fun dataPeople(): List<DataDummyPeople> {
        val peopleData = ArrayList<DataDummyPeople>()

        peopleData.add(
            DataDummyPeople(
                "1734",
                "Annette O'Toole",
                "1952-04-01",
                null,
                "Annette O'Toole (born April 1, 1952) is an American actress, dancer, and songwriter. She is most recently known for portraying Martha Kent, the mother of Clark Kent on the television series Smallville."
            )
        )
        return peopleData
    }

}