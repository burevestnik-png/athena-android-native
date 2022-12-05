package ru.yofik.athena.common.domain.model.exceptions

/** Exception which is used in repositories and wraps to domain retrofits HttpException */
class NetworkException(message: String) : RuntimeException(message)
