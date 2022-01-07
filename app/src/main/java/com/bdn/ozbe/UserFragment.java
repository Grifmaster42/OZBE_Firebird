package com.bdn.ozbe;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.bdn.ozbe.databinding.FragmentUserBinding;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UserFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private FragmentUserBinding binding;
    private int img;
    private final int[] img_res = {R.drawable.h,R.drawable.s};
    private boolean edit = false;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        binding = FragmentUserBinding.inflate(inflater, container, false);

        Spinner spin = binding.profileImage;
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getContext(), img_res);
        spin.setAdapter(spinnerAdapter);

        MainActivity mainActivity = (MainActivity) getActivity();
        binding.nameProfile.setFocusableInTouchMode(false);
        binding.phoneProfile.setFocusableInTouchMode(false);
        binding.countryProfile.setFocusableInTouchMode(false);
        binding.textView3.setFocusableInTouchMode(false);
        binding.textView4.setFocusableInTouchMode(false);
        spin.getSelectedView();
        spin.setEnabled(false);

        spin.setOnItemSelectedListener(this);

        assert mainActivity != null;
        binding.nameProfile.setText(mainActivity.getCurrent().getRaumID());
        binding.phoneProfile.setText(mainActivity.getCurrent().getStuhle());
        binding.countryProfile.setText(mainActivity.getCurrent().getTische());
        binding.textView3.setText(mainActivity.getCurrent().getAustattung());
        binding.textView4.setText(mainActivity.getCurrent().getMangel());
        for (int i = 0; i < img_res.length; i++) {
            if (mainActivity.getCurrent().getImageId() == img_res[i]) {
                spin.setSelection(i);
            }
        }

        FloatingActionButton b_edit = binding.floatingActionButtonEdit;
        b_edit.setOnClickListener(view -> {
            if (!edit) {

                binding.floatingActionButtonEdit.setImageResource(R.drawable.ic_baseline_save_24);
                binding.phoneProfile.setFocusableInTouchMode(true);
                binding.countryProfile.setFocusableInTouchMode(true);
                binding.textView3.setFocusableInTouchMode(true);
                binding.textView4.setFocusableInTouchMode(true);
                spin.setEnabled(true);

                binding.phoneProfile.setBackgroundColor(getResources().getColor(R.color.light_grey));
                binding.countryProfile.setBackgroundColor(getResources().getColor(R.color.light_grey));
                binding.textView3.setBackgroundColor(getResources().getColor(R.color.light_grey));
                binding.textView4.setBackgroundColor(getResources().getColor(R.color.light_grey));
                edit = true;
            } else {
                String stuhle = binding.phoneProfile.getText().toString().replace(" ","").replace("-","");
                String tische = binding.countryProfile.getText().toString().replace(" ","").replace("-","");
                if (stuhle.equals("")) {
                    stuhle = "0";
                }
                if (tische.equals("")){
                    tische = "0";
                }
                User cur = new User(binding.nameProfile.getText().toString(),
                                    stuhle,
                                    tische,
                                    binding.textView3.getText().toString(),
                                    binding.textView4.getText().toString(),
                                    img);
                mainActivity.editUser(cur);
                mainActivity.writeDB();
                mainActivity.setStartup(true);
                NavHostFragment.findNavController(UserFragment.this)
                            .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
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

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}