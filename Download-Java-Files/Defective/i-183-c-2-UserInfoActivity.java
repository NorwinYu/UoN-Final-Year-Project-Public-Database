package com.example.tweet.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tweet.R;
import com.example.tweet.adapter.TweetAdapter;
import com.example.tweet.network.HttpClient;
import com.example.tweet.pojo.Tweet;
import com.example.tweet.pojo.User;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.util.Collection;

public class UserInfoActivity extends AppCompatActivity {

    public static final String USER_ID = "userId";

    private ImageView userImageView;
    private TextView nameTextView;
    private TextView nickTextView;
    private TextView descriptionTextView;
    private TextView locationTextView;
    private TextView followingCountTextView;
    private TextView followersCountTextView;

    private RecyclerView tweetsRecyclerView;
    private TweetAdapter tweetAdapter;

    private HttpClient httpClient;

    private android.support.v7.widget.Toolbar toolbar;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search){
            Intent intent = new Intent(this, SearchUserActivity.class);
            startActivity(intent);
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_info_menu, menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        long userId = getIntent().getLongExtra(USER_ID, -1);

        userImageView = findViewById(R.id.user_image_view);
        nameTextView = findViewById(R.id.user_name_text_view);
        nickTextView = findViewById(R.id.user_nick_text_view);
        descriptionTextView = findViewById(R.id.user_description_text_view);
        locationTextView = findViewById(R.id.user_location_text_view);
        followingCountTextView = findViewById(R.id.following_count_text_view);
        followersCountTextView = findViewById(R.id.followers_count_text_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        initRecyclerView();

        httpClient = new HttpClient();

        loadUserInfo(userId);
        loadTweets(userId);

    }

    private void initRecyclerView(){
        tweetsRecyclerView = findViewById(R.id.tweets_recycler_view);
        tweetsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        tweetAdapter = new TweetAdapter();
        tweetsRecyclerView.setAdapter(tweetAdapter);
    }

    private void displayUserInfo(User user){
        Picasso.get().load(user.getImageUrl()).into(userImageView);
        nameTextView.setText(user.getName());
        nickTextView.setText(user.getNick());
        descriptionTextView.setText(user.getDescription());
        locationTextView.setText(user.getLocate());
        followingCountTextView.setText(String.valueOf(user.getFollowingCount()));
        followersCountTextView.setText(String.valueOf(user.getFollowersCount()));

        getSupportActionBar().setTitle(user.getName());
    }

    private void loadUserInfo(final long userId){

        new UserInfoAsyncTask().execute(userId);

    }

    private void loadTweets(Long userId){
        new TweetsAsyncTask().execute(userId);
    }


    private class TweetsAsyncTask extends AsyncTask<Long, Integer, Collection<Tweet>>{

        @Override
        protected Collection<Tweet> doInBackground(Long... longs) {

            try {
                Long userId = longs[0];
                return new HttpClient().readTweets(userId);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Collection<Tweet> tweets) {


            if (tweets != null) {
                tweetAdapter.setItems(tweets);
            }
            else {
            Toast.makeText(UserInfoActivity.this, "произошла ошибка!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class UserInfoAsyncTask extends AsyncTask<Long, Integer, User>{


        @Override
        protected User doInBackground(Long... longs) {
            try {
                Long userId = longs[0];
                return new HttpClient().readUserInfo(userId);

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(User s) {

            if (s != null){
                displayUserInfo(s);
            }
            else {
                Toast.makeText(UserInfoActivity.this, "произошла ошибка!", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
