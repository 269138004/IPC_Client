package com.example.administrator.ipc_client;
import com.example.administrator.ipc_client.aidl_entity.Student;

interface IStudentInterface {
    void addStudent(in Student s);
    List<Student> getAllStudent();
}
