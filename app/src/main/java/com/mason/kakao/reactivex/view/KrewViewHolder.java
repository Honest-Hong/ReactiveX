package com.mason.kakao.reactivex.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mason.kakao.reactivex.R;
import com.mason.kakao.reactivex.model.Krew;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kakao on 2017. 10. 25..
 */

public class KrewViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_view) TextView textView;
    private Krew krew;

    public KrewViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setupView(Krew krew) {
        this.krew = krew;
        textView.setText(krew.toString());
    }
}
