package ru.academits.valyukhov.model;

public class TemperatureConverterModel implements Model {
    @Override
    public double convertCelsiusToKelvin(double celsiusTemperature) {
        return celsiusTemperature + 273.15;
    }

    @Override
    public double convertCelsiusToFahrenheit(double celsiusTemperature) {
        return celsiusTemperature * 1.8 + 32;
    }

    @Override
    public double convertKelvinToCelsius(double kelvinTemperature) {
        return kelvinTemperature - 273.15;
    }

    @Override
    public double convertKelvinToFahrenheit(double kelvinTemperature) {
        return (kelvinTemperature - 273.15) * 1.8 + 32;
    }

    @Override
    public double convertFahrenheitToCelsius(double fahrenheitTemperature) {
        return (fahrenheitTemperature - 32) / 1.8;
    }

    @Override
    public double convertFahrenheitToKelvin(double fahrenheitTemperature) {
        return (fahrenheitTemperature - 32) / 1.8 + 273.15;
    }
}