package com.example.dragun.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dragun.R;
import com.example.dragun.ViewListing;
import com.example.dragun.data.Posting;
import com.example.dragun.data.User;
import com.example.dragun.helpers.DatabaseHelper;
import com.example.dragun.helpers.Utils;
import com.example.dragun.services.UserService;

import java.util.List;

public class PostingAdapter extends RecyclerView.Adapter<PostingAdapter.ViewHolder> {

    private List<Posting> localDataSet;
    private Context context;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mentorText, featuredSkillText, skillCategoryText, estimatedLearningTimeText;
        private final ImageView viewBtn, skillImage;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            mentorText = view.findViewById(R.id.mentorText);
            featuredSkillText = view.findViewById(R.id.featuredSkillText);
            skillCategoryText = view.findViewById(R.id.skillCategoryText);
            estimatedLearningTimeText = view.findViewById(R.id.estimatedLearningTimeText);
            viewBtn = view.findViewById(R.id.viewBtn);
            skillImage = view.findViewById(R.id.skillImage);
        }

        public TextView getMentorText() {
            return mentorText;
        }

        public TextView getFeaturedSkillText() {
            return featuredSkillText;
        }

        public TextView getSkillCategoryText() {
            return skillCategoryText;
        }

        public TextView getEstimatedLearningTimeText() {
            return estimatedLearningTimeText;
        }

        public ImageView getViewBtn() {
            return viewBtn;
        }

        public ImageView getSkillImage() {
            return skillImage;
        }
    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView
     */
    public PostingAdapter(List<Posting> dataSet, Context context) {
        localDataSet = dataSet;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycle_card_listing, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Posting posting = localDataSet.get(position);
        User mentor = UserService.get(posting.getUserId());
        viewHolder.getMentorText().setText(mentor.getUsername());
        viewHolder.getFeaturedSkillText().setText(posting.getFeaturedSkill());
        viewHolder.getSkillCategoryText().setText(posting.getSkillCategory());
        viewHolder.getEstimatedLearningTimeText().setText(posting.getEstimatedLearningTime());
        viewHolder.getSkillImage().setImageResource(Utils.getImageResource(posting.getSkillCategory()));
        viewHolder.getViewBtn().setOnClickListener(v -> {
            Intent intent = new Intent(context, ViewListing.class);
            intent.putExtra(ViewListing.POSTING_ID, posting.getId());
            context.startActivity(intent);
        });
    }

    public void updateDataSet() {
        updateDataSet(DatabaseHelper.getPostingBank().getAll());
    }

    public void updateDataSet(List<Posting> postings) {
        localDataSet.clear();
        localDataSet.addAll(postings);
        notifyDataSetChanged();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
