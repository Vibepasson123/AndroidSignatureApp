package com.example.vivek.signature1;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.vivek.signature1.model.Customer;

import java.util.HashMap;
import java.util.Map;


public class customer_information  extends DialogFragment {
    private boolean wantToCloseDialog = false;


    private Map<String, String> mLogin;

    private Customer customer;

    public static customer_information newIntace(String storeId, Customer customer) {
        customer_information sd = new customer_information();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putParcelable("customer", customer);
        sd.setArguments(args);

        return sd;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            customer = getArguments().getParcelable("customer");
        }
    }
//    @Override
//    public void onStart()
//    {
//        super.onStart();
//        Dialog dialog = getDialog();
//        if (dialog != null)
//        {
//            int width = ViewGroup.LayoutParams.MATCH_PARENT;
//            int height = ViewGroup.LayoutParams.MATCH_PARENT;
//            dialog.getWindow().setLayout(width, height);
//        }
//    }

    @Override

    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater= getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.customer_information,null);


        TextView name;
        TextView city;
        TextView address;
        Button accet;





        accet=view.findViewById(R.id.aceept1);
        name=view.findViewById(R.id.customername);
        city = view.findViewById(R.id.customercity);
        address = view.findViewById(R.id.customeradd);

        if (customer == null) customer = new Customer();
        name.setText(customer.getName());

        city.setText(customer.getCity());
        address.setText(customer.getAddress());



        accet.setOnClickListener(v -> {
            this.dismiss();
        });

        mLogin = new HashMap<>();
        mLogin = SaveSharedPreferences.getLogin(getActivity().getApplicationContext());
        final AlertDialog.Builder builder1 = builder.setView(view);
        return builder.create();


    }

    public void closesign(Customer customer){
        FragmentManager st = getActivity().getSupportFragmentManager();
        signdialog cuDialogFragment = new signdialog();
        getActivity().finish();
    }

}