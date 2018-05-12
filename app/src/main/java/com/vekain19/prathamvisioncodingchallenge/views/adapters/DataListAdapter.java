package com.vekain19.prathamvisioncodingchallenge.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vekain19.prathamvisioncodingchallenge.R;
import com.vekain19.prathamvisioncodingchallenge.helpers.Entity.Pojo;
import com.vekain19.prathamvisioncodingchallenge.helpers.Graphics.ColorGenerator;
import com.vekain19.prathamvisioncodingchallenge.helpers.Graphics.TextDrawable;

import java.util.ArrayList;

public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.Holder> {

    private ArrayList<Pojo> dataList;
    private Context context;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;

    public DataListAdapter(ArrayList<Pojo> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context)
                .inflate(R.layout.list_contact_layout,
                        parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final Pojo data = dataList.get(position);

        holder.name.setText(data.getName());
        holder.number.setText(data.getNumber());

        TextDrawable.IBuilder mDrawableBuilder = TextDrawable.builder()
                .beginConfig()
                .withBorder(4)
                .endConfig()
                .roundRect(10);

        TextDrawable drawable = mDrawableBuilder.build(String.valueOf(data.getName().charAt(0)),
                mColorGenerator.getColor(data.getName()));

        Glide.with(context).load("").placeholder(drawable)
                .error(drawable)
                .animate(R.anim.base_slide_right_in)
                .into(holder.imageThumbnail);

        holder.dataCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        ImageView imageThumbnail;
        TextView name,number;
        CardView dataCard;

        Holder(View itemView) {
            super(itemView);

            imageThumbnail = itemView.findViewById(R.id.userImage);
            name = itemView.findViewById(R.id.userName);
            number = itemView.findViewById(R.id.userNumber);
            dataCard = itemView.findViewById(R.id.dataCard);
        }
    }
}
