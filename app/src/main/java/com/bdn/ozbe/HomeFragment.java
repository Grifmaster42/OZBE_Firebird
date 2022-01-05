package com.bdn.ozbe;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bdn.ozbe.databinding.FragmentFirstBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentFirstBinding binding;
    private ListAdapter tlistAdapter;
    private ListAdapter listAdapter;
    private final ArrayList<User> tuserArrayList = new ArrayList<>();


    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        MainActivity mainActivity = (MainActivity) getActivity();
        assert mainActivity != null;
        tlistAdapter = new ListAdapter(getContext(), tuserArrayList);
        listAdapter = new ListAdapter(getContext(), mainActivity.getUserArrayList());


        mainActivity.getMyRef().addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int elem = (int) dataSnapshot.getChildrenCount();
                mainActivity.getUserArrayList().clear();
                for (int i = 1; i <= elem; i++) {
                    DataSnapshot task = dataSnapshot.child("" + i + "");
                    if (!task.exists()) {
                        Log.e("Firebase", "Error getting data");
                    } else {
                        User tuser = new User(
                                String.valueOf(task.child("raumID").getValue()),
                                String.valueOf(task.child("stuhle").getValue()),
                                String.valueOf(task.child("tische").getValue()),
                                String.valueOf(task.child("austattung").getValue()),
                                String.valueOf(task.child("mangel").getValue()),
                                Integer.parseInt(String.valueOf(task.child("imageId").getValue())));
                        mainActivity.addUser(tuser);
                    }
                }
                mainActivity.sort();
                listAdapter.notifyDataSetChanged();
                binding.listview.setAdapter(listAdapter);
                if (mainActivity.isStartup()) {
                    mainActivity.setStartup(false);
                }  //Snackbar.make(mainActivity.binding.getRoot(), "Datenbank aktualisiert", Snackbar.LENGTH_LONG).setAction("Action", null).show();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.e("Firebird", "Failed to read value.", error.toException());
            }
        });

        EditText search = binding.searchText;
        binding.listview.setAdapter(listAdapter);
        binding.listview.setClickable(true);
        search.setText(mainActivity.getResult());


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tuserArrayList.clear();
                for (int x=0;x < mainActivity.getUserArrayList().size();x++){
                    String stext = search.getText().toString().toUpperCase();
                    if (mainActivity.getUser(x).getRaumID().contains(stext)) {
                        System.out.println(mainActivity.getUser(x).getRaumID());
                        tuserArrayList.add(mainActivity.getUser(x));
                        System.out.println(tuserArrayList.size());
                    }
                    listAdapter.notifyDataSetChanged();
                    binding.listview.setAdapter(tlistAdapter);
                    if (stext.equals("")) {
                        listAdapter.notifyDataSetChanged();
                        binding.listview.setAdapter(listAdapter);
                    }
                }
                mainActivity.setResult(search.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.listview.setOnItemLongClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(getContext())
                    //set icon
                    .setIcon(android.R.drawable.ic_menu_delete)
                    //set title
                    .setTitle("Möchten Sie den Raum \"" + mainActivity.getUser(position).getRaumID() + "\" wirklich löschen?")
                    //set message
                    .setMessage("Damit werden die Daten endgültig verloren gegangen.")
                    //set positive button
                    .setPositiveButton("Ja", (dialogInterface, i) -> {
                        mainActivity.deleteUser(position);
                        listAdapter.notifyDataSetChanged();
                        binding.listview.setAdapter(listAdapter);
                        mainActivity.writeDB();
                        mainActivity.updateDB();
                    })
                    .setNegativeButton("Nein", (dialogInterface, i) -> {
                    })
                    .show();
            return true;
        });

        binding.listview.setOnItemClickListener((parent, view, position, id) -> {
            mainActivity.setCurrent(mainActivity.getUser(position));
            NavHostFragment.findNavController(HomeFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment);
        });

        binding.imageButton.setOnClickListener(view -> NavHostFragment.findNavController(HomeFragment.this)
                .navigate(R.id.action_FirstFragment_to_QRFragment));

        binding.floatingActionButtonAdd.setOnClickListener(view -> NavHostFragment.findNavController(HomeFragment.this)
                .navigate(R.id.action_FirstFragment_to_NewUserFragment));

        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}