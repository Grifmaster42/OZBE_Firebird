package com.bdn.ozbe;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bdn.ozbe.databinding.FragmentUserBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UserFragment extends Fragment {

    private FragmentUserBinding binding;
    boolean edit = false;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        binding = FragmentUserBinding.inflate(inflater, container, false);

        MainActivity mainActivity = (MainActivity) getActivity();
        binding.nameProfile.setFocusableInTouchMode(false);
        binding.phoneProfile.setFocusableInTouchMode(false);
        binding.countryProfile.setFocusableInTouchMode(false);
        binding.textView3.setFocusableInTouchMode(false);
        binding.textView4.setFocusableInTouchMode(false);

        assert mainActivity != null;
        binding.nameProfile.setText(mainActivity.getCurrent().getRaumID());
        binding.phoneProfile.setText(mainActivity.getCurrent().getStuhle());
        binding.countryProfile.setText(mainActivity.getCurrent().getTische());
        binding.textView3.setText(mainActivity.getCurrent().getAustattung());
        binding.textView4.setText(mainActivity.getCurrent().getMangel());
        binding.profileImage.setImageResource(R.drawable.h);

        FloatingActionButton b_edit = binding.floatingActionButtonEdit;
        b_edit.setOnClickListener(view -> {
            if (!edit) {

                binding.floatingActionButtonEdit.setImageResource(R.drawable.ic_baseline_save_24);
                binding.phoneProfile.setFocusableInTouchMode(true);
                binding.countryProfile.setFocusableInTouchMode(true);
                binding.textView3.setFocusableInTouchMode(true);
                binding.textView4.setFocusableInTouchMode(true);
                binding.phoneProfile.setBackgroundColor(getResources().getColor(R.color.light_grey));
                binding.countryProfile.setBackgroundColor(getResources().getColor(R.color.light_grey));
                binding.textView3.setBackgroundColor(getResources().getColor(R.color.light_grey));
                binding.textView4.setBackgroundColor(getResources().getColor(R.color.light_grey));
                edit = true;
            } else {
                User cur = new User(binding.nameProfile.getText().toString(),
                                    binding.phoneProfile.getText().toString(),
                                    binding.countryProfile.getText().toString(),
                                    binding.textView3.getText().toString(),
                                    binding.textView4.getText().toString(),
                                    String.valueOf(R.drawable.h));
                mainActivity.editUser(cur);
                mainActivity.writeDB();
                mainActivity.setStartup(true);
                NavHostFragment.findNavController(UserFragment.this)
                            .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
        return binding.getRoot();

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