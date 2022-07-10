package com.example.domain

import javax.inject.Inject

class DeleteEntityListUseCase @Inject constructor(
    private val dfRepository: DFRepository
) {
    suspend operator fun invoke() {
       return dfRepository.deleteEntityList()
    }
}