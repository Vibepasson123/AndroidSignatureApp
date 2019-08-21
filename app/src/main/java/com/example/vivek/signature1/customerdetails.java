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


public class customerdetails  extends DialogFragment {
    private boolean wantToCloseDialog = false;


    private Map<String, String> mLogin;
    //  private String newstore_id;
    private Customer customer;

    public static customerdetails newIntace(String storeId, Customer customer) {
        customerdetails sd = new customerdetails();

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

            //   newstore_id = getArguments().getString("warehouse");
            customer = getArguments().getParcelable("customer");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater= getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.customerdetais,null);

       // signatureView = view.findViewById(R.id.signature_view);
        TextView name;
        TextView tax;
        TextView email;
        TextView mobile;
        TextView city;
        TextView address;

        email=view.findViewById(R.id.cusemail);
        name=view.findViewById(R.id.cusname);
        tax= view.findViewById(R.id.custax);
        mobile = view.findViewById(R.id.cusmobile);
        city = view.findViewById(R.id.citycus);
        address = view.findViewById(R.id.cusaddress);

        if (customer == null) customer = new Customer();
        name.setText(customer.getName());
        tax.setText(customer.getTax());
        email.setText(customer.getEmail());
        mobile.setText(customer.getMobile());
        //city.setText(customer.getName());
       // address.setText(customer.getAddress());

        mLogin = new HashMap<>();
        mLogin = SaveSharedPreferences.getLogin(getActivity().getApplicationContext());
        final AlertDialog.Builder builder1 = builder.setView(view)
                .setTitle("Customer Detail")
                .setCancelable(false)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                       // closesign(customer);
//                      startActivity(f);
                      dialog.dismiss();

                    }
                })
                .setPositiveButton("Accept", (dialog, which) -> {

          getActivity().getSupportFragmentManager().popBackStack();

                });
        return builder.create();


    }

    public void closesign(Customer customer){
        FragmentManager st = getActivity().getSupportFragmentManager();
        signdialog cuDialogFragment = new signdialog();
        getActivity().finish();
    }

}