package ru.yofik.athena.common.data.api.model.chat.mappers

import javax.inject.Inject
import ru.yofik.athena.common.data.api.model.chat.responses.dto.ChatWithDetailsDto
import ru.yofik.athena.common.data.api.model.chat.responses.dto.MessageDto
import ru.yofik.athena.common.data.api.model.mappers.DtoMapper
import ru.yofik.athena.common.data.api.model.mappers.MappingException
import ru.yofik.athena.common.data.api.model.user.mappers.UserDtoMapper
import ru.yofik.athena.common.domain.model.chat.ChatWithDetails
import ru.yofik.athena.common.domain.model.chat.details.Details

class ChatWithDetailsDtoMapper
@Inject
constructor(private val userDtoMapper: UserDtoMapper, private val detailsMapper: DetailsMapper) :
    DtoMapper<ChatWithDetailsDto?, ChatWithDetails> {
    override fun mapToDomain(entityDTO: ChatWithDetailsDto?): ChatWithDetails {
        return ChatWithDetails(
            id = entityDTO?.id ?: throw MappingException("Chat Id cannot be null"),
            name = entityDTO.name.orEmpty(),
            users = entityDTO.users?.map(userDtoMapper::mapToDomain) ?: emptyList(),
            details = detailsMapper.mapToDomain(entityDTO.message)
        )
    }
}

class DetailsMapper @Inject constructor(private val messageDtoMapper: MessageDtoMapper) :
    DtoMapper<List<MessageDto>?, Details> {
    override fun mapToDomain(entityDTO: List<MessageDto>?): Details {
        return Details(messages = entityDTO?.map(messageDtoMapper::mapToDomain) ?: emptyList())
    }
}
