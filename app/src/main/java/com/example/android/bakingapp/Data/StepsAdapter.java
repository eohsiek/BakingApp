package com.example.android.bakingapp.Data;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipeFragment;
import com.example.android.bakingapp.databinding.StepsRecyclerViewBinding;

public class StepsAdapter  extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    private Steps[] steps;
    private StepsAdapterOnClickHandler mClickHandler;

    public interface StepsAdapterOnClickHandler {
        void onClick(Steps step, int position);
    }

    public StepsAdapter(RecipeFragment clickHandler, Steps[] steps) {
        this.steps = steps;
        mClickHandler = clickHandler;
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private StepsRecyclerViewBinding binding;

        public StepsViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
            view.setOnClickListener(this);
        }

        public void bind(Steps step) {
            binding.setStep(step);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Steps step = steps[adapterPosition];
            mClickHandler.onClick(step, adapterPosition);
        }
    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View statusContainer = inflater.inflate(R.layout.steps_recycler_view, parent, false);

        return new StepsViewHolder(statusContainer);
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {
        Steps status = steps[position];
        holder.bind(status);
    }

    @Override
    public int getItemCount() {
        return steps.length;
    }
}
