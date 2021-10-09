package com.ibnu.submission2jetpack.utils

import com.ibnu.submission2jetpack.data.source.model.Genres
import com.ibnu.submission2jetpack.data.source.model.Movie
import com.ibnu.submission2jetpack.data.source.model.TvShow

class GenreConverter {

     fun displayGenre(genreIds: List<Int>) : String {
        var nameGenre = ""
        if (genreIds.size == 1) {
            val genre = GenreConverter().convertSingleGenre(genreIds)
            nameGenre = genre
        } else {
            val genres = GenreConverter().convertMultipleGenres(genreIds)

            genres.forEachIndexed { index, genre ->
                if (index == genres.lastIndex) {
                    nameGenre += genre
                } else {
                    nameGenre = "$genre, "
                }
            }

        }
        return nameGenre
    }

    fun displayGenreOnDetailMovie(movie: Movie, genres: List<Genres>): String {
        var mGenre = ""
        if (genres.size == 1) {
            mGenre = genres[0].categoryName
        } else {
            movie.movieDetailCategory.forEachIndexed { index, genre ->
                if (index == movie.movieDetailCategory.lastIndex) {
                    mGenre += genre.categoryName
                } else {
                    mGenre = "${genre.categoryName}, "
                }
            }
        }
        return mGenre
    }

    fun displayGenreOnDetailTv(tvShow: TvShow, genres: List<Genres>): String {
        var mGenre = ""
        if (genres.size == 1) {
            mGenre = genres[0].categoryName
        } else {
            tvShow.tvShowDetailCategory.forEachIndexed { index, genre ->
                if (index == tvShow.tvShowDetailCategory.lastIndex) {
                    mGenre += genre.categoryName
                } else {
                    mGenre = "${genre.categoryName}, "
                }
            }
        }
        return mGenre
    }

    private fun convertSingleGenre(genresId: List<Int>): String {
        var mGenre = ""
        for (genre in genresId) {
            when (genre) {
                28 -> mGenre = "Action"
                12 -> mGenre = "Adventure"
                16 -> mGenre = "Animation"
                35 -> mGenre = "Comedy"
                80 -> mGenre = "Crime"
                99 -> mGenre = "Documentary"
                18 -> mGenre = "Drama"
                10751 -> mGenre = "Family"
                14 -> mGenre = "Fantasy"
                36 -> mGenre = "History"
                27 -> mGenre = "Horror"
                10402 -> mGenre = "Music"
                9648 -> mGenre = "Mystery"
                10749 -> mGenre = "Romance"
                878 -> mGenre = "Science Fiction"
                10770 -> mGenre = "TV Movie"
                53 -> mGenre = "Thriller"
                10752 -> mGenre = "War"
                37 -> mGenre = "Western"
                else -> mGenre = "Unknown Genre"
            }
        }
        return mGenre
    }

    private fun convertMultipleGenres(genreIds: List<Int>): List<String> {
        val mGenres = ArrayList<String>()
        for (genre in genreIds) {
            when (genre) {
                28 -> mGenres.add("Action")
                12 -> mGenres.add("Adventure")
                16 -> mGenres.add("Animation")
                35 -> mGenres.add("Comedy")
                80 -> mGenres.add("Crime")
                99 -> mGenres.add("Documentary")
                18 -> mGenres.add("Drama")
                10751 -> mGenres.add("Family")
                14 -> mGenres.add("Fantasy")
                36 -> mGenres.add("History")
                27 -> mGenres.add("Horror")
                10402 -> mGenres.add("Music")
                9648 -> mGenres.add("Mystery")
                10749 -> mGenres.add("Romance")
                878 -> mGenres.add("Science Fiction")
                10770 -> mGenres.add("TV Movie")
                53 -> mGenres.add("Thriller")
                10752 -> mGenres.add("War")
                37 -> mGenres.add("Western")
            }
        }
        return mGenres
    }

}