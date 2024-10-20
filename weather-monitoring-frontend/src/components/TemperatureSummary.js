import React from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import { Card, CardContent, CardHeader, CardTitle } from './ui/Card';
import { formatDate } from '../utils/dateUtils';

const TemperatureSummary = ({ data }) => {
  if (!data || data.length === 0) return null;

  const chartData = data.map(item => ({
    ...item,
    date: formatDate(item.date),
  }));

  return (
    <Card className="mb-4">
      <CardHeader>
        <CardTitle>7-Day Temperature Summary</CardTitle>
      </CardHeader>
      <CardContent>
        <ResponsiveContainer width="100%" height={300}>
          <LineChart data={chartData}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="date" />
            <YAxis />
            <Tooltip />
            <Legend />
            <Line type="monotone" dataKey="averageTemperature" stroke="#8884d8" name="Avg Temp" />
            <Line type="monotone" dataKey="maxTemperature" stroke="#82ca9d" name="Max Temp" />
            <Line type="monotone" dataKey="minTemperature" stroke="#ffc658" name="Min Temp" />
          </LineChart>
        </ResponsiveContainer>
      </CardContent>
    </Card>
  );
};

export default TemperatureSummary;