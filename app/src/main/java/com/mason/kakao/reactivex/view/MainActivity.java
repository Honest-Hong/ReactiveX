package com.mason.kakao.reactivex.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.mason.kakao.reactivex.KrewClickListener;
import com.mason.kakao.reactivex.R;
import com.mason.kakao.reactivex.ThinObserver;
import com.mason.kakao.reactivex.model.Krew;
import com.mason.kakao.reactivex.model.KrewManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity implements KrewClickListener {
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.edit_search) EditText editSearch;
    private KrewListAdapter listAdapter;
    private PublishSubject<Krew> removeSubject = PublishSubject.create();
    private KrewManager krewManager = KrewManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        listAdapter = new KrewListAdapter(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        doSearch();
        initRemoveSubject();
    }

    @Override
    public void onKrewClick(Krew krew) {
        removeSubject.onNext(krew);
    }

    @OnClick(R.id.button_search)
    public void doSearch() {
        String key = editSearch.getText().toString();
        Observable<List<Krew>> observable;
        if(key.isEmpty()) {
            observable = krewManager.getKrews();
        } else {
            observable = krewManager.findKrews(key);
        }
        showLoadingIndicator(true);
        observable.subscribe(new ThinObserver<List<Krew>>() {
            @Override
            public void onNext(@NonNull List<Krew> krews) {
                showLoadingIndicator(false);
                listAdapter.setKrews(krews);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });
    }

    private void showLoadingIndicator(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(!show ? View.VISIBLE : View.GONE);
    }

    private void initRemoveSubject() {
        removeSubject.subscribe(new ThinObserver<Krew>() {
            @Override
            public void onNext(@NonNull Krew krew) {
                krewManager.removeKrew(krew);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });
        removeSubject.subscribe(new ThinObserver<Krew>() {
            @Override
            public void onNext(@NonNull Krew krew) {
                listAdapter.removeKrew(krew);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });
    }
}
