package com.bdn.ozbe;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bdn.ozbe.databinding.ActivityMainBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private String result="";
    private User current = new User();
    private final ArrayList<User> userArrayList = new ArrayList<>();
    private DatabaseReference myRef;
    private boolean startup = true;

    public boolean isStartup() {
        return startup;
    }

    public void setStartup(boolean startup) {
        this.startup = startup;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void reqMulPerm(){
        String[] permissions ={
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_MEDIA_LOCATION
        };

        ActivityCompat.requestPermissions(this,permissions,1);
    }

    public User getUser(int index) {
        return userArrayList.get(index);
    }

    public void addUser(User content){
        userArrayList.add(content);
        int after = userArrayList.size();
        Log.d("Raum",
                "Raum Informationen" + "\n" +
                "Raum " + content.getRaumID() + " hinzugefügt" + "\n" +
                "Anzahl: "+after);
    }

    public void deleteUser(int index) {
        userArrayList.remove(index);
    }

    public void editUser(User newUser){
        int position = -1;
        for (int i = 0; i < userArrayList.size(); i++) {
            if (getUser(i).getRaumID().equals(newUser.getRaumID())) {
                position = i;
            }
        }
        if (position == -1) {
            return;
        }
        getUser(position).setStuhle(newUser.getStuhle());
        getUser(position).setTische(newUser.getTische());
        getUser(position).setAustattung(newUser.getAustattung());
        getUser(position).setMangel(newUser.getMangel());
        getUser(position).setImageId(newUser.getImageId());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sort() {
        Collections.sort(userArrayList,(Comparator.comparing(User::getRaumID)));
    }

    public void writeDB() {
        for(int i = 0; i<userArrayList.size();i++) {
            int t = i+1;
            myRef.child(Integer.toString(t)).setValue(getUser(i));
            Log.d("DEBUG","Anzahl: "+userArrayList.size());
            Log.d("DEBUG","Raum: "+getUser(i).getRaumID());
        }
    }

    public void updateDB() {
        int size = userArrayList.size() + 1;
        myRef.child(""+size+"").removeValue();
    }

    public ArrayList<User> getUserArrayList() {
        return userArrayList;
    }

    public DatabaseReference getMyRef() {
        return myRef;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public User getCurrent() {
        return current;
    }

    public void setCurrent(User current) {
        this.current = current;
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reqMulPerm();
        myRef = FirebaseDatabase.getInstance("https://ozbe-swe-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

        com.bdn.ozbe.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}