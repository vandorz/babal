package com.m2dl.cracotte.babal.scores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.m2dl.cracotte.babal.R;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder> {
    private String[] localDataSetNomJoueur;
    private String[] localDataSetScoreJoueur;

    public ScoreAdapter(String[] dataSetNomJoueur, String[] dataSetScoreJoueur){
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
        holder.getTextViewNomJoueur().setText(localDataSetNomJoueur[position]);
        holder.getTextViewScoreJoueur().setText(localDataSetScoreJoueur[position]);
    }

    @Override
    public int getItemCount() {
        return localDataSetNomJoueur.length;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView textViewNomJoueur;
        private final TextView textViewScoreJoueur;

        public ViewHolder(View view) {
            super(view);

            textViewNomJoueur = view.findViewById(R.id.scoreLine_textView_nomJoueur);
            textViewScoreJoueur = view.findViewById(R.id.scoreLine_textView_scoreJoueur);

        }

        public TextView getTextViewNomJoueur(){
            return this.textViewNomJoueur;
        }

        public TextView getTextViewScoreJoueur(){
            return this.textViewScoreJoueur;
        }
    }
}
