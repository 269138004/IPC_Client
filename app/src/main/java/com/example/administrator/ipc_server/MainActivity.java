package com.example.administrator.ipc_server;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.ipc_client.R;
import com.example.administrator.ipc_server.aidl_entity.Student;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtn_add , mBtn_getAll , mBtn_start_aidl;
    private static final String TAG = "MainActivity";
    private IStudentInterface iStudentInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mBtn_add = findViewById(R.id.mBtn_add);
        mBtn_getAll = findViewById(R.id.mBtn_getAll);
        mBtn_start_aidl = findViewById(R.id.mBtn_start_aidl);
        mBtn_add.setOnClickListener(this);
        mBtn_getAll.setOnClickListener(this);
        mBtn_start_aidl.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mBtn_start_aidl:
                startAIDL();
                break;
            case R.id.mBtn_add:
                addStudentToServer();
                break;
            case R.id.mBtn_getAll:
                getStudentFromServer();
                break;
        }
    }

    private void startAIDL() {
        Intent intent = new Intent();
        intent.setAction("tkc.connect.ipc.server");
        intent.setPackage("com.example.administrator.ipc_server");
        bindService(intent , serviceConnection , Context.BIND_AUTO_CREATE);
        Log.e(TAG, "startAIDL: 启动对应进程");
    }

    private void addStudentToServer(){
        if (iStudentInterface == null)return;
        try {
            iStudentInterface.addStudent(new Student("what" , 1 , 24));
            Log.e(TAG, "添加学生");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void getStudentFromServer(){
        if (iStudentInterface == null)return;
        try {
            List<Student> allStudent = iStudentInterface.getAllStudent();
            for (Student student : allStudent) {
                Log.e(TAG, "allStudent--->name:"+student.getName()+"...gender:"+student.getGender()+"...age:"+student.getAge());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iStudentInterface = IStudentInterface.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
}
