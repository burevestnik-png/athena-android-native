package ru.yofik.athena.main.domain.usecases

import ru.yofik.athena.common.domain.repositories.UserRepository
import javax.inject.Inject

class HasAccess @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(): Boolean {
        return repository.hasAccess()
    }
}