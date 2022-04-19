package ru.yofik.athena.main.presentation

// TODO Navigation res not working
sealed class MainActivityViewEffect {
    data class SetStartDestination(val destination: Int) : MainActivityViewEffect()
}
