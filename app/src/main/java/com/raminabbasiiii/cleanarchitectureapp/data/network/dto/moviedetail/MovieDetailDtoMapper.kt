package com.raminabbasiiii.cleanarchitectureapp.data.network.dto.moviedetail

import com.raminabbasiiii.cleanarchitectureapp.domain.model.MovieDetail
import com.raminabbasiiii.cleanarchitectureapp.domain.util.DomainMapper

class MovieDetailDtoMapper: DomainMapper<MovieDetailDto,MovieDetail> {

    override fun mapToDomainModel(model: MovieDetailDto): MovieDetail {
        return MovieDetail(
            id = model.id,
            title = model.title,
            poster = model.poster,
            year = model.year,
            country = model.country,
            rating = model.rating,
            rated = model.rated,
            released = model.released,
            runtime = model.runtime,
            director = model.director,
            writer = model.writer,
            actors = model.actors,
            plot = model.plot,
            awards = model.awards,
            votes = model.votes,
            genres = model.genres,
            images = model.images
        )
    }

    override fun mapFromDomainModel(domainModel: MovieDetail): MovieDetailDto {
        return MovieDetailDto(
            id = domainModel.id,
            title = domainModel.title,
            poster = domainModel.poster,
            year = domainModel.year,
            country = domainModel.country,
            rating = domainModel.rating,
            rated = domainModel.rated,
            released = domainModel.released,
            runtime = domainModel.runtime,
            director = domainModel.director,
            writer = domainModel.writer,
            actors = domainModel.actors,
            plot = domainModel.plot,
            awards = domainModel.awards,
            votes = domainModel.votes,
            genres = domainModel.genres,
            images = domainModel.images
        )
    }
}