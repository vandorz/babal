package com.m2dl.cracotte.babal.scores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.m2dl.cracotte.babal.R;

public class ScoresAdapter extends RecyclerView.Adapter<ScoresAdapter.ViewHolder> {
    private final String[] localDataSetPlayerName;
    private final String[] localDataSetPlayerScore;

    public ScoresAdapter(String[] dataSetPlayerName, String[] dataSetPlayerScore) {
        this.localDataSetPlayerName = dataSetPlayerName;
        this.localDataSetPlayerScore = dataSetPlayerScore;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_line_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String positionString = "" + (position + 1);
        holder.getTextViewPosition().setText(positionString);
        holder.getTextViewPlayerName().setText(localDataSetPlayerName[position]);
        holder.getTextViewPlayerScore().setText(localDataSetPlayerScore[position]);
    }

    @Override
    public int getItemCount() {
        return localDataSetPlayerName.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewPosition;
        private final TextView textViewPlayerName;
        private final TextView textViewPlayerScore;

        public ViewHolder(View view) {
            super(view);
            textViewPosition = view.findViewById(R.id.scoreLine_textView_position);
            textViewPlayerName = view.findViewById(R.id.scoreLine_textView_nomJoueur);
            textViewPlayerScore = view.findViewById(R.id.scoreLine_textView_scoreJoueur);
        }

        public TextView getTextViewPosition() {
            return this.textViewPosition;
        }

        public TextView getTextViewPlayerName() {
            return this.textViewPlayerName;
        }

        public TextView getTextViewPlayerScore() {
            return this.textViewPlayerScore;
        }
    }
}
