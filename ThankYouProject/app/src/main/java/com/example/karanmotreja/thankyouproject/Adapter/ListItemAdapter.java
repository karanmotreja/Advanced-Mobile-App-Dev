package com.example.karanmotreja.thankyouproject.Adapter;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karanmotreja.thankyouproject.MainActivity;
import com.example.karanmotreja.thankyouproject.Model.Gift;
import com.example.karanmotreja.thankyouproject.R;
import com.example.karanmotreja.thankyouproject.messageActivity;

import java.util.List;

class ListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener
{

    ItemClickListener itemClickListener;
    TextView name_title, gift_title;
    ImageView messageButton;





    public ListItemViewHolder(@NonNull final View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);

        name_title = (TextView)itemView.findViewById(R.id.name_title);
        gift_title = (TextView)itemView.findViewById(R.id.gift_title);
        messageButton = (ImageView)itemView.findViewById(R.id.messageButton);


        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), messageActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }





    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        menu.setHeaderTitle("Select the Action");
        menu.add(0,0,getAdapterPosition(),"Delete");
    }

    @Override
    public void onClick(View v) {
       itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}

public class ListItemAdapter extends RecyclerView.Adapter<ListItemViewHolder>
{

    MainActivity mainActivity;
    List<Gift> gifts;

    public ListItemAdapter(MainActivity mainActivity, List<Gift> gifts) {
        this.mainActivity = mainActivity;
        this.gifts = gifts;
    }

    @NonNull
    @Override
    public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mainActivity.getBaseContext());
        View view = inflater.inflate(R.layout.list_item, viewGroup, false);

        return new ListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemViewHolder holder, int position) {

        holder.name_title.setText(gifts.get(position).getName());
        holder.gift_title.setText(gifts.get(position).getGift());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick)
            {

                mainActivity.name.setText(gifts.get(position).getName());
                mainActivity.gift.setText(gifts.get(position).getGift());

                mainActivity.isUpdate = true;
                mainActivity.idUpdate = gifts.get(position).getId();

            }
        });



    }

    @Override
    public int getItemCount() {
        return gifts.size();
    }
}
