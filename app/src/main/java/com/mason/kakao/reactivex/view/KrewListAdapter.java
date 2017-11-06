package com.mason.kakao.reactivex.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding.view.RxView;
import com.mason.kakao.reactivex.KrewClickListener;
import com.mason.kakao.reactivex.R;
import com.mason.kakao.reactivex.model.Krew;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by kakao on 2017. 10. 25..
 */

public class KrewListAdapter extends RecyclerView.Adapter<KrewViewHolder> {
    private Context context;
    private List<Krew> krews;
    private KrewClickListener krewClickListener;

    public KrewListAdapter(Context context) {
        this.context = context;
        if(context instanceof KrewClickListener) {
            this.krewClickListener = (KrewClickListener) context;
        }
    }

    @Override
    public KrewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final KrewViewHolder holder = new KrewViewHolder(LayoutInflater.from(context).inflate(R.layout.viewholder_krew, parent, false));

        RxView.clicks(holder.itemView)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .map(e -> krews.get(holder.getAdapterPosition()))
                .delay(200, TimeUnit.MILLISECONDS)
                .subscribe(krewClickListener::onKrewClick);
        return holder;
    }

    @Override
    public void onBindViewHolder(KrewViewHolder holder, int position) {
        holder.setupView(krews.get(position));
    }

    @Override
    public int getItemCount() {
        return krews == null ? 0 : krews.size();
    }

    public void setKrews(List<Krew> krews) {
        this.krews = krews;
        notifyDataSetChanged();
    }

    public void removeKrew(Krew krew) {
        int index = krews.indexOf(krew);
        krews.remove(index);
        notifyItemRemoved(index);
    }
}
