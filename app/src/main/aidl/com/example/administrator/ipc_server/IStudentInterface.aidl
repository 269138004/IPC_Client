package com.example.administrator.ipc_server;
import com.example.administrator.ipc_server.aidl_entity.Student;

interface IStudentInterface {
    void addStudent(in Student s);
    List<Student> getAllStudent();
}
