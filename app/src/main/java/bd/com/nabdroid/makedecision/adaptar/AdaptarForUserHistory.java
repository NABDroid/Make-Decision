package bd.com.nabdroid.makedecision.adaptar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bd.com.nabdroid.makedecision.R;
import bd.com.nabdroid.makedecision.pojo.Vote;

public class AdaptarForUserHistory extends RecyclerView.Adapter<AdaptarForUserHistory.ViewHolder> {
    private ArrayList<Vote> votes;
    private Context context;

    public AdaptarForUserHistory(ArrayList<Vote> votes, Context context) {
        this.votes = votes;
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_user_history, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Vote vote = votes.get(position);
        holder.userNameTV.setText(vote.getCreatorName());
        holder.topicTV.setText(vote.getTopic());
        holder.resultTimeTV.setText(vote.getEndtimeString());
    }

    @Override
    public int getItemCount() {
        return votes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView userNameTV, topicTV, resultTimeTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTV = itemView.findViewById(R.id.userNameSingleHistoryProfile);
            topicTV = itemView.findViewById(R.id.singleHistoryTopic);
            resultTimeTV = itemView.findViewById(R.id.resultTimeSingleHistoryProfileTV);
        }
    }
}
