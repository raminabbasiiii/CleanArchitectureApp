package com.raminabbasiiii.cleanarchitectureapp.data.network.dto.movie

import com.raminabbasiiii.cleanarchitectureapp.domain.model.Movie
import com.raminabbasiiii.cleanarchitectureapp.domain.util.DomainMapper

class MovieDtoMapper: DomainMapper<MovieDto,Movie> {

    override fun mapToDomainModel(model: MovieDto): Movie {
        return Movie(
            id = model.id,
            title = model.title,
            poster = model.poster,
            year = model.year,
            country = model.country,
            rating = model.rating,
            genres = model.genres,
            images = model.images
        )
    }

    override fun mapFromDomainModel(domainModel: Movie): MovieDto {
        return MovieDto(
            id = domainModel.id,
            title = domainModel.title,
            poster = domainModel.poster,
            year = domainModel.year,
            country = domainModel.country,
            rating = domainModel.rating,
            genres = domainModel.genres,
            images = domainModel.images
        )
    }

    fun toDomainList(initial: List<MovieDto>): List<Movie>{
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Movie>): List<MovieDto>{
        return initial.map { mapFromDomainModel(it) }
    }

}