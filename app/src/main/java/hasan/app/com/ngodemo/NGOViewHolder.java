package hasan.app.com.ngodemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class NGOViewHolder extends RecyclerView.ViewHolder {

    View mView;

    public NGOViewHolder(View itemView) {
        super(itemView);
        mView=itemView;

        //itemClick expand
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v,getAdapterPosition());
            }
        });

        //on long press
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemClick(v,getAdapterPosition());
                return true;
            }
        });
    }

    //see deatils to recycle view
    public void setDetails(Context ctx, String Name, String Address, String Imageurl, String Background, String Phone, String Website)
        //Views
    {
        ImageView postImage = mView.findViewById(R.id.post_Image);
        TextView postTitle = mView.findViewById(R.id.post_Title);
        TextView postAddress = mView.findViewById(R.id.post_Location);
        TextView postPhone = mView.findViewById(R.id.post_Phone1);
        TextView postWebsite = mView.findViewById(R.id.post_Website1);
        TextView postAbout = mView.findViewById(R.id.post_About1);

        //set to view rows
        postTitle.setText(Name);
        postAbout.setText(Background);
        postPhone.setText(Phone);
        postWebsite.setText(Website);
        postAddress.setText(Address);

        Glide.with(ctx)
                .load(Imageurl)
                .into(postImage);

    }

    //putting on click on recycler rows
    private NGOViewHolder.ClickListener mClickListener;

    //interface send callbacks
    public interface ClickListener{
        void onItemClick(View view, int position);
        void onLongItemClick(View view, int position);
    }

    public void setOnClickListener(NGOViewHolder.ClickListener clickListener)
    {
        mClickListener=clickListener;
    }

}
