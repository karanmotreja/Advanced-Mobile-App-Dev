package com.example.karanmotreja.project2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Intent;

import java.util.ArrayList;

public class TYAdapter extends RecyclerView.Adapter<TYAdapter.MyViewHolder>
{

    Context context;
    ArrayList<MyTY> myTY;

    public TYAdapter(Context c, ArrayList<MyTY> p)
    {
        context = c;
        myTY = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.thank_you, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i)
    {
        myViewHolder.nameText.setText(myTY.get(i).getNameText());
        myViewHolder.giftText.setText(myTY.get(i).getGiftText());
        myViewHolder.confirmationText.setText(myTY.get(i).getConfirmationText());
        //myViewHolder.keyText.setText(myTY.get(i).getKeyText());

        final String getNameText = myTY.get(i).getNameText();
        final String getGiftText = myTY.get(i).getGiftText();
        final String getConfirmationText = myTY.get(i).getConfirmationText();

        final String getKeyText = myTY.get(i).getKeyText();




        //edit entries
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent update = new Intent(context, EditActivity.class);

                update.putExtra("nameText", getNameText);
                update.putExtra("giftText", getGiftText);
                update.putExtra("confirmationText", getConfirmationText);
                update.putExtra("keyText", getKeyText);

                context.startActivity(update);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myTY.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView nameText, giftText, confirmationText, keyText;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            nameText = (TextView) itemView.findViewById(R.id.nameText);
            giftText = (TextView) itemView.findViewById(R.id.giftText);
            confirmationText = (TextView) itemView.findViewById(R.id.confirmationText);


        }
    }
}
