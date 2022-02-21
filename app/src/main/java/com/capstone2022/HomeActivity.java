package com.capstone2022;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
          RecyclerView recycleView;
          RecyclerViewAdapter myAdapter;

          List<Recipes> recipes1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recipes1 = new ArrayList<>();
        recipes1.add(new Recipes("Chicken Roll", "200 gm chopped into cubes chicken" +
                "1 medium chopped tomato" +
                "1/2 pinch red chilli powder" +
                "2 tablespoon vegetable oil" +
                "1/2 cut into strips cucumber" +
                "1/2 tablespoon chopped coriander leaves" +
                "1 large thinly sliced onion" +
                "2 medium chopped green chilli" +
                "2 pinches garam masala powder" +
                "1 lemon wedges" +
                "1 teaspoon tomato ketchup" +
                "1 tablespoon green chilli sauce", "Method",
                "Chicken Roll is a delectable North Indian recipe made using all purpose flour, stir-fried chicken, yoghurt and a variety of vegetables rolled into paranthas. On days you do not want to prepare an elaborate meal, this delectable dish can be a saviour. Rolls are quite popular across India and they are often a favourite evening snack. Egg Roll, Kathi Kebab Roll, Mutton Roll, Paneer Roll, Potato Roll and even Fish Roll are among its many variations. This easy roll recipe is made using chicken and has the unforgettable aroma of Indian spices. You can also utilize your leftover parathas and chapatis in making this recipe. If you do not like to use all-purpose flour or maida, you can make it with whole wheat flour too. In fact, it can be made even with leftover chicken. All you have to ensure is that you use the right amount of spices so that the flavour is not lost. Easy to pack and carry, you can also take it to office or prepare it for picnics and day trips. A must try roll recipe for all chicken lovers!\n", R.drawable.ic_launcher_foreground));
        RecyclerView recyclerview = (RecyclerView) findViewById(R.id.recyclerView_id);

        myAdapter = new RecyclerViewAdapter(this, recipes1);

        recyclerview.setLayoutManager(new GridLayoutManager(this, 1));

        recyclerview.setAdapter(myAdapter);
       }

    

    public void logout(View view)
    {
        FirebaseAuth.getInstance().signOut();
        startActivity((new Intent(getApplicationContext(),LoginActivity.class)));
        finish();
    }
}