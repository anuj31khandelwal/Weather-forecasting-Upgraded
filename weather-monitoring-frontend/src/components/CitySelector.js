import React from 'react';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "./ui/Select";

const cities = ['Delhi', 'Mumbai', 'Chennai', 'Bangalore', 'Kolkata', 'Hyderabad'];

const CitySelector = ({ selectedCity, onCityChange }) => {
  return (
    <Select onValueChange={onCityChange} defaultValue={selectedCity}>
      <SelectTrigger className="w-[180px] mb-4">
        <SelectValue placeholder="Select a city" />
      </SelectTrigger>
      <SelectContent>
        {cities.map((city) => (
          <SelectItem key={city} value={city}>{city}</SelectItem>
        ))}
      </SelectContent>
    </Select>
  );
};

export default CitySelector;