package ru.yofik.athena.main.presentation

sealed class MainActivityViewEffect {
    data class SetStartDestination(val destination: Int) : MainActivityViewEffect()
}
