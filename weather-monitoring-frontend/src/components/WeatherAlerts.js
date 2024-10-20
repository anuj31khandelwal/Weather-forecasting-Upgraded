import React from 'react';
import { Card, CardContent, CardHeader, CardTitle } from './ui/Card';
import { Alert, AlertDescription, AlertTitle } from './ui/Alert';

const WeatherAlerts = ({ alerts }) => {
  if (!alerts || alerts.length === 0) return null;

  return (
    <Card>
      <CardHeader>
        <CardTitle>Weather Alerts</CardTitle>
      </CardHeader>
      <CardContent>
        {alerts.map((alert, index) => (
          <Alert key={index} variant="destructive" className="mb-2">
            <AlertTitle>{alert.condition}</AlertTitle>
            <AlertDescription>{alert.message}</AlertDescription>
          </Alert>
        ))}
      </CardContent>
    </Card>
  );
};

export default WeatherAlerts;