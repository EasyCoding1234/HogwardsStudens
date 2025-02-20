public class Main {
    public static void main(String[] args) {
        //Заполнил список
        Student.addInList("Harry", "Gryffindor", 1,
                "harry@gmail.com", "1234567890");
        Student.addInList("Hermione", "Gryffindor", 3,
                "hermione@gmal.com", "123456df7890g");
        Student.addInList("Malfoy", "Slytherin", 3,
                "malfoy@gmal.com", "12adsf24dfs0g");
        Student.addInList("Timofey", "Ravenclaw", 4,
                "sans.papirus.undyne@gmal.com", "1234asdf20g");
        Student.addInList("Cedric", "Hufflepuff", 3,
                "cedric@gmal.com", "er3456adsf223g");
        Student.addInList("Filius", "Ravenclaw", 3,
                "Filius@gmal.com", "1234asdffgdf7890g");
        Student.addInList("Quirrell", "Ravenclaw", 3,
                "quirrell@gmal.com", "asdfa256df78fdg");
        Student.addInList("Riddle", "Slytherin", 1,
                "riddle@gmal.com", "i234af2f7890g");
        Student.addInList("Snape", "Slytherin", 2,
                "snape@gmal.com", "12asdf256df7890g");
        Student.addInList("Lupine", "Hufflepuff", 3,
                "lupine@gmal.com", "hjk3ads36df7890g");
        Student.addInList("Longbottom", "Gryffindor", 1,
                "longbottom@gmal.com", "12636dadsf90g");
        Student.addInList("Weasley", "Gryffindor", 2,
                "weasley.ww@gmal.com", "12sadsf4df7890g");

        //Заполнил мапу
//        addInMap(list_students);
//        searchStudent("Gryffindor", 2);
//        removeStudent("Timofey", "Ravenclaw", 4);

        Student.printGroupedStudents();

//        saveStudents(list_students);
//        printSaved();

        Student.exportInFile(Student.list_students, "exportedStudents.xlsx");
        Student.readInFile("exportedStudents.xlsx");

//        Student.printGroupedStudents();
    }
}
