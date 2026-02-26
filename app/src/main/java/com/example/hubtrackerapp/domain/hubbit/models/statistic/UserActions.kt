package com.example.hubtrackerapp.domain.hubbit.models.statistic

// Сущность для логирования всех действий пользователя,для последующего наглядного вывода
// а так же для использовании в подведении статистики
data class UserActions(
    val userActionId: Long = 0,
    val userId: String, // чьё действие
    val actionType: ActionType, // тип действия пользователя

    val entityId: String? = null, // айди сущности с которой пользователь взаимоедйствовал
    val entityName: String? = null, // название на момент записи
    val pointEarned: Int = 0, //присужденные очки
    val timeStamp : Long = System.currentTimeMillis(), // когда сделал

    val additionalData: String? = null // Json для доп данных

)