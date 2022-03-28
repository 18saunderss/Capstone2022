package com.capstone2022;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Recipe> recipeArrayList;

    public MyAdapter(Context context, ArrayList<Recipe> recipeArrayList) {
        this.context = context;
        this.recipeArrayList = recipeArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.recipecard,parent, false);

        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        Recipe recipe = recipeArrayList.get(position);

        holder.recipeName.setText(recipe.Title);
    }

    @Override
    public int getItemCount() {
        return recipeArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView recipeName;

        public MyViewHolder(@NonNull View recipeView) {
            super(recipeView);
            recipeName = itemView.findViewById(R.id.recipeName);
        }
    }
}
