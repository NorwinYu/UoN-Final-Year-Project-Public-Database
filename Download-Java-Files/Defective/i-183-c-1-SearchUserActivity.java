package com.example.tweet.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tweet.R;
import com.example.tweet.adapter.UsersAdapter;
import com.example.tweet.network.HttpClient;
import com.example.tweet.pojo.User;

import org.json.JSONException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

public class SearchUserActivity extends AppCompatActivity {

    private RecyclerView usersRecyclerView;

    private UsersAdapter usersAdapter;
    private Toolbar toolbar;
    private EditText queryEditText;
    private Button searchButton;

    private HttpClient httpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_user);

        initRecyclerView();

        toolbar = findViewById(R.id.toolbar);
        queryEditText = toolbar.findViewById(R.id.query_edit_text);
        searchButton = toolbar.findViewById(R.id.search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchUsers();
            }
        });

        queryEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchUsers();
                    return true;
                }
                return false;
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        httpClient = new HttpClient();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initRecyclerView() {
        usersRecyclerView = findViewById(R.id.users_recycler_view);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        UsersAdapter.OnUserClickListener onUserClickListener = new UsersAdapter.OnUserClickListener() {
            @Override
            public void onUserClick(User user) {
                Intent intent = new Intent(SearchUserActivity.this, UserInfoActivity.class);
                intent.putExtra(UserInfoActivity.USER_ID, user.getId());
                startActivity(intent);
            }
        };
        usersAdapter = new UsersAdapter(onUserClickListener);
        usersRecyclerView.setAdapter(usersAdapter);
    }

    private void searchUsers() {

        final String query = queryEditText.getText().toString();
        if (query.length() == 0){
            Toast.makeText(SearchUserActivity.this, "введите текст в поле ввода", Toast.LENGTH_SHORT).show();
            return;
        }

        new UsersAsyncTask().execute(query);

    }

    private class UsersAsyncTask extends AsyncTask<String, Integer, Collection<User>>{

        @Override
        protected Collection<User> doInBackground(String... strings) {

            String query = strings[0];

            try {
               return httpClient.readUsers(query);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(Collection<User> users) {

            if (users != null) {
                usersAdapter.clearItem();
                usersAdapter.setItems(users);
            }
            else {
            Toast.makeText(SearchUserActivity.this, "произошла ошибка!", Toast.LENGTH_SHORT).show();
            }
        }


    }


}
