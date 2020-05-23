package com.summertaker.fruits3;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

class ViewHolder extends RecyclerView.ViewHolder {

    private Context mContext;
    private ViewInterface mViewInterface;
    private ImageView ivPicture;
    private TextView tvName;
    private TextView tvFurigana;
    private TextView tvGroupTeam;
    private TextView tvBirthdayAge;
    private ImageView ivTwitter;
    private ImageView ivInstagram;
    private ImageView ivWiki;

    ViewHolder(Context context, ViewInterface viewInterface, View itemView) {
        super(itemView);
        mContext = context;
        mViewInterface = viewInterface;
        ivPicture = itemView.findViewById(R.id.ivPicture);
        tvName = itemView.findViewById(R.id.tvName);
        tvFurigana = itemView.findViewById(R.id.tvFurigana);
        tvGroupTeam = itemView.findViewById(R.id.tvGroupTeam);
        tvBirthdayAge = itemView.findViewById(R.id.tvBirthdayAge);
        ivTwitter = itemView.findViewById(R.id.ivTwitter);
        ivInstagram = itemView.findViewById(R.id.ivInstagram);
        ivWiki = itemView.findViewById(R.id.ivWiki);
    }

    void onBind(final Member member) {
        Glide.with(mContext).load(member.getImage()).placeholder(R.drawable.placeholder).into(ivPicture);
        ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewInterface.onMemberPictureClick(member);
            }
        });

        tvName.setText(member.getName());
        tvFurigana.setText(member.getFurigana());
        String groupTeam = member.getGroup() + " " + member.getTeam();
        tvGroupTeam.setText(groupTeam);
        String birthdayAge = member.getBirthday() + " " + member.getAge();
        tvBirthdayAge.setText(birthdayAge);

        //--------------
        // 트위터
        //--------------
        if (member.getTwitter() != null && !member.getTwitter().isEmpty()) {
            ivTwitter.setVisibility(View.VISIBLE);
            ivTwitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(member.getTwitter()));
                    mContext.startActivity(intent);
                }
            });
        } else {
            ivTwitter.setVisibility(View.GONE);
        }

        //--------------
        // 인스타그램
        //--------------
        if (member.getInstagram() != null && !member.getInstagram().isEmpty()) {
            ivInstagram.setVisibility(View.VISIBLE);
            ivInstagram.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(member.getInstagram()));
                    mContext.startActivity(intent);
                }
            });
        } else {
            ivInstagram.setVisibility(View.GONE);
        }

        //--------------
        // 위키
        //--------------
        if (member.getWiki() != null && !member.getWiki().isEmpty()) {
            ivWiki.setVisibility(View.VISIBLE);
            ivWiki.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(member.getWiki()));
                    mContext.startActivity(intent);
                }
            });
        } else {
            ivWiki.setVisibility(View.GONE);
        }
    }

    String getUserId(String url) {
        String[] array = url.split("/");
        String userId = array[array.length - 1];
        if ("/".equals(userId)) {
            userId = array[array.length - 2];
        }
        return userId;
    }
}
