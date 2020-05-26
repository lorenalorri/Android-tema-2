package com.example.myapplication_tema2;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class Fragment_1 extends Fragment {

    private EditText firstName, lastName;

    private Button buttonAddUser;
    private Button buttonDeleteUser;
    private Button buttonSyncServer;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private UserAdapter adapter;

    private List<User> users;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_1, container, false);


        firstName = view.findViewById(R.id.textbox_1);
        lastName = view.findViewById((R.id.textbox_2));

        buttonAddUser = view.findViewById(R.id.button_add_user);
        buttonDeleteUser = view.findViewById(R.id.button_remove_user);
        buttonSyncServer = view.findViewById(R.id.button_sync_with_server);


        buttonAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String first_name = firstName.getText().toString();
                String last_name = lastName.getText().toString();


                AsyncTask asyncTask = new SaveDataAsyncTask().execute(first_name, last_name);

            }
        });

        buttonDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String first_name = firstName.getText().toString();
                String last_name = lastName.getText().toString();

                AsyncTask asyncTask = new DeleteDataAsyncTask().execute(first_name, last_name);
            }
        });

//        buttonSyncServer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        recyclerView = view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        buttonSyncServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUsers();


            }
        });

        return view;
    }


    public void getUsers()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//        requestQueue.start();
        String url = "https://jsonplaceholder.typicode.com/users";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            List<User> users = new ArrayList<>();
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String name = object.getString("name");
                                StringTokenizer stringTokenizer = new StringTokenizer(name, " ", false);
                                String firstName = stringTokenizer.nextToken();
                                String lastName = stringTokenizer.nextToken();
                                User user = new User();
                                user.setFirstName(firstName);
                                user.setLastName(lastName);
                                users.add(user);
                                //Toast.makeText(getActivity(), "Ai pus asta in baza de date", Toast.LENGTH_SHORT).show();

                            }
                            new GetUsersAsyncTask(users).execute();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String string;
            }
        });
        requestQueue.add(stringRequest);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////SAVE DATA
    public class SaveDataAsyncTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {


            User user = new User();
            user.setFirstName(strings[0]);
            user.setLastName(strings[1]);

            ApplicationController.getInstance().getDataBaseInstance().userDao().AddUser(user);

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getActivity(), "User added", Toast.LENGTH_SHORT).show();

            firstName.setText("");
            lastName.setText("");
        }
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////DELETE DATA
    public class DeleteDataAsyncTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            User user = new User();
            user.setFirstName(strings[0]);
            user.setLastName(strings[1]);

            ApplicationController.getInstance().getDataBaseInstance().userDao().DeleteUser(user);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getActivity(), "User deleted", Toast.LENGTH_SHORT).show();

            firstName.setText("");
            lastName.setText("");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////REFRESH RECYCLERVIEW
    public class RefreshRecyclerAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            List<User> users;
            users = ApplicationController.getInstance().getDataBaseInstance().userDao().getUsers();
            adapter = new UserAdapter(users);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            recyclerView.setAdapter(adapter);
            Toast.makeText(getActivity(), "Recyclerview Refreshed", Toast.LENGTH_SHORT).show();

        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////GET USERS

    public class GetUsersAsyncTask extends AsyncTask<Void, Void, Void> {
       List< User> user;
        GetUsersAsyncTask(List<User> user)
        {
            this.user = user;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            ApplicationController.getInstance().getDataBaseInstance().userDao().insertAll(user);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getActivity(), "Ai reusit pisi", Toast.LENGTH_SHORT).show();
            new RefreshRecyclerAsyncTask().execute();
        }

    }


}

