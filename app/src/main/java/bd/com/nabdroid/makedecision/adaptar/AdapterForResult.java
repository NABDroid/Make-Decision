package bd.com.nabdroid.makedecision.adaptar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bd.com.nabdroid.makedecision.R;
import bd.com.nabdroid.makedecision.pojo.Vote;

public class AdapterForResult extends RecyclerView.Adapter<AdapterForResult.ViewHolder> {
    private ArrayList<Vote> votes;
    private Context context;

    public AdapterForResult(ArrayList<Vote> votes, Context context) {
        this.votes = votes;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_result, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int yesVote, noVote;
        String peopleCount;
        Vote vote = votes.get(position);

        yesVote = vote.getYesVote();
        noVote = vote.getNoVote();
        if (yesVote>noVote){
            holder.resultSummaryTV.setText("Passed");

        }

        else if (yesVote<noVote){
            holder.resultSummaryTV.setText("Failed");
            holder.resultSummaryTV.setTextColor(Color.RED);

        }
        else {
            holder.resultSummaryTV.setText("Draw");
            holder.resultSummaryTV.setTextColor(Color.BLACK);
        }

        holder.peopleCountTV.setText(""+yesVote+" people agreed and "+noVote+" people disagreed");
        holder.topicTV.setText(vote.getTopic());
        holder.creatorNameTV.setText(vote.getCreatorName());


    }

    @Override
    public int getItemCount() {
        return votes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView creatorNameTV, topicTV, resultSummaryTV, peopleCountTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            creatorNameTV = itemView.findViewById(R.id.userNameSRTV);
            topicTV = itemView.findViewById(R.id.topicSRTV);
            resultSummaryTV = itemView.findViewById(R.id.resultSummerySRTV);
            peopleCountTV = itemView.findViewById(R.id.peopleCountSRTV);

        }
    }
}

