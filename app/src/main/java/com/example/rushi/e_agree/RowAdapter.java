package com.example.rushi.e_agree;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rushi on 18-04-2017.
 */

public class RowAdapter extends RecyclerView.Adapter<RowAdapter.MyViewHolder> {


    //changes by me
      //private NetworkImageView NetworkImageView;
      //private ImageLoader imageLoader;
//
    List<BuyInfo> l = new ArrayList<BuyInfo>();

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView imagePath,name,price,quantity,mobileno,email,address;
        public ImageView img;
        public MyViewHolder(View view)
        {
            super(view);

            //changes by me
           /* NetworkImageView = (NetworkImageView)View.findViewById(R.id
                    .imageView);
            */
           //Obtained imagePath
          //  imagePath=(TextView)view.findViewById(R.id.image);
            img = (ImageView)view.findViewById(R.id.imageView2);
            name=(TextView)view.findViewById(R.id.name);
            price=(TextView)view.findViewById(R.id.price);
            quantity=(TextView)view.findViewById(R.id.quantity);
            mobileno=(TextView)view.findViewById(R.id.mobileno);
            email=(TextView)view.findViewById(R.id.email);
            address=(TextView)view.findViewById(R.id.address);
        }
    }
    public RowAdapter(List<BuyInfo> b)
    {
        this.l=b;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.buy_row,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,int i)
    {
        final BuyInfo buyInfo=l.get(i);
        //holder.imagePath.setImage(buyInfo.getEventName());
/*
         imageLoader = CustomVolleyRequest.getInstance(this.getApplicationContext()).getImageLoader();
         imageLoader.get(buyInfo.imagePath, ImageLoader.getImageListener(NetworkImageView,R.mipmap.truiton_short, android.R.drawable.ic_dialog_alert));
         holder.NetworkImageView.setImageUrl(buyInfo.imagePath, imageLoader);
*/
        //code above this is changes by me
        //holder.img.setImageBitmap(BitmapFactory.decodeFile(buyInfo.imagePath));
        //File file = new File(buyInfo.imagePath);
        //Uri uri = Uri.fromFile(file);
        //holder.img.setImageURI(uri);
       // Picasso.with().load(buyInfo.imagePath).into(holder.img);
        try {
             new Thread(new Runnable() {
                @Override
                public void run() {
                    try  {
                        URL url = new URL(buyInfo.imagePath);
                        Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        holder.img.setImageBitmap(image);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch(Exception e) {
            System.out.println(e);
        }
        holder.name.setText("Name:"+buyInfo.name);
        holder.price.setText("Price:"+buyInfo.price);
        holder.quantity.setText("Quantity:"+buyInfo.quantity);
        holder.mobileno.setText("Mobile No:"+buyInfo.mobileno);
        holder.email.setText(buyInfo.email);
        holder.address.setText("Address:"+buyInfo.address);
    }
    @Override
    public int getItemCount()
    {
        return l.size();
    }


}
