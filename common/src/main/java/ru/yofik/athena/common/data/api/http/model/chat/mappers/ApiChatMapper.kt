package ru.yofik.athena.common.data.api.http.model.chat.mappers

import ru.yofik.athena.common.data.api.common.ApiMapper
import ru.yofik.athena.common.data.api.common.MappingException
import ru.yofik.athena.common.data.api.common.mappers.ApiMessageMapper
import ru.yofik.athena.common.data.api.http.model.chat.apiEntity.ApiChat
import ru.yofik.athena.common.data.api.http.model.common.mappers.ApiUserMapper
import ru.yofik.athena.common.domain.model.chat.Chat
import ru.yofik.athena.common.domain.model.chat.ChatType
import javax.inject.Inject

class ApiChatMapper
@Inject
constructor(
    private val apiUserMapper: ApiUserMapper,
    private val apiMessageMapper: ApiMessageMapper
) : ApiMapper<ApiChat, Chat> {
    override fun mapToDomain(apiEntity: ApiChat?): Chat {
        return Chat(
            id = apiEntity?.id ?: throw MappingException("Invalid chat chat id from server"),
            type = apiEntity.type ?: throw MappingException("Chat must have type"),
            name = apiEntity.name.orEmpty(),
            users = apiEntity.userViews?.map(apiUserMapper::mapToDomain) ?: emptyList(),
            lastMessage = apiMessageMapper.mapToDomain(apiEntity.lastMessage)
        )
    }
}
