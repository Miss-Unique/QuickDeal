package com.example.soumyaagarwal.quickdeal.attach_row;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.soumyaagarwal.quickdeal.R;

import java.util.List;

public class attachment_adapter extends RecyclerView.Adapter<attachment_adapter.MyViewHolder>  {

    private List<row_items> attachlist;
    private Context mcontext;
    float scale;
    int w,h;

    public attachment_adapter(Context c,List<row_items> attachlist)
    {
        this.attachlist = attachlist;
        this.mcontext=c;
        scale=mcontext.getResources().getDisplayMetrics().density;
        w=mcontext.getResources().getDisplayMetrics().widthPixels-(int)(14*scale);
        h=(w/16)*9;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView image;
        public TextView name;
        public TextView size;
        public ImageButton delete;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView)view.findViewById(R.id.cardimage);
            name = (TextView)view.findViewById(R.id.name);
            size = (TextView)view.findViewById(R.id.size);
            delete = (ImageButton)view.findViewById(R.id.delete);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int p = getAdapterPosition();
                    attachlist.remove(p);
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_card_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final row_items movie = attachlist.get(position);

        holder.name.setText(movie.getName());
        holder.size.setText(movie.getSize()+"kb");

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
            holder.image.setImageResource(movie.getImage());
        }
        else
        {
            Bitmap bitmap = BitmapFactory.decodeResource(mcontext.getResources(),attachlist.get(position).getImage());
            bitmap=Bitmap.createScaledBitmap(bitmap,w,h,false);

            holder.image.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount()
    {
        return attachlist.size();
    }
}
