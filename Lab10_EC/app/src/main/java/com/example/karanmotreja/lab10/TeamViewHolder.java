package com.example.karanmotreja.lab10;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;



public class TeamViewHolder extends RecyclerView.ViewHolder
{
    private TextView teamName;

    public TeamViewHolder(@NonNull View itemView) {
        super(itemView);
        teamName = itemView.findViewById(R.id.teamTextView);
    }

    public void setTeamName(String name){
        teamName.setText(name);
    }
}
