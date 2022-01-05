package com.bdn.ozbe;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bdn.ozbe.databinding.FragmentNewUserBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;


public class NewUserFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private int img;
    private final int[] img_res = {R.drawable.h,R.drawable.s};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FragmentNewUserBinding binding = FragmentNewUserBinding.inflate(inflater, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();

        Spinner spinner = binding.newProfileImage;
        spinner.setOnItemSelectedListener(this);

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getContext(), img_res);
        spinner.setAdapter(spinnerAdapter);

        TextInputLayout rl = binding.newRaumLayout;
        TextInputLayout sl = binding.newStuhleLayout;
        TextInputLayout tl = binding.newTischeLayout;
        TextInputLayout al = binding.newAustattungLayout;
        TextInputLayout ml = binding.newMangelLayout;
        EditText rt = binding.newRaum;

        if (mainActivity != null) {
            rt.setText(mainActivity.getResult());
        }
        Objects.requireNonNull(rl.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 1 && s.length() < 2) {
                    rl.setError("Der Raum muss mindestens 2 Zeichen haben");
                    rl.setErrorEnabled(true);
                } else {
                    rl.setErrorEnabled(false);
                } }
        });

        FloatingActionButton save = binding.floatingActionButtonSave;
        save.setOnClickListener(v -> {
            if (rl.isErrorEnabled()) {
                Toast.makeText(getContext(), "Bitte geben Sie einen gültigen Raum ein.",Toast.LENGTH_LONG).show();
                return;
            } else if (rl.getEditText().getText().length() == 0) {
                rl.setError("Der Raum darf nicht leer sein");
                return;
            }
            if (Objects.requireNonNull(sl.getEditText()).getText().length() == 0){
                sl.setError("Geben Sie eine Anzahl an Stühlen an");
                return;
            }
            if (Objects.requireNonNull(tl.getEditText()).getText().length() == 0){
                tl.setError("Geben Sie eine Anzahl an Tischen an");
                return;
            }


            assert mainActivity != null;
            mainActivity.addUser(new User(rl.getEditText().getText().toString()
                    ,sl.getEditText().getText().toString()
                    ,tl.getEditText().getText().toString()
                    ,Objects.requireNonNull(al.getEditText()).getText().toString()
                    ,Objects.requireNonNull(ml.getEditText()).getText().toString()
                    ,img));
            mainActivity.setStartup(true);
            mainActivity.writeDB();
            NavHostFragment.findNavController(NewUserFragment.this)
                    .navigate(R.id.action_NewUserFragment_to_HomeFragment);
        });

        return binding.getRoot();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        img = img_res[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}