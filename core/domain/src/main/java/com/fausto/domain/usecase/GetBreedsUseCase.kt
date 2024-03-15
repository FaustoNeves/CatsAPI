package com.fausto.domain.usecase

import com.fausto.data.repository.CatsRepository
import com.fausto.model.BreedsModel
import com.fausto.network.mapper.toModel
import javax.inject.Inject

fun interface GetBreedsUseCase {
    suspend fun getBreeds(): List<BreedsModel>
}

class GetBreedsUseCaseImpl @Inject constructor(private val catsRepository: CatsRepository) :
    GetBreedsUseCase {
    override suspend fun getBreeds(): List<BreedsModel> {
        return catsRepository.getBreeds().map { it.toModel() }
    }
}