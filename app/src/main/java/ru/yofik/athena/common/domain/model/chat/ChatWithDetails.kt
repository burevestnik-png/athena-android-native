package ru.yofik.athena.common.domain.model.chat

/** Basic entity object which has more details than base entity Chat */
data class ChatWithDetails(val id: Int, val name: String, val userIds: List<String>)
