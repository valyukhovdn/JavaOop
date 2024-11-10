package ru.academits.valyukhov;

import ru.academits.valyukhov.controller.Controller;
import ru.academits.valyukhov.model.Model;
import ru.academits.valyukhov.model.TemperatureConverterModel;
import ru.academits.valyukhov.view.DesktopView;
import ru.academits.valyukhov.view.View;

public class TemperatureMain {
    public static void main(String[] args) {
        Model model = new TemperatureConverterModel();
        View view = new DesktopView();
        Controller controller = new Controller(model, view);

        view.setController(controller);

        view.start();
    }
}
