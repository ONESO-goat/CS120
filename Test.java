
class Test{
public static void main(String[] args){
    Student student1 = new Student(1, "joe");
    Student student2 = new Student(2, "lin");
    Student student3 = new Student(3, "Alish");

    student1.setGpa(3.0);
    student2.setGpa(2.45);
    student3.setGpa(4.0);
    
    

    System.out.println(student1.toString());

    Student[] students = {student1, student2, student3};

    StudentHelper helper = new StudentHelper();
    helper.showAboveAvgStudent(students);

}
}