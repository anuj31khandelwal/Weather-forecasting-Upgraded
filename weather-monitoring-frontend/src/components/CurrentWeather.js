import React from 'react';
import { Card, CardContent, CardHeader, CardTitle } from './ui/Card';
import { formatDateTime } from '../utils/dateUtils';

const CurrentWeather = ({ data }) => {
  if (!data) return null;

  return (
    <Card className="mb-4">
      <CardHeader>
        <CardTitle>Current Weather in {data.city}</CardTitle>
      </CardHeader>
      <CardContent>
        <p>Temperature: {data.temp.toFixed(1)}°C</p>
        <p>Feels Like: {data.feels_like.toFixed(1)}°C</p>
        <p>Condition: {data.main}</p>
        <p>Last Updated: {formatDateTime(data.dt)}</p>
      </CardContent>
    </Card>
  );
};

export default CurrentWeather;