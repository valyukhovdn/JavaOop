package ru.academits.valyukhov.Csv;

import java.io.*;
import java.nio.charset.Charset;
import java.util.NoSuchElementException;

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

    private static int convertCsvToHtml(BufferedReader reader, PrintWriter writer) throws IOException {
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
                
                        table {
                            border-collapse: collapse;
                            font-size: 0.9rem;
                        }
                
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

        if (!reader.ready()) {
            writer.println("""
                        </table>
                    </body>
                    </html>
                    """);

            return 0;
        }

        String currentLine = reader.readLine();
        int currentCharIndex = 0;
        char currentChar = currentLine.charAt(currentCharIndex);

        boolean isTableEnd = false;

        while (!isTableEnd) {
            writer.println("        <tr>");

            boolean isRowEnd = false;

            while (!isRowEnd) {
                writer.print("            <td>");

                boolean isDetailEnd = false;

                if (currentChar == '"') {   // если в ячейке есть запятая, перевод строки или двойная кавычка
                    while (!isDetailEnd) {
                        if (currentCharIndex >= currentLine.length() - 1) {
                            writer.println(currentChar);
                            writer.print("                <br />");
                            currentLine = reader.readLine();
                            currentCharIndex = -1;
                            continue;
                        }

                        ++currentCharIndex;
                        currentChar = currentLine.charAt(currentCharIndex);

                        if (currentChar == '"') {
                            if (currentCharIndex >= currentLine.length() - 1) {
                                isDetailEnd = true;
                                isRowEnd = true;
                                continue;
                            }

                            ++currentCharIndex;
                            currentChar = currentLine.charAt(currentCharIndex);

                            if (currentChar == '"') {
                                writer.print('"');
                                continue;
                            }

                            if (currentChar == ',') {
                                isDetailEnd = true;

                                if (currentCharIndex >= currentLine.length() - 1) {
                                    writer.println("</td>");
                                    writer.print("            <td>");
                                    isRowEnd = true;
                                } else {
                                    ++currentCharIndex;
                                    currentChar = currentLine.charAt(currentCharIndex);
                                }

                                continue;
                            }
                        }

                        printCharacter(writer, currentChar);
                    }
                } else {   // если в ячейке НЕТ запятой, перевода строки или двойной кавычки
                    while (!isDetailEnd) {
                        if (currentChar == '"') {
                            throw new NoSuchElementException();
                        }

                        if (currentChar == ',') {
                            isDetailEnd = true;

                            if (currentCharIndex >= currentLine.length() - 1) {
                                writer.println("</td>");
                                writer.print("            <td>");
                                isRowEnd = true;
                            } else {
                                ++currentCharIndex;
                                currentChar = currentLine.charAt(currentCharIndex);
                            }

                            continue;
                        }

                        if (currentCharIndex >= currentLine.length() - 1) {
                            writer.print(currentChar);
                            isDetailEnd = true;
                            isRowEnd = true;
                            continue;
                        }

                        printCharacter(writer, currentChar);

                        ++currentCharIndex;
                        currentChar = currentLine.charAt(currentCharIndex);
                    }
                }

                writer.println("</td>");
            }

            writer.println("        </tr>");

            if (!reader.ready()) {
                isTableEnd = true;
                continue;
            }

            currentLine = reader.readLine();
            currentCharIndex = 0;
            currentChar = currentLine.charAt(currentCharIndex);
        }

        writer.println("""
                    </table>
                </body>
                </html>
                """);

        return 1;
    }

    public static void main(String[] args) {
        // Для тестов пути к файлам:
        // args[0]: C:\Users\User\IdeaProjects\JavaOop\Csv_input.csv
        // args[1]: C:\Users\User\IdeaProjects\\JavaOop\Csv_output.html

        try (BufferedReader reader = new BufferedReader(new FileReader(args[0], Charset.forName("windows-1251")));
             PrintWriter writer = new PrintWriter(args[1])) {
            if (convertCsvToHtml(reader, writer) == 0) {
                System.out.println("Исходный файл пуст. Сформирована пустая таблица.");
            } else {
                System.out.println("HTML-файл создан.");
            }
        } catch (IOException e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
        } catch (NoSuchElementException e) {
            try (PrintWriter writer = new PrintWriter(args[1])) {    // Создание пустого (без html-кода) итогового html-файла.
                System.out.println("Исходный файл не в CSV формате, или содержит ошибку.");
            } catch (IOException ioException) {
                System.out.println("При попытке создания пустого html-файла произошла ошибка: " + ioException.getMessage());
            }
        }
    }
}
