# open-meteo
##### Current State of the app
The app currently, loops the 10 given locations and fetches the forecast for that location.
The Displayed forecast info are the following:
1. Current Weather real feel, wind, code description (if available) and time.
2. List of 7 days including today, displaying, min, max temperatures, date and code description (if available)
##### Technicanl description
1. The app is structured using MVVM + Clean architecture.
2. Used Koin for Dependency Injection
3. For network layer Ktor is used + Kotlinx serialization.
4. Kotlin Corutines for reactive streams
5. Added Junit test as examples, for `LatestWeatherViewModel`, `GetLatestWeatherUseCase` and `ListLocationProviderTest`


#### Improvements todo
1. When tapping on a day item from the list can display a detail view of this day.
2. displaying city name from coordinates.
3. use a widget or a notification and use a background worker (interval would be 15 minutes) and looping locations to update the widget/notification current weather condition.
3. Using library modules (for example a module for network, module for location providing, module for latest weather, etc..)
4. Add more tests for example mappers and the repository.
