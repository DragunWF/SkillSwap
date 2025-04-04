package com.example.dragun.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dragun.R;
import com.example.dragun.data.Request;
import com.example.dragun.helpers.Utils;
import com.example.dragun.services.RequestService;

import java.util.List;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.ViewHolder> {

    private List<Request> localDataSet;
    private Context context;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView usernameText, studentEmailText, sessionModeText, skillCategoryText, featuredSkillText;
        private final Button doneBtn;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

           usernameText = view.findViewById(R.id.usernameText);
           studentEmailText = view.findViewById(R.id.emailText);
           sessionModeText = view.findViewById(R.id.sessionModeText);
           skillCategoryText = view.findViewById(R.id.skillCategoryText);
           featuredSkillText = view.findViewById(R.id.featuredSkillText);
           doneBtn = view.findViewById(R.id.doneBtn);
        }

        public TextView getUsernameText() {
            return usernameText;
        }

        public TextView getStudentEmailText() {
            return studentEmailText;
        }

        public TextView getSessionModeText() {
            return sessionModeText;
        }

        public TextView getSkillCategoryText() {
            return skillCategoryText;
        }

        public TextView getFeaturedSkillText() {
            return featuredSkillText;
        }

        public Button getDoneBtn() {
            return doneBtn;
        }
    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView
     */
    public RequestsAdapter(List<Request> dataSet, Context context) {
        localDataSet = dataSet;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.request_session_card, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        // viewHolder.getTextView().setText(localDataSet[position]);
        Request request = localDataSet.get(position);
        viewHolder.getUsernameText().setText(request.getUsername());
        viewHolder.getStudentEmailText().setText(request.getStudentEmail());
        viewHolder.getSessionModeText().setText(request.getSessionMode());
        viewHolder.getSkillCategoryText().setText(request.getSkillCategory());
        viewHolder.getFeaturedSkillText().setText(request.getFeaturedSkill());
        viewHolder.getDoneBtn().setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    RequestService.delete(request.getId());
                    Utils.toast("Request has been removed from the list", context);
                    updateDataSet();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancels the dialog.
                }
            });
            builder.setTitle("Is the session done?");
            builder.setMessage("Are you sure you want to mark this session as done? This will remove the request from your requests list.");

            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    public void updateDataSet() {
        updateDataSet(RequestService.getRequests());
    }

    public void updateDataSet(List<Request> updatedSet) {
        localDataSet.clear();
        localDataSet.addAll(updatedSet);
        notifyDataSetChanged();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
