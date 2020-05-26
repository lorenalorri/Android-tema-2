package com.example.myapplication_tema2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Fragment_1 extends Fragment {

    private EditText firstName, lastName;
    private Button buttonAddUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);

        firstName = view.findViewById(R.id.textbox_1);
        lastName = view.findViewById((R.id.textbox_2));
        buttonAddUser = view.findViewById(R.id.button_add_user);

        buttonAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String first_name = firstName.getText().toString();
                String last_name = lastName.getText().toString();

                User user = new User();
                user.setFirstName(first_name);
                user.setLastName(last_name);
                user.setUid(1);

                MainActivity.appDatabase.userDao().AddUser(user);
                Toast.makeText(getActivity(), "User added", Toast.LENGTH_SHORT).show();

                firstName.setText("");
                lastName.setText("");
            }
        });
        return view;
    }
}