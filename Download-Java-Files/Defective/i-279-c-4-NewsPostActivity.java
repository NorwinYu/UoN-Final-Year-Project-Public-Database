/*
 * Copyright (C) 2018 Evgenii Vizhev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pro.dprof.dorprofzhelzszd.ui.newspost;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import pro.dprof.dorprofzhelzszd.R;
import pro.dprof.dorprofzhelzszd.ui.base.BaseActivity;
import pro.dprof.dorprofzhelzszd.dataclasses.News;

public final class NewsPostActivity extends BaseActivity implements NewsPostMvpView {

    @BindView(R.id.cv_news_post) CardView mCardView;
    @BindView(R.id.tv_news_post_title) TextView mTvPostTitle;
    @BindView(R.id.tv_news_post_text) TextView mTvPostText;
    @BindView(R.id.tb_news_post) Toolbar mToolbar;
    @BindView(R.id.iv_news_post_picture) ImageView mIvPicture;
    @BindView(R.id.pb_news_post) ProgressBar mProgressBar;

    private NewsPostMvpPresenter<NewsPostMvpView> mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_post);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mPresenter = getActivityComponent().getNewsPostPresenter();
        mPresenter.onAttach(this);
        if (savedInstanceState == null) {
            final Intent intent = getIntent();
            final String postTitle = intent.getStringExtra("PostTitle");
            final String postLink = intent.getStringExtra("PostLink");
            final String imageLink = intent.getStringExtra("ImageLink");
            mPresenter.onSetPostContent(postTitle, postLink, imageLink);
        } else {
            mPresenter.onSetPostContent("Title", "postLink", "imageLink");
        }
        mProgressBar.setVisibility(View.VISIBLE);
        mCardView.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setPostContent(final News postContent) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String postDate = postContent.getDate();
                    final String postTitle = postContent.getTitle();
                    final String postTextHtml = postContent.getText();
                    final String imageLink = postContent.getImageLink();
                    mTvPostTitle.setText(postTitle);
                    mTvPostText.setText(Html.fromHtml(postTextHtml));
                    mTvPostText.setMovementMethod(LinkMovementMethod.getInstance());
                    mCardView.setVisibility(View.VISIBLE);
                    try {
                        Picasso.get()
                                .load(imageLink)
                                .resize(420, 290)
                                .into(mIvPicture);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                    final ActionBar actionBar = getSupportActionBar();
                    if (actionBar != null) {
                        actionBar.setTitle(postDate);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    Toast.makeText(NewsPostActivity.this, R.string.connect_error_message, Toast.LENGTH_SHORT).show();
                }
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }
}
