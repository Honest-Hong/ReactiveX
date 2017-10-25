package com.mason.kakao.reactivex.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mason.kakao.reactivex.KrewClickListener;
import com.mason.kakao.reactivex.R;
import com.mason.kakao.reactivex.model.Krew;

import java.util.List;

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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if(position != -1 && krewClickListener != null) {
                    krewClickListener.onKrewClick(krews.get(position));
                }
            }
        });
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
