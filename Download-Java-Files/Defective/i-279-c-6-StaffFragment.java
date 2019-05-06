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

package pro.dprof.dorprofzhelzszd.ui.staff;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pro.dprof.dorprofzhelzszd.R;
import pro.dprof.dorprofzhelzszd.ui.base.BaseFragment;

public final class StaffFragment extends BaseFragment implements StaffMvpView {

    public final static String TAG = "StaffFragment";

    @BindView(R.id.pb_staff) ProgressBar mProgressBar;
    @BindView(R.id.rv_staff) RecyclerView mRecyclerView;

    private Unbinder mUnbinder;
    private StaffMvpPresenter<StaffMvpView> mPresenter;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_staff, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mRecyclerView.setVisibility(savedInstanceState == null ? View.GONE : View.VISIBLE);
        mProgressBar.setVisibility(savedInstanceState == null ? View.VISIBLE : View.GONE);
        mPresenter = getActivityComponent().getPersonsPresenter();
        mPresenter.onAttach(this);
        mPresenter.onSetAdapter();
        mPresenter.onSetContent();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();
        mPresenter = null;
    }


    @Override
    public void setAdapter(final StaffAdapter adapter) {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showContent() {
        try {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mProgressBar.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    if (mRecyclerView.getAdapter().getItemCount() == 0) {
                        Toast.makeText(getActivity(), R.string.connect_error_message, Toast.LENGTH_SHORT).show();

                    }
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
