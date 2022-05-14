package ru.yofik.athena.common.data.api.http.model.chat.mappers

import ru.yofik.athena.common.data.api.http.model.chat.dto.ApiChat
import ru.yofik.athena.common.data.api.mappers.ApiMapper
import ru.yofik.athena.common.data.api.mappers.MappingException
import ru.yofik.athena.common.data.api.http.model.user.mappers.ApiUserMapper
import ru.yofik.athena.common.data.api.mappers.ApiMessageMapper
import ru.yofik.athena.common.domain.model.chat.Chat
import javax.inject.Inject

class ApiChatMapper @Inject constructor(
    private val apiUserMapper: ApiUserMapper,
    private val apiMessageMapper: ApiMessageMapper
) : ApiMapper<ApiChat, Chat> {
    override fun mapToDomain(apiEntity: ApiChat?): Chat {
        return Chat(
            id = apiEntity?.id ?: throw MappingException("Invalid chat chat id from server"),
            name = apiEntity.name.orEmpty(),
            users = apiEntity.userViews?.map(apiUserMapper::mapToDomain) ?: emptyList(),
            lastMessage = apiMessageMapper.mapToDomain(apiEntity.lastMessage)
        )
    }
}