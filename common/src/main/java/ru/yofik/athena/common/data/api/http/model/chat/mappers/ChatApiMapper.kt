package ru.yofik.athena.common.data.api.http.model.chat.mappers

import ru.yofik.athena.common.data.api.http.model.chat.responses.dto.ChatDto
import ru.yofik.athena.common.data.api.mapping.ApiMapper
import ru.yofik.athena.common.data.api.mapping.MappingException
import ru.yofik.athena.common.data.api.http.model.user.mappers.UserApiMapper
import ru.yofik.athena.common.domain.model.chat.Chat
import javax.inject.Inject

class ChatApiMapper @Inject constructor(
    private val userDtoMapper: UserApiMapper,
    private val messageDtoMapper: MessageApiMapper
) : ApiMapper<ChatDto, Chat> {
    override fun mapToDomain(entityDTO: ChatDto?): Chat {
        return Chat(
            id = entityDTO?.id ?: throw MappingException("Invalid chat chat id from server"),
            name = entityDTO.name.orEmpty(),
            users = entityDTO.userViews?.map(userDtoMapper::mapToDomain) ?: emptyList(),
            lastMessage = messageDtoMapper.mapToDomain(entityDTO.lastMessage)
        )
    }
}