# Weather-forecasting-Upgraded

Weather Monitoring App
This project consists of two main modules:
1. Weather Monitoring Web App (weather-monitoring-app): A React-based frontend for monitoring weather conditions, showing current
   weather, temperature summaries, and weather alerts.
2. Weather Monitoring Backend System (`weather-monitoring-system`): A Spring Boot-based backend that fetches weather data from
   external APIs, processes it, and serves it to the frontend.
   
```   
weather-monitoring-app/
   ├── public/
   │ ├── index.html 
   │ └── favicon.icon
   ├── src/
   │ ├── components/ 
   │ │ ├── WeatherDashboard.js 
   │ │ ├── CurrentWeather.js 
   │ │ ├── TemperatureSummary.js 
   │ │ ├── WeatherAlerts.js
   │ │ └── CitySelector.js
   │ ├── services/
   │ │ └── api.js
   │ ├── utils/
   │ │ └── dateUtils.js
   │ ├── App.js 
   │ └── index.js 
   ├── .env 
   ├── package.json
   └── README.md 
```
```
   weather-monitoring-system/
   ├── src/
   │ ├── main/
   │ │ ├── java/
   │ │ │ └── com/
   │ │ │ └── example/
   │ │ │ └── weathermonitoring/
   │ │ │ ├── WeatherMonitoringApplication.java 
   │ │ │ ├── config/
   │ │ │ │ └── OpenWeatherMapConfig.java 
   │ │ │ ├── controller/
   │ │ │ │ └── WeatherController.java 
   │ │ │ ├── model/
   │ │ │ │ ├── WeatherData.java
   │ │ │ │ └── DailySummary.java
   │ │ │ ├── repository/
   │ │ │ │ ├── WeatherDataRepository.java
   │ │ │ │ └── DailySummaryRepository.java
   │ │ │ ├── service/
   │ │ │ │ ├── WeatherApiService.java 
   │ │ │ │ ├── DataProcessingService.java
   │ │ │ │ ├── AlertService.java
   │ │ │ │ └── PersistenceService.java
   │ │ │ └── scheduler/
   │ │ │ └── WeatherDataScheduler.java
   │ └── resources/
   │ └── application.properties
   │ └── test/
   │ └── java/
   │ └── com/
   │ └── example/
   │ └── weathermonitoring/
   │ └── WeatherMonitoringApplicationTests.java
   ├── pom.xml
   └── README.md
```
***Prerequisites***

***Frontend ( weather-monitoring-app )***
* React
* OpenWeatherMap API key (store in .env file)

***Backend ( weather-monitoring-system)***
* Java 11 or higher
* Maven
* OpenWeatherMap API key (store in application.properties file)

***Setup and Running the Application***
***Frontend (React)***
1. Navigate to the weather-monitoring-app directory:

    ```cd weather-monitoring-app```
2. Install the dependencies:
   
    ```npm install```
3. Set up your .env file with the following content:
    ``` REACT_APP_WEATHER_API_KEY=your_openweathermap_api_key```
4. Start the development server:

    ```npm start```


   The app will run on http://localhost:3000.


   ***Backend (Spring Boot)***
1. Navigate to the weather-monitoring-system directory:
    ```cd weather-monitoring-system```
2. Configure the application.properties file located in src/main/resources/ :
    ``` openweathermap.api.key=your_openweathermap_api_key```
3. Build the project using Maven:

    ```mvn clean install```
4. Run the Spring Boot application:
   
   ```mvn spring-boot:run```

   The backend will run on http://localhost:8080.


   ***API Endpoints***

   ***Weather Monitoring System (Backend)***
   * GET /api/weather/current?city={cityName} : Fetches current weather data for a specific city.
   * GET /api/weather/summary?city={cityName} : Retrieves daily weather summary for a city.
   * GET /api/weather/alerts?city={cityName} : Retrieves weather alerts for the specified city.
   

   ***Key Features***

   ***Frontend:***
*    Displays current weather conditions, temperature, and alerts.
*    Allows users to search and select different cities for weather data.
   
***Backend:***

*    Fetches weather data from OpenWeatherMap API.
*    Processes and stores weather data and alerts.
*    Schedules periodic updates of weather data.
   