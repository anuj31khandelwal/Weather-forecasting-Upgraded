import React, { useState, useEffect } from 'react';
import { fetchCurrentWeather, fetchDailySummaries, fetchAlerts } from '../services/api';
import CitySelector from './CitySelector';
import CurrentWeather from './CurrentWeather';
import TemperatureSummary from './TemperatureSummary';
import WeatherAlerts from './WeatherAlerts';

const WeatherDashboard = () => {
  const [selectedCity, setSelectedCity] = useState('Delhi');
  const [currentWeather, setCurrentWeather] = useState(null);
  const [dailySummaries, setDailySummaries] = useState([]);
  const [alerts, setAlerts] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [weatherResponse, summariesResponse, alertsResponse] = await Promise.all([
          fetchCurrentWeather(selectedCity),
          fetchDailySummaries(selectedCity),
          fetchAlerts(selectedCity)
        ]);

        setCurrentWeather(weatherResponse.data);
        setDailySummaries(summariesResponse.data);
        setAlerts(alertsResponse.data);
      } catch (error) {
        console.error('Error fetching weather data:', error);
      }
    };

    fetchData();
    const interval = setInterval(fetchData, 300000); // Refresh every 5 minutes

    return () => clearInterval(interval);
  }, [selectedCity]);

  return (
    <div className="p-4">
      <h1 className="text-2xl font-bold mb-4">Weather Monitoring Dashboard</h1>
      <CitySelector selectedCity={selectedCity} onCityChange={setSelectedCity} />
      <CurrentWeather data={currentWeather} />
      <TemperatureSummary data={dailySummaries} />
      <WeatherAlerts alerts={alerts} />
    </div>
  );
};

export default WeatherDashboard;