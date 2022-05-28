# Athena android native app

## Что это и зачем разрабатывается

Все это приложение - часть большого проекта, который делается в рамках изучения экосистемы Android.
Данный мессенджер подразумевает хорошую защиту пользовательских данных, и во многом похож на все
популярные аналоги. Но так как цель этого проекта - саморазвитие, то по итогу разработка выливается
в некоторого рода челендж :]

В этом проекте я, при помощи моего однокурсника бэкендера [Алексея](https://github.com/Roggired),
опробоваю различные возможности Android SDK, различные библиотеки и архитектурные решения.

На данный момент я успел поработать с:

- Мультимодульная архитектура
- Смесь MVVM и MVI на уровне представления
- Одна Activity и много Fragments, связь между ними при помощи Navigation API
- Для работы с HTTP и Websocket API - OkHttp, Retrofit и Moshi
- Для кэширования данных - Room
- RxJava и Coroutines API
- Hilt в качестве DI фреймворка


## Реализация требований

### Пререквизиты

Как я говорил, приложение завязано на бэкэнде моего товарища, сложного в настройке из-за различной
генерации ключей, поднятия докер образов и самих микросервисов, поэтому я думаю что можно
предоставить набор gif для показа функционала.

### Лента -> детализация

Пример такого решения есть в виде "Чаты -> чат"

![chat detailed](./gifs/chats-chat.gif)

### Использование открытого API

К сожалению никакое открытое API не поможет с написанием своего классного мессенджера, поэтому оно
не используется :[

### Тесты

Тесты - будут :]

Опыт написания юнит тестов есть, но нужно разобраться с тестированием тех же самых репозиториев и
вебсокет слушателей, а так же и с тестированием представления.

### Оффлайн режим

Тема, которая реализована на данный момент частично, и только в `createChat` feature. Там просиходит
тривиальное кэширование пользователей системы. Стратегия тоже простая - если кэш пустой, то
подгружаем, если нет то достаем данные из него. Если нужно форсировать запрос через сеть, то можно
потянуть список вниз и вызвать запрос через сеть и обновить кэш.

![create chat](./gifs/create-chat.gif)

Модель приложения не тривиальна и чтобы реализовать оставшийся кэш нужно порисовать UML диаграммы и
посмотреть как именно хранить чаты и сообщения, какие агрегированные сущности придумать и как
настроить между всем этим делом связи.

## Что будет дальше?

- Релиз Android нативной версии
- Разработка и релиз IOS нативной версии
- Разработка кроссплатформы на Flutter (благо опыт есть)
- Может быть разработка на React Native

### P.S

Цель проекта - мне, как разработчику, попробовать весь современный стек разработки и определиться
с приоритетами в саморазвитии

## Version notes

### v0.2.1 - In progress

#### Features

- TODO в чат листе, протестить
- вынести пагинацию
- рефактор логина и профиля

#### Plans

- [ ] Rework AppBar with Navigation framework
- [ ] Think about settings
- [ ] Coroutines :]
- [ ] TextInputLayout rewrite on
- [ ] Add validation on Login screen like on Onboarding
- [ ] Think about creating several chats
- [ ] Very bad realization of rx java, need to study more
- [ ] Add a list of my name convention in fragment and activity
- [ ] add chat to chat list module
- [ ] added response error parsing
- Вопрос, где лучше инитить - в фрагменте или в вм

---

### v0.2.0

#### Features

* Added force refresh for users and messages
* Added simple messaging via ws
* Reworked settings page to profile page
* Redesigned login page

#### Plans

* Rework AppBar with Navigation framework
* Think about settings
* Coroutines :]
* TextInputLayout rewrite on
* Add validation on Login screen like on Onboarding
* Think about creating several chats
* Very bad realization of rx java, need to study more
* Add a list of my name convention in fragment and activity
* add chat to chat list

---

### v0.1.4

#### Features

* Now we can create chats with any user which we have in DB
* Auto logining if you have already logined
* Logout button in settings, which will be moved in future

#### Plans

* Rework AppBar with Navigation framework
* Think about settings
* Coroutines :]
* TextInputLayout rewrite on
* Add validation on Login screen like on Onboarding
* Add functionality to buttons in actionbar
* Think about creating several chats

---

### v0.1.3

#### Features

* Added bottom navigation
* Remade activities and fragments structures (check draw.io)

#### Plans

* Rework Login UI
* Add functionality to buttons in actionbar
* Think about settings

---

### v0.1.2

#### Features

* Added Login screen
* Moved to modern API (LocalDateTime instead of Date)

#### Plans

* Add Settings screen
* Add functionality to buttons in actionbar

---

### v0.1.1

#### Features

* UI & UX

    * Troubles with running lib with LocalDateTime, returned to Date <br>
      So dates in app are very bad right now :]
* Codebase
    * Added base for networking, need Alex to implement in fully

#### Plans

* Adding login screen
* Making dates look better

---

### v0.1.0

#### Features

* Added basic logic of opening chat and writing messages
* Refactoring domain due to Best Practices (BP)

#### Plans

* Adding network infrastructure
* Adding login screen