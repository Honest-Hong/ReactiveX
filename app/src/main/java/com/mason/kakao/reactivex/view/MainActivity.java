package com.mason.kakao.reactivex.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.jakewharton.rxbinding.view.RxView;
import com.mason.kakao.reactivex.KrewClickListener;
import com.mason.kakao.reactivex.R;
import com.mason.kakao.reactivex.ThinObserver;
import com.mason.kakao.reactivex.model.Krew;
import com.mason.kakao.reactivex.model.KrewManager;

import org.reactivestreams.Subscription;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity implements KrewClickListener {
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.edit_search) EditText editSearch;
    private KrewListAdapter listAdapter;
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
    }

    @Override
    public void onKrewClick(Krew krew) {
        listAdapter.removeKrew(krew);
        krewManager.removeKrew(krew);
    }

    @OnClick(R.id.button_search)
    public void doSearch() {
        String key = editSearch.getText().toString();
        (key.isEmpty()
                ? krewManager.getKrews()
                : krewManager.findKrews(key))
                .doOnSubscribe(disposable -> showLoadingIndicator(true))
                .subscribe(krews -> {
                    showLoadingIndicator(false);
                    listAdapter.setKrews(krews);
                }, throwable -> {
                    showLoadingIndicator(false);
                    throwable.printStackTrace();
                });
    }

    private void showLoadingIndicator(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(!show ? View.VISIBLE : View.GONE);
    }
}
