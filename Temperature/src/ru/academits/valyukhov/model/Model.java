package ru.academits.valyukhov.model;

public interface Model {
    double convertCelsiusToKelvin(double celsiusTemperature);

    double convertCelsiusToFahrenheit(double celsiusTemperature);

    double convertKelvinToCelsius(double kelvinTemperature);

    double convertKelvinToFahrenheit(double kelvinTemperature);

    double convertFahrenheitToCelsius(double fahrenheitTemperature);

    double convertFahrenheitToKelvin(double fahrenheitTemperature);
}
