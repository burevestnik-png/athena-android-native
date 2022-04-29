package ru.yofik.athena.common.data.api.http.model.chat.mappers

import javax.inject.Inject
import ru.yofik.athena.common.data.api.http.model.chat.responses.dto.ChatWithDetailsDto
import ru.yofik.athena.common.data.api.http.model.chat.responses.dto.MessageDto
import ru.yofik.athena.common.data.api.mapping.ApiMapper
import ru.yofik.athena.common.data.api.mapping.MappingException
import ru.yofik.athena.common.data.api.http.model.user.mappers.UserApiMapper
import ru.yofik.athena.common.domain.model.chat.ChatWithDetails
import ru.yofik.athena.common.domain.model.chat.details.Details

class ChatWithDetailsApiMapper
@Inject
constructor(private val userDtoMapper: UserApiMapper, private val detailsMapper: DetailsMapper) :
    ApiMapper<ChatWithDetailsDto?, ChatWithDetails> {
    override fun mapToDomain(entityDTO: ChatWithDetailsDto?): ChatWithDetails {
        return ChatWithDetails(
            id = entityDTO?.id ?: throw MappingException("Chat Id cannot be null"),
            name = entityDTO.name.orEmpty(),
            users = entityDTO.users?.map(userDtoMapper::mapToDomain) ?: emptyList(),
            details = detailsMapper.mapToDomain(entityDTO.message)
        )
    }
}

class DetailsMapper @Inject constructor(private val messageDtoMapper: MessageApiMapper) :
    ApiMapper<List<MessageDto>?, Details> {
    override fun mapToDomain(entityDTO: List<MessageDto>?): Details {
        return Details(messages = entityDTO?.map(messageDtoMapper::mapToDomain) ?: emptyList())
    }
}
