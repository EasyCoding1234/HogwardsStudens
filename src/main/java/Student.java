import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Student implements Serializable {

    private String name;
    private String faculty;
    private int year;
    private String email;
    private String hashPassword;
    private static final long serialVersionUID = 1L;

    //Конструктор
    public Student(String name, String faculty, int year, String email, String password) {
        if (!EmailValidator.isValidEmail(email)) {
            throw new IllegalArgumentException("Некорректный email: " + email);
        }

        this.name = name;
        this.faculty = faculty;
        this.year = year;
        this.email = email;
        this.hashPassword = PasswordHasher.hashPassword(password);
    }

    public String getName() {
        return name;
    }

    public String getFaculty() {
        return faculty;
    }

    public int getYear() {
        return year;
    }

    public String getEmail() {
        return email;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    //Заполняю мапу со студентами
    public static HashMap<String, Integer> addInMap(List<Student> students) {
        //Создал
        HashMap<String, Integer> studentMap = new HashMap<>();

        //Заполнил
        for (Student student : students) {
            String key = student.getName() + "---" + student.getFaculty();
            int value = student.getYear();
            studentMap.put(key, value);
        }
        System.out.println(studentMap);
        return studentMap;
    }

    //Наш список
    static List<Student> list_students = new ArrayList<>();

    //Метод для добавления студента в список
    public static void addInList(String name, String faculty, int year, String email, String password) {
        Student newStudent = new Student(name, faculty, year, email, password);
        list_students.add(newStudent);
    }

    //Удалить студента по имени, факультету и курсу
    public static boolean removeStudent(String name, String faculty, int year) {
        for (Student student : list_students) {
            if (student.getName().equals(name) && student.getFaculty().equals(faculty) && student.getYear() == year) {
                list_students.remove(student);
                return true; //Студент успешно удален
            }
        }
        return false; //Студент не найден
    }

    //Поиск всех студентов определённого факультета и курса
    public static boolean searchStudent(String faculty, int year) {
        for (Student student : list_students) {
            if (student.getFaculty().equals(faculty) && student.getYear() == year) {
                System.out.print("Имя: " + student.getName() + ", Факультет: " + student.getFaculty()
                        + ", Курс: " + student.getYear());
                return true;
            }
        }
        return false;
    }

    //Сохраняем и читаем ОБЪЕКТЫ студентов в файл
    public static void saveStudents(List<Student> students) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("savedStudents.ser"))) {
            oos.writeObject(students); // Записываем объект
            System.out.println("Успешное сохранение!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printSaved() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("savedStudents.ser"))) {
            List<Student> students = (List<Student>) ois.readObject(); // Читаем список

            for (Student student : students) {
                System.out.println("Имя: " + student.getName() +
                        ", Факультет: " + student.getFaculty() +
                        ", Год: " + student.getYear());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Экспорт студентов в файл
    public static void exportInFile(List<Student> students, String address) {
        try (Workbook workbook = new XSSFWorkbook()) { // Создаём Excel-файл
            Sheet sheet = workbook.createSheet("Лист1");

            // Создаём заголовки
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Имя");
            headerRow.createCell(1).setCellValue("Факультет");
            headerRow.createCell(2).setCellValue("Год обучения");
            headerRow.createCell(3).setCellValue("Почта");
            headerRow.createCell(4).setCellValue("Хеш пароля");

            // Записываем несколько данных
            int rowNum = 1;
            for (Student student : students) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(student.getName());
                row.createCell(1).setCellValue(student.getFaculty());
                row.createCell(2).setCellValue(student.getYear());
                row.createCell(3).setCellValue(student.getEmail());
                row.createCell(4).setCellValue(student.getHashPassword());
            }

            //Автоматоматически расширяются колонки
            for(int i = 0; i < students.size(); i++){
                sheet.autoSizeColumn(i);
            }

            // Сохраняем в файл
            try (FileOutputStream fileOut = new FileOutputStream(address)) {
                workbook.write(fileOut);
            }

            System.out.println("Файл успешно создан!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void readInFile(String address) {
        try (FileInputStream file = new FileInputStream(new File(address));
             Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheetAt(0); // Читаем первый лист

            for (Row row : sheet) { // Перебираем строки
                for (Cell cell : row) { // Перебираем ячейки
                    System.out.print(cell.toString() + "\t");
                }
                System.out.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //Вывод списка всех студентов сгруппированных по факультетам и курсам
    public static void printGroupedStudents() {
        HashMap<String, HashMap<Integer, List<Student>>> groupedStudents = new HashMap<>();

        // Группируем студентов
        for (Student student : list_students) {
            groupedStudents.computeIfAbsent(student.getFaculty(), k -> new HashMap<>())
                    .computeIfAbsent(student.getYear(), k -> new ArrayList<>())
                    .add(student);
        }

        //Выводим сгрупированные данные
        for (String faculty : groupedStudents.keySet()) {
            System.out.println("Факультет: " + faculty);
            for (Integer year : groupedStudents.get(faculty).keySet()) {
                System.out.println("  Курс " + year + ":");
                for (Student student : groupedStudents.get(faculty).get(year)) {
                    System.out.println("    " + student.getName());
                }
            }
        }
    }
}