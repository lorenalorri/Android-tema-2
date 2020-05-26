package com.example.myapplication_tema2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    public static AppDatabase appDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "userdb").allowMainThreadQueries().build();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, new Fragment_1());
        fragmentTransaction.commit();
    }
}
