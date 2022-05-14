package ru.yofik.athena.common.data.api.http.model.chat.mappers

import javax.inject.Inject
import ru.yofik.athena.common.data.api.http.model.chat.dto.ApiChatWithDetails
import ru.yofik.athena.common.data.api.http.model.user.mappers.ApiUserMapper
import ru.yofik.athena.common.data.api.mappers.ApiMapper
import ru.yofik.athena.common.data.api.mappers.ApiMessageMapper
import ru.yofik.athena.common.data.api.mappers.MappingException
import ru.yofik.athena.common.domain.model.chat.ChatWithDetails

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
