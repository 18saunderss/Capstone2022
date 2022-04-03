package com.capstone2022;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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


    //TODO: Make cards clickable within the Adapter's onBindViewHolder method
    //https://stackoverflow.com/questions/37888530/make-recycler-view-clickable-inside-a-card-view
    //Plan: set onclicklisteners within the viewholder method
    //  Then, pass the recipe title to the GetRecipeActivity
    //  Finally, within the GetRecipeActivity, find the recipe by title and display recipe.
    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        Recipe recipe = recipeArrayList.get(position);
        CharSequence recipeNameText = holder.recipeName.getText();
        holder.recipeName.setText(recipe.Title);

        holder.recipeName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Pass recipeName to GetRecipeActivity and display recipe
                //String recipeName = itemView.findViewById(R.id.recipeName);
                //GetRecipeActivity.DisplayRecipeFromCardClick((String) recipeNameText);
                Intent myIntent = new Intent(context, GetRecipeActivity.class);
                //context.startActivity(new Intent(getActivity(), GetRecipeActivity.class));
                //context.startActivity(GetRecipeActivity.class);
                v.getContext().startActivity(myIntent);
            }


        });
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
