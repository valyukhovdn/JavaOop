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

    private static boolean isConvertedCsvToHtml(BufferedReader reader, PrintWriter writer) throws IOException {
        writer.println("""
                 <!DOCTYPE html>
                 <html lang="ru">
                                \s
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
                                \s
                 <body>
                     <table border="1">
                         <colgroup>
                             <col style="width: 450px" />
                         </colgroup>
                \s""");

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

        char currentChar;

        boolean isLineFeedPossibleInDetail = false;

        while (currentLine != null) {
            if (!isLineFeedPossibleInDetail) {
                writer.println("        <tr>");
                writer.print("            <td>");
            }

            for (int currentCharIndex = 0; currentCharIndex <= currentLine.length() - 1; ++currentCharIndex) {
                currentChar = currentLine.charAt(currentCharIndex);

                switch (currentChar) {
                    case '"':
                        if (currentCharIndex >= currentLine.length() - 1) {
                            writer.println("</td>");
                            writer.println("        </tr>");
                            isLineFeedPossibleInDetail = false;
                            break;
                        }

                        isLineFeedPossibleInDetail = true;

                        ++currentCharIndex;
                        currentChar = currentLine.charAt(currentCharIndex);

                        switch (currentChar) {
                            case '"':
                                ++currentCharIndex;
                                currentChar = currentLine.charAt(currentCharIndex);

                                switch (currentChar) {
                                    case '"':
                                        writer.print("\"");

                                        if (currentCharIndex >= currentLine.length() - 1) {
                                            writer.print("</td>");
                                            writer.println("        </tr>");
                                        }

                                        break;
                                    case ',':
                                        writer.print("\",");
                                        break;
                                    default:
                                        writer.print('"');
                                        printCharacter(writer, currentChar);
                                }

                                break;
                            case ',':
                                writer.println("</td>");

                                isLineFeedPossibleInDetail = false;

                                if (currentCharIndex >= currentLine.length() - 1) {
                                    writer.println("            <td></td>");
                                    writer.println("        </tr>");
                                } else {
                                    writer.print("            <td>");
                                }

                                break;
                            default:
                                printCharacter(writer, currentChar);
                        }

                        break;
                    case ',':
                        writer.println("</td>");

                        if (currentCharIndex >= currentLine.length() - 1) {
                            writer.println("            <td></td>");
                            writer.println("        </tr>");
                        } else {
                            writer.print("            <td>");
                        }

                        break;
                    default:
                        printCharacter(writer, currentChar);

                        if (currentCharIndex >= currentLine.length() - 1) {
                            if (isLineFeedPossibleInDetail) {
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
            throw new IllegalArgumentException(String.format(System.lineSeparator() + "В программу должны быть переданы " +
                    "\"2\" аргумента (пути к исходному CSV-файлу и итоговому HTML-файлу), а Вы передали \"%d\".", args.length));
        }

        String inputFilePath = args[0];    // Для тестов: String inputFilePath = "C:\\Users\\User\\IdeaProjects\\JavaOop\\Csv_input.csv";
        String outputFilePath = args[1];   // Для тестов: String outputFilePath = "C:\\Users\\User\\IdeaProjects\\JavaOop\\Csv_output.html";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath, Charset.forName("windows-1251")));
             PrintWriter writer = new PrintWriter(outputFilePath)) {
            if (isConvertedCsvToHtml(reader, writer)) {
                System.out.println("HTML-файл создан.");
            } else {
                System.out.println("Исходный файл пуст. Сформирована пустая таблица.");
            }
        } catch (IOException e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
        }
    }
}
