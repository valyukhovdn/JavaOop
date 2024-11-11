package ru.academits.valyukhov.view;

import ru.academits.valyukhov.controller.Controller;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;

public class DesktopView implements View {
    private Controller controller;
    private JPanel generalTemperaturePanel;
    private JLabel celsiusLabel;
    private JLabel kelvinLabel;
    private JLabel fahrenheitLabel;
    private JTextField celsiusTemperatureField;
    private JTextField kelvinTemperatureField;
    private JTextField fahrenheitTemperatureField;
    private JLabel celsiusTemperatureTextLabel;
    private JLabel kelvinTemperatureTextLabel;
    private JLabel fahrenheitTemperatureTextLabel;
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

            celsiusLabel = new JLabel("Температура по Цельсию (°C)");
            generalTemperaturePanel.add(celsiusLabel);
            celsiusTemperatureField = new JTextField();
            generalTemperaturePanel.add(celsiusTemperatureField);

            kelvinLabel = new JLabel("Температура по Кельвину (К)");
            generalTemperaturePanel.add(kelvinLabel);
            kelvinTemperatureField = new JTextField();
            generalTemperaturePanel.add(kelvinTemperatureField);

            fahrenheitLabel = new JLabel("Температура по Фаренгейту (°F)");
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

            // Сокрытие невыбранных полей ввода температуры
            celsiusTemperatureField.addCaretListener(s -> {
                kelvinTemperatureField.setVisible(false);
                fahrenheitTemperatureField.setVisible(false);
            });

            kelvinTemperatureField.addCaretListener(s -> {
                celsiusTemperatureField.setVisible(false);
                fahrenheitTemperatureField.setVisible(false);
            });

            fahrenheitTemperatureField.addCaretListener(s -> {
                celsiusTemperatureField.setVisible(false);
                kelvinTemperatureField.setVisible(false);
            });

            convertButton.addActionListener(l -> {
                if (isConversionCompleted) {
                    return;
                }

                if (celsiusTemperatureField.isVisible() && kelvinTemperatureField.isVisible()
                        && fahrenheitTemperatureField.isVisible()) {
                    JOptionPane.showMessageDialog(frame, "Выберите температурную шкалу.", "Ошибка",
                            JOptionPane.ERROR_MESSAGE);
                    returnToInitialState(tooltipLabel, frame);
                    return;
                }

                try {
                    if (celsiusTemperatureField.isVisible()) {
                        double celsiusTemperature = Double.parseDouble(celsiusTemperatureField.getText());

                        if (celsiusTemperature < -273.15) {
                            JOptionPane.showMessageDialog(frame, "Температура по шкале Цельсия должна быть " +
                                            "не ниже -273.15°C, а Вы ввели " + celsiusTemperature + "°C.", "Ошибка",
                                    JOptionPane.ERROR_MESSAGE);
                            returnToInitialState(tooltipLabel, frame);
                            return;
                        }

                        celsiusTemperatureField.setText("");
                        setCelsiusTemperature(celsiusTemperature);
                        controller.convertCelsiusToKelvin(celsiusTemperature);
                        controller.convertCelsiusToFahrenheit(celsiusTemperature);
                    } else if (kelvinTemperatureField.isVisible()) {
                        double kelvinTemperature = Double.parseDouble(kelvinTemperatureField.getText());

                        if (kelvinTemperature < 0) {
                            JOptionPane.showMessageDialog(frame, "Температура по шкале Кельвина должна быть " +
                                            "не ниже 0K, а Вы ввели " + kelvinTemperature + "K.", "Ошибка",
                                    JOptionPane.ERROR_MESSAGE);
                            returnToInitialState(tooltipLabel, frame);
                            return;
                        }

                        kelvinTemperatureField.setText("");
                        setKelvinTemperature(kelvinTemperature);
                        controller.convertKelvinToCelsius(kelvinTemperature);
                        controller.convertKelvinToFahrenheit(kelvinTemperature);
                    } else if (fahrenheitTemperatureField.isVisible()) {
                        double fahrenheitTemperature = Double.parseDouble(fahrenheitTemperatureField.getText());

                        if (fahrenheitTemperature < -459.67) {
                            JOptionPane.showMessageDialog(frame, "Температура по шкале Фаренгейта должна быть " +
                                            "не ниже -459.67°F, а Вы ввели " + fahrenheitTemperature + "°F.", "Ошибка",
                                    JOptionPane.ERROR_MESSAGE);
                            returnToInitialState(tooltipLabel, frame);
                            return;
                        }

                        fahrenheitTemperatureField.setText("");
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
        if (isConversionCompleted) {
            celsiusTemperatureTextLabel.setVisible(false);
            kelvinTemperatureTextLabel.setVisible(false);
            fahrenheitTemperatureTextLabel.setVisible(false);
        }

        generalTemperaturePanel.removeAll();

        generalTemperaturePanel.add(celsiusLabel);
        celsiusTemperatureField.setText("");
        generalTemperaturePanel.add(celsiusTemperatureField);

        generalTemperaturePanel.add(kelvinLabel);
        kelvinTemperatureField.setText("");
        generalTemperaturePanel.add(kelvinTemperatureField);

        generalTemperaturePanel.add(fahrenheitLabel);
        fahrenheitTemperatureField.setText("");
        generalTemperaturePanel.add(fahrenheitTemperatureField);

        celsiusTemperatureField.setVisible(true);
        kelvinTemperatureField.setVisible(true);
        fahrenheitTemperatureField.setVisible(true);

        isConversionCompleted = false;

        tooltipLabel.setText("Введите исходную температуру в ячейке соответствующей шкалы");

        generalTemperaturePanel.setVisible(true);
        frame.setVisible(true);    // Для корректного отображения обновлённых элементов
    }

    @Override
    public void setCelsiusTemperature(double celsiusTemperature) {
        generalTemperaturePanel.remove(celsiusTemperatureField);
        celsiusTemperatureTextLabel = new JLabel(String.format("%4.2f", celsiusTemperature));
        celsiusTemperatureTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        generalTemperaturePanel.add(celsiusTemperatureTextLabel, 1);
    }

    @Override
    public void setKelvinTemperature(double kelvinTemperature) {
        generalTemperaturePanel.remove(kelvinTemperatureField);
        kelvinTemperatureTextLabel = new JLabel(String.format("%4.2f", kelvinTemperature));
        kelvinTemperatureTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        generalTemperaturePanel.add(kelvinTemperatureTextLabel, 3);
    }

    @Override
    public void setFahrenheitTemperature(double fahrenheitTemperature) {
        generalTemperaturePanel.remove(fahrenheitTemperatureField);
        fahrenheitTemperatureTextLabel = new JLabel(String.format("%4.2f", fahrenheitTemperature));
        fahrenheitTemperatureTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        generalTemperaturePanel.add(fahrenheitTemperatureTextLabel, 5);
    }
}