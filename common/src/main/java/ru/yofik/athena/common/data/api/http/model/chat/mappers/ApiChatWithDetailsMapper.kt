package ru.yofik.athena.common.data.api.http.model.chat.mappers

import ru.yofik.athena.common.data.api.common.ApiMapper
import ru.yofik.athena.common.data.api.common.MappingException
import ru.yofik.athena.common.data.api.common.mappers.ApiMessageMapper
import ru.yofik.athena.common.data.api.http.model.chat.apiEntity.ApiChatWithDetails
import ru.yofik.athena.common.data.api.http.model.common.mappers.ApiUserMapper
import ru.yofik.athena.common.domain.model.chat.ChatWithDetails
import javax.inject.Inject

class ApiChatWithDetailsMapper
@Inject
constructor(
    private val apiUserMapper: ApiUserMapper,
    private val apiMessageMapper: ApiMessageMapper
) : ApiMapper<ApiChatWithDetails?, ChatWithDetails> {
    override fun mapToDomain(apiEntity: ApiChatWithDetails?): ChatWithDetails {
        return ChatWithDetails(
            id = apiEntity?.id ?: throw MappingException("Chat Id cannot be null"),
            name = apiEntity.name.orEmpty(),
            users = apiEntity.users?.map(apiUserMapper::mapToDomain) ?: emptyList(),
            messages = apiEntity.messages?.map(apiMessageMapper::mapToDomain) ?: emptyList()
        )
    }
}
