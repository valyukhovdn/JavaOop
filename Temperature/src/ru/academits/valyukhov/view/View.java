package ru.academits.valyukhov.view;

import ru.academits.valyukhov.controller.Controller;

public interface View {
    void setController(Controller controller);

    void start();

    void setKelvinTemperature(double kelvinTemperature);

    void setFahrenheitTemperature(double fahrenheitTemperature);

    void setCelsiusTemperature(double celsiusTemperature);
}