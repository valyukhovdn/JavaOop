package ru.academits.valyukhov.controller;

import ru.academits.valyukhov.model.Model;
import ru.academits.valyukhov.view.View;

public class Controller {
    private final Model model;
    private final ru.academits.valyukhov.view.View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void convertCelsiusToKelvin(double celsiusTemperature) {
        double kelvinTemperature = model.convertCelsiusToKelvin(celsiusTemperature);
        view.setKelvinTemperature(kelvinTemperature);
    }

    public void convertCelsiusToFahrenheit(double celsiusTemperature) {
        double fahrenheitTemperature = model.convertCelsiusToFahrenheit(celsiusTemperature);
        view.setFahrenheitTemperature(fahrenheitTemperature);
    }

    public void convertKelvinToCelsius(double kelvinTemperature) {
        double celsiusTemperature = model.convertKelvinToCelsius(kelvinTemperature);
        view.setCelsiusTemperature(celsiusTemperature);
    }

    public void convertKelvinToFahrenheit(double kelvinTemperature) {
        double fahrenheitTemperature = model.convertKelvinToFahrenheit(kelvinTemperature);
        view.setFahrenheitTemperature(fahrenheitTemperature);
    }

    public void convertFahrenheitToCelsius(double fahrenheitTemperature) {
        double celsiusTemperature = model.convertFahrenheitToCelsius(fahrenheitTemperature);
        view.setCelsiusTemperature(celsiusTemperature);
    }

    public void convertFahrenheitToKelvin(double fahrenheitTemperature) {
        double kelvinTemperature = model.convertFahrenheitToKelvin(fahrenheitTemperature);
        view.setKelvinTemperature(kelvinTemperature);
    }
}