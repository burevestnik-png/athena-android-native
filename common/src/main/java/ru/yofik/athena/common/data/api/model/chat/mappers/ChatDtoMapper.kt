package ru.yofik.athena.common.data.api.model.chat.mappers

import ru.yofik.athena.common.data.api.model.chat.responses.dto.ChatDto
import ru.yofik.athena.common.data.api.model.mappers.DtoMapper
import ru.yofik.athena.common.data.api.model.mappers.MappingException
import ru.yofik.athena.common.data.api.model.user.mappers.UserDtoMapper
import ru.yofik.athena.common.domain.model.chat.Chat
import javax.inject.Inject

class ChatDtoMapper @Inject constructor(
    private val userDtoMapper: UserDtoMapper,
    private val messageDtoMapper: MessageDtoMapper
) : DtoMapper<ChatDto, Chat> {
    override fun mapToDomain(entityDTO: ChatDto?): Chat {
        return Chat(
            id = entityDTO?.id ?: throw MappingException("Invalid chat chat id from server"),
            name = entityDTO.name.orEmpty(),
            users = entityDTO.userViews?.map(userDtoMapper::mapToDomain) ?: emptyList(),
            lastMessage = messageDtoMapper.mapToDomain(entityDTO.lastMessage)
        )
    }
}