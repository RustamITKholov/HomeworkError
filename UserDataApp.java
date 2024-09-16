package Homework;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class UserDataApp {

    public static void main(String[] args) {
        // Создаем Scanner с указанием кодировки UTF-8
        Scanner scanner = new Scanner(System.in, "UTF-8");
        System.out.println("Введите данные (Фамилия Имя Отчество датарождения номертелефона пол):");

        // Считываем введенную строку
        String input = scanner.nextLine();
        String[] data = input.split(" ");

        try {
            // Проверка количества введенных данных
            if (data.length != 6) {
                throw new Exception("Неверное количество данных. Введите все 6 параметров.");
            }

            // Парсинг данных
            String lastName = data[0]; // Фамилия
            String firstName = data[1]; // Имя
            String middleName = data[2]; // Отчество
            String birthDate = data[3]; // Дата рождения
            String phoneNumber = data[4]; // Номер телефона
            String gender = data[5]; // Пол

            // Проверка формата даты рождения
            if (!isValidDate(birthDate)) {
                throw new ParseException("Неверный формат даты рождения. Ожидается формат dd.mm.yyyy.", 0);
            }

            // Проверка формата номера телефона
            if (!isNumeric(phoneNumber)) {
                throw new NumberFormatException("Номер телефона должен быть целым числом.");
            }

            // Проверка пола
            if (!gender.equals("f") && !gender.equals("m")) {
                throw new IllegalArgumentException("Пол должен быть указан как 'f' или 'm'.");
            }

            // Запись данных в файл с правильной кодировкой
            writeToFile(lastName, firstName, middleName, birthDate, phoneNumber, gender);

        } catch (ParseException e) {
            System.err.println("Ошибка: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Ошибка: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    // Метод для проверки корректности формата даты
    public static boolean isValidDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    // Метод для проверки, что строка является числом
    public static boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Метод для записи данных в файл с кодировкой UTF-8
    public static void writeToFile(String lastName, String firstName, String middleName, String birthDate,
            String phoneNumber, String gender) {
        // Используем кодировку UTF-8 при записи
        String fileName = lastName + ".txt"; // Имя файла на основе фамилии
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(fileName, true), "UTF-8"))) {
            // Записываем данные в файл
            writer.write(
                    lastName + " " + firstName + " " + middleName + " " + birthDate + " " + phoneNumber + " " + gender);
            writer.newLine();
            System.out.println("Данные успешно записаны в файл: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}