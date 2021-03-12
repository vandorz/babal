package com.m2dl.cracotte.babal.scores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.m2dl.cracotte.babal.R;

public class ScoresAdapter extends RecyclerView.Adapter<ScoresAdapter.ViewHolder> {
    private final String[] localDataSetNomJoueur;
    private final String[] localDataSetScoreJoueur;

    public ScoresAdapter(String[] dataSetNomJoueur, String[] dataSetScoreJoueur) {
        this.localDataSetNomJoueur = dataSetNomJoueur;
        this.localDataSetScoreJoueur = dataSetScoreJoueur;
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
        holder.getTextViewNomJoueur().setText(localDataSetNomJoueur[position]);
        holder.getTextViewScoreJoueur().setText(localDataSetScoreJoueur[position]);
    }

    @Override
    public int getItemCount() {
        return localDataSetNomJoueur.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewPosition;
        private final TextView textViewNomJoueur;
        private final TextView textViewScoreJoueur;

        public ViewHolder(View view) {
            super(view);
            textViewPosition = view.findViewById(R.id.scoreLine_textView_position);
            textViewNomJoueur = view.findViewById(R.id.scoreLine_textView_nomJoueur);
            textViewScoreJoueur = view.findViewById(R.id.scoreLine_textView_scoreJoueur);
        }

        public TextView getTextViewPosition() { return this.textViewPosition; }

        public TextView getTextViewNomJoueur(){
            return this.textViewNomJoueur;
        }

        public TextView getTextViewScoreJoueur(){
            return this.textViewScoreJoueur;
        }
    }
}
