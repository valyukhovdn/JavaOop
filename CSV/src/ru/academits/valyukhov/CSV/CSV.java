package ru.academits.valyukhov.CSV;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CSV {
    private static void printCharacter(PrintWriter writer, char currentChar) {
        if (currentChar == '<') {
            writer.print("&lt");
        } else if (currentChar == '>') {
            writer.print("&gt");
        } else if (currentChar == '&') {
            writer.print("&amp");
        } else {
            writer.write(currentChar);
        }
    }

    private static void convertCsvToHtml(String inputFile, String outputFile) {
        try (Scanner scanner = new Scanner(new FileInputStream(inputFile), "windows-1251");
             PrintWriter writer = new PrintWriter(outputFile)) {
            if (!scanner.hasNext()) {
                throw new NoSuchElementException("Исходный файл пуст.");
            }

            writer.println("<!DOCTYPE html>");

            writer.println("<html lang=\"ru\">");

            writer.println("  <head>");
            writer.println("    <meta charset=\"UTF-8\"/>");
            writer.println("    <title>HTML-таблица из CSV-файла</title>");
            writer.println("    <style>");
            writer.println("      html {");
            writer.println("        font-family: calibri;");
            writer.println("      }");
            writer.println();
            writer.println("      table {");
            writer.println("        border-collapse: collapse;");
            writer.println("        font-size: 0.9rem;");
            writer.println("      }");
            writer.println();
            writer.println("      td {");
            writer.println("        padding: 3px;");
            writer.println("        vertical-align: top;");
            writer.println("      }");
            writer.println("    </style>");
            writer.println("  </head>");
            writer.println();
            writer.println("  <body>");
            writer.println("    <table border = '1'>");
            writer.println();
            writer.println("      <colgroup>");
            writer.println("        <col style=\"width: 450px\" />");
            writer.println("      </colgroup>");
            writer.println();

            String currentLine;
            int currentCharIndex;
            char currentChar;

            boolean tableEnd = false;

            while (!tableEnd) {
                currentLine = scanner.nextLine();
                currentCharIndex = 0;
                currentChar = currentLine.charAt(currentCharIndex);

                writer.println("      <tr>");

                boolean rowEnd = false;

                while (!rowEnd) {
                    writer.print("        <td>");

                    boolean detailEnd = false;

                    if (currentChar == '"') {   // если в ячейке есть запятая, перевод строки или двойная кавычка
                        while (!detailEnd) {
                            currentChar = currentLine.charAt(++currentCharIndex);

                            if (currentChar == '"') {
                                if (currentCharIndex >= currentLine.length() - 1) {
                                    detailEnd = true;
                                    rowEnd = true;
                                    continue;
                                }

                                currentChar = currentLine.charAt(++currentCharIndex);

                                if (currentChar == '"') {
                                    writer.print('"');
                                    continue;
                                }

                                if (currentChar == ',') {
                                    detailEnd = true;

                                    if (currentCharIndex >= currentLine.length() - 1) {
                                        writer.println("</td>");
                                        writer.print("        <tr>");
                                        rowEnd = true;
                                    } else {
                                        currentChar = currentLine.charAt(++currentCharIndex);
                                    }

                                    continue;
                                }
                            }

                            if (currentCharIndex >= currentLine.length() - 1) {
                                writer.println(currentChar);
                                writer.print("            <br/>");
                                currentLine = scanner.nextLine();
                                currentCharIndex = -1;
                                continue;
                            }

                            printCharacter(writer, currentChar);
                        }
                    } else {   // если в ячейке НЕТ запятой, перевода строки или двойной кавычки
                        while (!detailEnd) {
                            if (currentChar == '"') {
                                throw new NoSuchElementException("Исходный файл не в CSV формате, или содержит ошибку.");
                            }

                            if (currentChar == ',') {
                                if (currentCharIndex >= currentLine.length() - 1) {
                                    writer.println("</td>");
                                    writer.print("        <tr>");
                                    detailEnd = true;
                                    rowEnd = true;
                                } else {
                                    detailEnd = true;
                                    currentChar = currentLine.charAt(++currentCharIndex);
                                }

                                continue;
                            }

                            if (currentCharIndex >= currentLine.length() - 1) {
                                writer.print(currentChar);
                                detailEnd = true;
                                rowEnd = true;
                                continue;
                            }

                            printCharacter(writer, currentChar);

                            currentChar = currentLine.charAt(++currentCharIndex);
                        }
                    }

                    writer.println("</td>");
                }

                if (!scanner.hasNext()) {
                    tableEnd = true;
                }

                writer.println("      </tr>");
            }

            writer.println("    </table>");

            writer.println("  </body>");

            writer.println("<html>");

            System.out.println("HTML-файл скомпилирован.");
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("Произошла ошибка: " + fileNotFoundException.getMessage());
            fileNotFoundException.printStackTrace(System.out);
        }
    }

    public static void main(String[] args) {
        String inputFile = "C:\\Users\\User\\IdeaProjects\\JavaOop\\CSV_input.csv";
        String outputFile = "C:\\Users\\User\\IdeaProjects\\JavaOop\\CSV_output.html";

        convertCsvToHtml(inputFile, outputFile);
    }
}
