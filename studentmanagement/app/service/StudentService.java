package service;

import entities.Student;

import java.util.HashMap;
import java.util.Map;

public class StudentService {
    private static StudentService studentService;
    private Map<Integer, Student> studentHashMap = new HashMap<>();



    public Student addStudent(Student student) {
        int id = studentHashMap.size()+1;
        student.setId(id);
        studentHashMap.put(id, student);
        return student;
    }
    public Student getStudent(int id) {
        return studentHashMap.get(id);
    }

}
