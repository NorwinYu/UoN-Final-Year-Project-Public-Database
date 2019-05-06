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

import java.util.concurrent.TimeUnit;

import pro.dprof.dorprofzhelzszd.ui.base.BasePresenter;
import pro.dprof.dorprofzhelzszd.dataclasses.News;
import pro.dprof.dorprofzhelzszd.utils.AsyncUtil;

public final class NewsPostPresenter<V extends NewsPostMvpView> extends BasePresenter<V>
        implements NewsPostMvpPresenter<V> {

    private News news;

    @Override
    public void onSetPostContent(final String postTitle, final String postLink, final String imageLink) {
        AsyncUtil.submitRunnable(new Runnable() {
            @Override
            public void run() {
                if (news == null) {
                    news = getDataProvider().getNewsPostText(postLink);
                    news.setTitle(postTitle);
                    news.setImageLink(imageLink);
                }
                int retry = 0;
                do {
                    try {
                        getMvpView().setPostContent(news);
                        retry = 2;
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        try {
                            TimeUnit.MILLISECONDS.sleep(100);
                        } catch (InterruptedException ie) {
                            ie.printStackTrace();
                        }
                    } finally {
                        retry++;
                    }
                } while (retry < 2);
            }
        });
    }
}
