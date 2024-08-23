package ru.academits.valyukhov.Csv;

import java.io.*;
import java.nio.charset.Charset;

public class Csv {
    private static void printCharacter(PrintWriter writer, char currentChar) {
        if (currentChar == '<') {
            writer.print("&lt;");
        } else if (currentChar == '>') {
            writer.print("&gt;");
        } else if (currentChar == '&') {
            writer.print("&amp;");
        } else {
            writer.write(currentChar);
        }
    }

    private static boolean convertCsvToHtml(BufferedReader reader, PrintWriter writer) throws IOException {
        writer.println("""
                <!DOCTYPE html>
                <html lang="ru">
                <head>
                    <meta charset="UTF-8"/>
                    <title>HTML-таблица из CSV-файла</title>
                    <style>
                        html {
                            font-family: calibri;
                        }
                               \s
                        table {
                            border-collapse: collapse;
                            font-size: 0.9rem;
                        }
                               \s
                        td {
                            padding: 3px;
                            vertical-align: top;
                        }
                    </style>
                </head>
                <body>
                    <table border="1">
                        <colgroup>
                            <col style="width: 450px" />
                        </colgroup>
                """);

        String tableEnd = """
                    </table>
                </body>
                </html>
                """;

        String currentLine = reader.readLine();

        if (currentLine == null) {
            writer.println(tableEnd);

            return false;
        }

        boolean hasSpecialSymbols = false;

        while (currentLine != null) {
            if (!hasSpecialSymbols) {
                writer.println("        <tr>");
                writer.print("            <td>");
            } else if (currentLine.isEmpty()) {
                writer.println();
                writer.print("                <br />");
                currentLine = reader.readLine();
                continue;
            }

            boolean isEscapingQuote = false;

            for (int i = 0; i < currentLine.length(); ++i) {
                char currentChar = currentLine.charAt(i);

                switch (currentChar) {
                    case '"':
                        if (i >= currentLine.length() - 1) {
                            if (!hasSpecialSymbols) {
                                writer.println();
                                writer.print("                <br />");
                                hasSpecialSymbols = true;
                                break;
                            }

                            writer.println("</td>");
                            writer.println("        </tr>");
                            hasSpecialSymbols = false;
                            break;
                        }

                        if (!hasSpecialSymbols) {
                            hasSpecialSymbols = true;
                            break;
                        }

                        if (!isEscapingQuote) {
                            isEscapingQuote = true;
                            break;
                        }

                        writer.print("\"");
                        isEscapingQuote = false;
                        break;
                    case ',':
                        if (hasSpecialSymbols) {
                            if (!isEscapingQuote) {
                                writer.print(',');
                                break;
                            }

                            isEscapingQuote = false;
                            hasSpecialSymbols = false;
                        }

                        if (i >= currentLine.length() - 1) {
                            writer.println("</td>");
                            writer.println("            <td></td>");
                            writer.println("        </tr>");
                        } else {
                            writer.println("</td>");
                            writer.print("            <td>");
                        }

                        break;
                    default:
                        printCharacter(writer, currentChar);

                        if (i >= currentLine.length() - 1) {
                            if (hasSpecialSymbols) {
                                writer.println();
                                writer.print("                <br />");
                            } else {
                                writer.println("</td>");
                                writer.println("        </tr>");
                            }
                        }
                }
            }

            currentLine = reader.readLine();
        }

        writer.println(tableEnd);

        return true;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.printf("В программу должны быть переданы \"2\" аргумента (пути к исходному CSV-файлу и " +
                    "итоговому HTML-файлу), а Вы передали \"%d\".%n", args.length);
            return;
        }

        String inputFilePath = args[0];     // Для тестов: "C:\\Users\\User\\IdeaProjects\\JavaOop\\Csv_input(2).csv";
        String outputFilePath = args[1];    // Для тестов: "C:\\Users\\User\\IdeaProjects\\JavaOop\\Csv_output.html";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath, Charset.forName("windows-1251")));
             PrintWriter writer = new PrintWriter(outputFilePath)) {
            if (convertCsvToHtml(reader, writer)) {
                System.out.println("HTML-файл создан.");
            } else {
                System.out.println("Исходный файл пуст. Сформирована пустая таблица.");
            }
        } catch (IOException e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
        }
    }
}
