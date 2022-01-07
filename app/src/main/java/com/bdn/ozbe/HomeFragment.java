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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bdn.ozbe.databinding.FragmentFirstBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private FragmentFirstBinding binding;
    private ListAdapter tlistAdapter;
    private ListAdapter listAdapter;
    private ArrayList<User> tuserArrayList = new ArrayList<>();
    private String curFilter = "Raum";
    private final String[] filter_array = {"Raum","Stühle","Tische","Austattung","Mängel"};
    MainActivity mainActivity;
    private EditText search;

    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mainActivity = (MainActivity) getActivity();
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

        search = binding.searchText;
        binding.listview.setAdapter(listAdapter);
        binding.listview.setClickable(true);
        search.setText(mainActivity.getResult());
        boolean found = false;
        if (!mainActivity.getResult().equals("")){
            for (int i = 0; i < mainActivity.getUserArrayList().size(); i++) {
                if (mainActivity.getUser(i).getRaumID().equals(mainActivity.getResult()))
                {
                    mainActivity.setCurrent(mainActivity.getUser(i));
                    mainActivity.setResult("");
                    NavHostFragment.findNavController(HomeFragment.this)
                            .navigate(R.id.action_FirstFragment_to_SecondFragment);
                    found = true;
                }
            }
            if (!found){
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.action_FirstFragment_to_NewUserFragment);
            }
        }
        search.setText("");

        Spinner filter = binding.filter;
        FilterAdapter filterAdapter = new FilterAdapter(getContext(), filter_array);
        filter.setAdapter(filterAdapter);
        filter.setOnItemSelectedListener(this);
        filter.setSelection(0);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tuserArrayList.clear();
                String stext = search.getText().toString();
                String ftext = "";
                if (stext.equals("")) {
                    listAdapter.notifyDataSetChanged();
                    binding.listview.setAdapter(listAdapter);
                } else {
                for (int x=0;x < mainActivity.getUserArrayList().size();x++){
                    switch (curFilter) {
                        case "Stühle":
                            ftext = mainActivity.getUser(x).getStuhle();
                            if(!stext.replaceAll("[^0-9]+", "").equals("")) {
                                if (Integer.parseInt(ftext) >= Integer.parseInt(stext.replaceAll("[^0-9]+", ""))) {
                                    System.out.println(mainActivity.getUser(x).getRaumID());
                                    tuserArrayList.add(mainActivity.getUser(x));
                                    System.out.println(tuserArrayList.size());
                                }

                            }
                            break;
                        case "Tische":
                            ftext = mainActivity.getUser(x).getTische();
                            if(!stext.replaceAll("[^0-9]+", "").equals("")) {
                                if (Integer.parseInt(ftext) >= Integer.parseInt(stext.replaceAll("[^0-9]+", ""))) {
                                    System.out.println(mainActivity.getUser(x).getRaumID());
                                    tuserArrayList.add(mainActivity.getUser(x));
                                    System.out.println(tuserArrayList.size());
                                }
                            }
                            break;
                        case "Austattung":
                            ftext = mainActivity.getUser(x).getAustattung();

                            if (ftext.toUpperCase().contains(stext.toUpperCase())) {
                                System.out.println(mainActivity.getUser(x).getRaumID());
                                tuserArrayList.add(mainActivity.getUser(x));
                                System.out.println(tuserArrayList.size());
                            }
                            break;
                        case "Mängel":
                            ftext = mainActivity.getUser(x).getMangel();

                            if (ftext.toUpperCase().contains(stext.toUpperCase())) {
                                System.out.println(mainActivity.getUser(x).getRaumID());
                                tuserArrayList.add(mainActivity.getUser(x));
                                System.out.println(tuserArrayList.size());
                            }
                            break;
                        default:
                            ftext = mainActivity.getUser(x).getRaumID();

                            if (ftext.toUpperCase().contains(stext.toUpperCase())) {
                                System.out.println(mainActivity.getUser(x).getRaumID());
                                tuserArrayList.add(mainActivity.getUser(x));
                                System.out.println(tuserArrayList.size());
                            }
                            break;
                    }
                    listAdapter.notifyDataSetChanged();
                    binding.listview.setAdapter(tlistAdapter);

                }
                mainActivity.setResult(search.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });



        binding.listview.setOnItemLongClickListener((parent, view, position, id) -> {
            int index = -1;
            for (int x = 0; x < mainActivity.getUserArrayList().size(); x++) {
                if (tuserArrayList.get(position).getRaumID().equals(mainActivity.getUser(x).getRaumID())) {
                    index = x;
                }
            }
            int finalIndex = index;
            new AlertDialog.Builder(getContext())
                    //set icon
                    .setIcon(android.R.drawable.ic_menu_delete)
                    //set title
                    .setTitle("Möchten Sie den Raum \"" + mainActivity.getUser(index).getRaumID() + "\" wirklich löschen?")
                    //set message
                    .setMessage("Damit werden die Daten endgültig verloren gegangen.")
                    //set positive button
                    .setPositiveButton("Ja", (dialogInterface, i) -> {
                        if (tuserArrayList.isEmpty())
                        {
                            tuserArrayList = new ArrayList<>(mainActivity.getUserArrayList());
                        }
                        mainActivity.deleteUser(finalIndex);
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
            if (tuserArrayList.isEmpty()) {
                tuserArrayList = new ArrayList<>(mainActivity.getUserArrayList());
            }
            mainActivity.setCurrent(tuserArrayList.get(position));
            NavHostFragment.findNavController(HomeFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment);
            mainActivity.setResult("");
            search.setText("");
            filter.setSelection(0);

            });

        binding.imageButton.setOnClickListener(view -> {
            filter.setSelection(0);
            NavHostFragment.findNavController(HomeFragment.this)
                .navigate(R.id.action_FirstFragment_to_QRFragment);
        });

        binding.floatingActionButtonAdd.setOnClickListener(view -> {
            filter.setSelection(0);
            NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_FirstFragment_to_NewUserFragment);
        });


        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        curFilter = filter_array[i];
        tuserArrayList.clear();
        String stext = search.getText().toString();
        String ftext = "";
        for (int x=0;x < mainActivity.getUserArrayList().size();x++){
            switch (curFilter) {
                case "Stühle":
                    ftext = mainActivity.getUser(x).getStuhle();
                    break;
                case "Tische":
                    ftext = mainActivity.getUser(x).getTische();
                    break;
                case "Austattung":
                    ftext = mainActivity.getUser(x).getAustattung();
                    break;
                case "Mängel":
                    ftext = mainActivity.getUser(x).getMangel();
                    break;
                default:
                    ftext = mainActivity.getUser(x).getRaumID();
                    break;
            }

            if (ftext.toUpperCase().contains(stext.toUpperCase())) {
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
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}