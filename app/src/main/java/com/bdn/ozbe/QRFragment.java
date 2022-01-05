package com.bdn.ozbe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bdn.ozbe.databinding.FragmentQRBinding;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QRFragment} factory method to
 * create an instance of this fragment.
 */
public class QRFragment extends Fragment {

    private FragmentQRBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentQRBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        MainActivity mainActivity = (MainActivity) getActivity();
        CodeScannerView scannerView = binding.scannerView;
        CodeScanner mCodeScanner = new CodeScanner(requireContext(), scannerView);
        mCodeScanner.setDecodeCallback(result -> requireActivity().runOnUiThread(() -> {
            assert mainActivity != null;
            mainActivity.setResult(result.getText());
            NavHostFragment.findNavController(QRFragment.this)
                    .navigate(R.id.action_QRFragment_to_FirstFragment);
        }));
        mCodeScanner.startPreview();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}