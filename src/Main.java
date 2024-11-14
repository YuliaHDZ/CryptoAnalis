import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static char[] elementsOfAlphabetic = new char[]{
                    'А', 'Б', 'В', 'Г', 'Ґ', 'Д', 'Е', 'Є',
                    'Ж', 'З', 'И', 'І', 'Ї', 'Й', 'К', 'Л',
                    'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У',
                    'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ь', 'Ю',
                    'Я', 'а', 'б', 'в', 'г', 'ґ', 'д', 'е', 'є',
                    'ж', 'з', 'и', 'і', 'ї', 'й', 'к', 'л',
                    'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у',
                    'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ь', 'ю',
                    'я', ':', '!', '?', ',', '.', '\"', ' ', '-'};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Добрий день! Введіть будь ласка шлях до вашохо файлу");
        String path = scanner.nextLine();

        boolean isValidPath = false;

        while (!isValidPath) {
            try {
                Path filePath = Paths.get(path);
                if (Files.exists(filePath) && Files.isRegularFile(filePath) && Files.isReadable(filePath)) {
                    isValidPath = true;
                    readFile(path);
                } else {
                    System.out.println("Шлях до файлу не вірний. Спробуйте ще раз!");
                    path = scanner.nextLine();
                }
            } catch (InvalidPathException e) {
                System.out.println("Некоректний шлях. Спробуйте ще раз!");
            }
        }

        System.out.println("Оберіть що саме ви хочете зробити з данними файлу :\n" +
                        "Якщо ви хочете зашифрувати данні шифром Цезаря , введіть " + "\"+\"\n" +
                        "Якщо ви хочете розшифрувати данні шифром Цезаря , введіть " + "\"-\"\n" +
                        "Якщо ви хочете використати метод \"brute force\" , введіть" + " \"!\"");

        String userSign = scanner.nextLine();

        int key = 0;

        boolean check = false;
        while (!check) {

            if (userSign.equals("!")) {
                System.out.println("Ключ вводити не потрібно!");
            } else if (userSign.equals("+") || userSign.equals("-")) {
                System.out.println("Тепер введіть ключ " + "(Обовязково число!)");
                boolean useNotTrue = false;
                while (!useNotTrue) {
                    String userInputNumber = scanner.nextLine();
                    if (userInputNumber.matches("\\d+")) {
                        key = Integer.parseInt(userInputNumber);
                        useNotTrue = true;
                    } else {
                        System.out.println("Введений символ не є числом! Спробуйте ще раз)");
                    }
                }
            }

            if (userSign.equals("+")) {
                encrypt(path, key);
                check = true;
            } else if (userSign.equals("-")) {
                decrypt(path, key);
                check = true;
            } else if (userSign.equals("!")) {
                brtForce(path);
                check = true;
            } else {
                System.out.println("Введіть вірний символ!");
                userSign = scanner.nextLine();
            }
        }
    }

    public static void encrypt(String path, int key) {

        String FilePath = "encrypted.txt";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FilePath))) {
            String linen = bufferedReader.readLine();
            char[] charArray = linen.toCharArray();
            for (int i = 0; i < linen.length(); i++) {
                int index = search(elementsOfAlphabetic, charArray[i]);
                int newIndex = (index + key) % elementsOfAlphabetic.length;
                String newChar = String.valueOf(elementsOfAlphabetic[newIndex]);
                bufferedWriter.write(newChar);
            }
            bufferedReader.close();
            bufferedWriter.close();
            readNAme(FilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void decrypt(String path, int key) {

        String FilePath = "decrypted.txt";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FilePath))) {
            String lines = bufferedReader.readLine();
            char[] charArray = lines.toCharArray();

            for (int i = 0; i < lines.length(); i++) {
                int index = search(elementsOfAlphabetic, charArray[i]);
                if (index == -1) {
                    continue;
                }
                int newIndex = (index - key) % elementsOfAlphabetic.length;
                if (newIndex <= 0) {
                    newIndex = (index - key + elementsOfAlphabetic.length) % elementsOfAlphabetic.length;
                }
                String newChar = String.valueOf(elementsOfAlphabetic[newIndex]);
                bufferedWriter.write(newChar);
            }
            bufferedReader.close();
            bufferedWriter.close();
            readNAme(FilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void brtForce(String path) {

        String[] words = new String[]{
                        "При", "Дяк", "Буд", "Кох", "Род", "Дру", "Укр", "Мов", "Їжа", "Вод",
                        "Сон", "Дощ", "Зим", "Вес", "Літ", "Осі", "Міс", "Сел", "Люб", "Діт", "Шко", "Вчи",
                        "Кни", "Муз", "Теа", "Філ", "Мис", "Іст", "Кул", "Спо", "Фут", "Бас",
                        "Гра", "Под", "Мов", "Сло", "Роз", "Пис", "Чит", "Нав", "Роб", "Гро", "Біз",
                        "Еко", "Здо", "Хво", "Лік", "Нап", "Фру", "Ово", "М'я", "Риб",
                        "Пта", "Тва", "Кіт", "Соб", "Кві", "Дер", "Річ", "Озе", "Гор", "Мор", "Зем",
                        "Неб", "Зір", "Міс", "Віт", "Сні", "Вес", "Зем", "Вул", "Буд",
                        "Ква", "Кім", "Две", "Вік", "Схо", "Меб", "Лам", "Ком", "Тел", "Авт", "Вел",
                        "Пош", "Зам", "Клю", "Маг", "Рин", "Пар", "Міс", "Зал", "Аер", "Мов", "Сло", "Вчи", "Уче",
                        "при", "дяк", "буд", "кох", "род", "дру", "укр", "мов", "їжа", "вод",
                        "сон", "дощ", "зим", "вес", "літ", "осі", "міс", "сел", "люб", "діт", "шко", "вчи",
                        "кни", "муз", "теа", "філ", "мис", "іст", "кул", "спо", "фут", "бас",
                        "гра", "под", "мов", "сло", "роз", "пис", "чит", "нав", "роб", "гро", "біз",
                        "еко", "здо", "хво", "лік", "нап", "фру", "ово", "м'я", "риб",
                        "пта", "тва", "кіт", "соба", "кві", "дер", "річ", "озе", "гор", "мор", "зем",
                        "неб", "зір", "міс", "віт", "сні", "вес", "зем", "вул", "буд",
                        "ква", "кім", "две", "вік", "схо", "меб", "лам", "ком", "тел", "авт", "вел",
                        "пош", "зам", "клю", "маг", "рин", "пар", "міс", "зал", "аер", "мов", "сло", "вчи", "уче",
                        "я", "Я", "Ти", "ти", "Ми", "ми"
        };

        ArrayList<String> wordsList = new ArrayList<>(Arrays.asList(words));

        String pathOf = "decriptBrdForse.txt";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(pathOf))) {
            String line = bufferedReader.readLine();
            char[] newElement = new char[line.length()];
            boolean found = false;
            for (int key = 1; key < elementsOfAlphabetic.length; key++) {
                for (int b = 0; b < line.length(); b++) {
                    char[] elementsOfReader = line.toCharArray();
                    int index = search(elementsOfAlphabetic, elementsOfReader[b]);
                    int newIndex = (index - key + elementsOfAlphabetic.length) % elementsOfAlphabetic.length;
                    if (newIndex == -1) {
                        newIndex = index;
                    }
                    newElement[b] = elementsOfAlphabetic[newIndex];
                }

                String sub = String.valueOf(newElement);
                String substringFromString = sub.substring(0, 3);

                for (int c = 0; c < words.length; c++) {
                    if (wordsList.contains(substringFromString)) {
                        System.out.println("Ваші данні розшифровано!");
                        System.out.println("Ключ: " + key);
                        c = words.length + 1;
                        key = elementsOfAlphabetic.length + 1;
                        bufferedWriter.write(sub);
                    } else {
                        continue;
                    }
                }
            }
           waitTime();
            System.out.println("Данні не підходять для використання методу brute force!");
            bufferedReader.close();
            bufferedWriter.close();
            readFile(pathOf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFile(String path) {
        try {
            File file = new File(path);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Зчитанні данні з вашого файлу: " + line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readNAme(String newNAme) {
        try {
            File file = new File(newNAme);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                waitTime();
                System.out.println("Данні успішно опрацьованні: " + line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int search(char[] alpha, char target) {
        for (int i = 0; i < alpha.length; i++) {
            if (alpha[i] == target) {
                return i;
            }
        }
        return -1;
    }
    public static void waitTime(){
        try {
            for (int i = 1; i <= 5; i++) {
                System.out.println(".");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
