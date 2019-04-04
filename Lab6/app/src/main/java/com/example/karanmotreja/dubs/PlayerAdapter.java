package com.example.karanmotreja.dubs;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView playerTextView;


        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            playerTextView = itemView.findViewById(R.id.textView);
            playerTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onListItemClick(getAdapterPosition());
        }

    }

    private List<Player> mPlayer;

    public PlayerAdapter(List<Player> dub, ListItemClickListener playerClickListener)
    {
        mPlayer = dub;
        itemClickListener = playerClickListener;
    }


    @NonNull
    @Override
    public PlayerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View playerView = inflater.inflate(R.layout.list_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(playerView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerAdapter.ViewHolder viewHolder, int i) {
        Player player = mPlayer.get(i);
        viewHolder.playerTextView.setText(player.getName());
    }

    @Override
    public int getItemCount() {
        return mPlayer.size();
    }

    public interface ListItemClickListener{
        void onListItemClick(int position);
    }

    ListItemClickListener itemClickListener;


    }