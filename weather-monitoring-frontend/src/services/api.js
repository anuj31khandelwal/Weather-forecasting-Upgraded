import axios from 'axios';

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL || 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
});

export const fetchCurrentWeather = (city) => api.get(`/weather/current/${city}`);
export const fetchDailySummaries = (city) => api.get(`/weather/summary/${city}`);
export const fetchAlerts = (city) => api.get(`/weather/alerts/${city}`);