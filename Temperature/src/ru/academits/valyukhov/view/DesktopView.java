package ru.academits.valyukhov.view;

import ru.academits.valyukhov.controller.Controller;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;

public class DesktopView implements View {
    private Controller controller;
    private JPanel generalTemperaturePanel;
    private JTextField celsiusTemperatureField;
    private JTextField kelvinTemperatureField;
    private JTextField fahrenheitTemperatureField;
    private boolean isConversionCompleted;

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void start() {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            JFrame frame = new JFrame("Конвертер температур");
            frame.setSize(500, 500);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            JPanel upperPanel = new JPanel();
            upperPanel.setSize(frame.getWidth(), frame.getHeight() / 5);
            upperPanel.setBorder(new BorderUIResource.BevelBorderUIResource(0));

            JLabel tooltipLabel = new JLabel("Введите исходную температуру в ячейке соответствующей шкалы");
            tooltipLabel.setVerticalTextPosition(SwingConstants.CENTER);
            tooltipLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            tooltipLabel.setVisible(true);

            upperPanel.add(tooltipLabel);
            upperPanel.setVisible(true);
            frame.getContentPane().add(upperPanel, BorderLayout.PAGE_START);

            generalTemperaturePanel = new JPanel(new GridLayout(3, 2, 10, 10));
            generalTemperaturePanel.setSize(frame.getWidth(), frame.getHeight() / 5 * 3);
            generalTemperaturePanel.setBorder(new BorderUIResource.BevelBorderUIResource(0));

            JLabel celsiusLabel = new JLabel("Температура по Цельсию (°C)");
            generalTemperaturePanel.add(celsiusLabel);
            celsiusTemperatureField = new JTextField();
            generalTemperaturePanel.add(celsiusTemperatureField);

            JLabel kelvinLabel = new JLabel("Температура по Кельвину (К)");
            generalTemperaturePanel.add(kelvinLabel);
            kelvinTemperatureField = new JTextField();
            generalTemperaturePanel.add(kelvinTemperatureField);

            JLabel fahrenheitLabel = new JLabel("Температура по Фаренгейту (°F)");
            generalTemperaturePanel.add(fahrenheitLabel);
            fahrenheitTemperatureField = new JTextField();
            generalTemperaturePanel.add(fahrenheitTemperatureField);

            frame.add(generalTemperaturePanel, BorderLayout.CENTER);

            JPanel buttonsPanel = new JPanel();
            buttonsPanel.setSize(frame.getWidth(), frame.getHeight() / 5);
            buttonsPanel.setBorder(new BorderUIResource.BevelBorderUIResource(0));

            JButton convertButton = new JButton("Конвертировать");
            JButton resetButton = new JButton("Сброс");

            buttonsPanel.add(convertButton);
            buttonsPanel.add(resetButton);

            frame.getContentPane().add(buttonsPanel, BorderLayout.PAGE_END);

            frame.pack();
            frame.setVisible(true);

            // Отключение невыбранных полей ввода температуры
            celsiusTemperatureField.addCaretListener(s -> {
                celsiusTemperatureField.setEnabled(true);
                kelvinTemperatureField.setEnabled(false);
                fahrenheitTemperatureField.setEnabled(false);
            });

            kelvinTemperatureField.addCaretListener(s -> {
                celsiusTemperatureField.setEnabled(false);
                kelvinTemperatureField.setEnabled(true);
                fahrenheitTemperatureField.setEnabled(false);
            });

            fahrenheitTemperatureField.addCaretListener(s -> {
                celsiusTemperatureField.setEnabled(false);
                kelvinTemperatureField.setEnabled(false);
                fahrenheitTemperatureField.setEnabled(true);
            });

            convertButton.addActionListener(l -> {
                if (isConversionCompleted) {
                    return;
                }

                try {
                    if (celsiusTemperatureField.isEnabled()) {
                        double celsiusTemperature = Double.parseDouble(celsiusTemperatureField.getText());

                        if (celsiusTemperature < -273.15) {
                            JOptionPane.showMessageDialog(frame, "Температура по шкале Цельсия должна быть " +
                                            "не ниже -273.15°C, а Вы ввели " + celsiusTemperature + "°C.", "Ошибка",
                                    JOptionPane.ERROR_MESSAGE);
                            returnToInitialState(tooltipLabel, frame);
                            return;
                        }

                        celsiusTemperatureField.setEnabled(false);
                        setCelsiusTemperature(celsiusTemperature);
                        controller.convertCelsiusToKelvin(celsiusTemperature);
                        controller.convertCelsiusToFahrenheit(celsiusTemperature);
                    } else if (kelvinTemperatureField.isEnabled()) {
                        double kelvinTemperature = Double.parseDouble(kelvinTemperatureField.getText());

                        if (kelvinTemperature < 0) {
                            JOptionPane.showMessageDialog(frame, "Температура по шкале Кельвина должна быть " +
                                            "не ниже 0K, а Вы ввели " + kelvinTemperature + "K.", "Ошибка",
                                    JOptionPane.ERROR_MESSAGE);
                            returnToInitialState(tooltipLabel, frame);
                            return;
                        }

                        kelvinTemperatureField.setEnabled(false);
                        setKelvinTemperature(kelvinTemperature);
                        controller.convertKelvinToCelsius(kelvinTemperature);
                        controller.convertKelvinToFahrenheit(kelvinTemperature);
                    } else if (fahrenheitTemperatureField.isEnabled()) {
                        double fahrenheitTemperature = Double.parseDouble(fahrenheitTemperatureField.getText());

                        if (fahrenheitTemperature < -459.67) {
                            JOptionPane.showMessageDialog(frame, "Температура по шкале Фаренгейта должна быть " +
                                            "не ниже -459.67°F, а Вы ввели " + fahrenheitTemperature + "°F.", "Ошибка",
                                    JOptionPane.ERROR_MESSAGE);
                            returnToInitialState(tooltipLabel, frame);
                            return;
                        }

                        fahrenheitTemperatureField.setEnabled(false);
                        setFahrenheitTemperature(fahrenheitTemperature);
                        controller.convertFahrenheitToCelsius(fahrenheitTemperature);
                        controller.convertFahrenheitToKelvin(fahrenheitTemperature);
                    } else {
                        return;
                    }
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(frame, "Температура должна быть числом", "Ошибка",
                            JOptionPane.ERROR_MESSAGE);
                    returnToInitialState(tooltipLabel, frame);
                    return;
                }

                isConversionCompleted = true;

                tooltipLabel.setText("Конвертация температуры выполнена");

                frame.setVisible(true);    // Для корректного отображения обновлённых элементов
            });

            resetButton.addActionListener(resetButtonPushed -> returnToInitialState(tooltipLabel, frame));
        });
    }

    private void returnToInitialState(JLabel tooltipLabel, JFrame frame) {
        generalTemperaturePanel.remove(1);
        celsiusTemperatureField.setText("");
        generalTemperaturePanel.add(celsiusTemperatureField, 1);

        generalTemperaturePanel.remove(3);
        kelvinTemperatureField.setText("");
        generalTemperaturePanel.add(kelvinTemperatureField, 3);

        generalTemperaturePanel.remove(5);
        fahrenheitTemperatureField.setText("");
        generalTemperaturePanel.add(fahrenheitTemperatureField, 5);

        celsiusTemperatureField.setEnabled(true);
        kelvinTemperatureField.setEnabled(true);
        fahrenheitTemperatureField.setEnabled(true);

        isConversionCompleted = false;

        tooltipLabel.setText("Введите исходную температуру в ячейке соответствующей шкалы");

        frame.setVisible(true);    // Для корректного отображения обновлённых элементов
    }

    @Override
    public void setCelsiusTemperature(double celsiusTemperature) {
        generalTemperaturePanel.remove(celsiusTemperatureField);
        JLabel celsiusTemperatureTextLabel = new JLabel(String.format("%4.2f", celsiusTemperature));
        celsiusTemperatureTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        generalTemperaturePanel.add(celsiusTemperatureTextLabel, 1);
    }

    @Override
    public void setKelvinTemperature(double kelvinTemperature) {
        generalTemperaturePanel.remove(kelvinTemperatureField);
        JLabel kelvinTemperatureTextLabel = new JLabel(String.format("%4.2f", kelvinTemperature));
        kelvinTemperatureTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        generalTemperaturePanel.add(kelvinTemperatureTextLabel, 3);
    }

    @Override
    public void setFahrenheitTemperature(double fahrenheitTemperature) {
        generalTemperaturePanel.remove(fahrenheitTemperatureField);
        JLabel fahrenheitTemperatureTextLabel = new JLabel(String.format("%4.2f", fahrenheitTemperature));
        fahrenheitTemperatureTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        generalTemperaturePanel.add(fahrenheitTemperatureTextLabel, 5);
    }
}