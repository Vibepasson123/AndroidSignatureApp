package com.example.vivek.signature1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.vivek.signature1.Remote.VolleyQueueService;
import com.example.vivek.signature1.model.Customer;
import com.google.gson.JsonObject;
import com.kyanogen.signatureview.SignatureView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.jar.JarException;

import static com.example.vivek.signature1.SaveSharedPreferences.API_KEY_PREF;
import static com.example.vivek.signature1.SaveSharedPreferences.USERNAME_PREF;
import static com.example.vivek.signature1.SaveSharedPreferences.WAREHOUSE_ID_PREF;


public class signdialog extends DialogFragment {
    private SignatureView signatureView;
    private boolean wantToCloseDialog = false;

    private Map<String, String> mLogin;
    String newstore_id;
    private Customer customer;
    String sign = "";
    boolean Action;
    String updatecustomer="";
    String mail_customer2="";



    public static signdialog newIntace(String storeId, Customer customer) {
        signdialog sd = new signdialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("warehouse", storeId);
        args.putParcelable("customer", customer);
        sd.setArguments(args);

        return sd;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            newstore_id = getArguments().getString("warehouse");
            customer = getArguments().getParcelable("customer");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
      AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
      LayoutInflater inflater= getActivity().getLayoutInflater();
      View view = inflater.inflate(R.layout.extended_dialog,null);
        signatureView = view.findViewById(R.id.signature_view);

        if (customer == null) customer = new Customer();

        mLogin = new HashMap<>();
        mLogin = SaveSharedPreferences.getLogin(getActivity().getApplicationContext());
         mLogin.get(WAREHOUSE_ID_PREF);
      final AlertDialog.Builder builder1 = builder.setView(view)
              .setTitle("\n" + "Por favor assine ")
              .setCancelable(false)
              .setNegativeButton("Cancelar", null)
              .setNeutralButton("Limpar", ((dialog, which) -> dismiss()))
              .setPositiveButton("Enviar", (dialog, which) -> {


                  Bitmap getSign = signatureView.getSignatureBitmap();

                  String  signString  =getEncoded64ImageStringFromBitmap(getSign);
                  sign = "data:image/png;base64,"+getEncoded64ImageStringFromBitmap(getSign);


//                  Intent myIntent = new Intent(getActivity().getApplicationContext(), review.class);
//                  myIntent.putExtra("sign", signString);
//
//                  startActivity(myIntent);
                //  startActivity(new Intent(getActivity(), review.class));

//                  String customerID ="";
//                  String tax  = "";
//                  String name ="";
//                  String email ="";
//                  String address ="";
//                  String city ="";
//                  String code ="";
//                  String country="";
//                  String phone="";
//                  String mobile="";

//                  Intent intent = getActivity().getIntent();
//
//                       customerID = intent.getStringExtra("cusID");
//                      tax = intent.getStringExtra("tax");
//                      name = intent.getStringExtra("Name");
//                      email = intent.getStringExtra("Email");
//                      address = intent.getStringExtra("Address");
//                      city = intent.getStringExtra("City");
//                      code = intent.getStringExtra("Code");
//                      country = intent.getStringExtra("Country");
//                      phone = intent.getStringExtra("Phone");
//                      mobile = intent.getStringExtra("Mobile");
                    //  store= intent.getStringExtra("warehouse2");
                    //Log.d("fikjhkjghj89056677", "doLogin: -> "+store);


                  Log.d("CREATEDIALOG", "onCreateDialog: "+newstore_id+ " - "+customer+" - "+signString);



                            checkTaxid();









                  getActivity().getSupportFragmentManager().popBackStack();

              });
      return builder.create();


  }




    @Override

    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        window.setBackgroundDrawableResource(R.drawable.gradient_bg);
        window.setGravity(Gravity.CENTER);


        //prevent dismiss to handle error
        final AlertDialog d = (AlertDialog)getDialog();
        if(d != null) {
            Button btn = d.getButton(Dialog.BUTTON_NEUTRAL);
            btn.setOnClickListener(v -> {
                signatureView.clearCanvas();
                if(wantToCloseDialog) d.dismiss();
            });
        }
    }
    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return imgString;
    }
    public void  submit(final String newstore_id,
                        String customerID,
                        String tax, String name, String email, String address, String city, String code, String country, String phone, String mobile, String sign){
        String URL = "https://americanasa.officegest.com/api/entities/customers";


        final String auth = Utils.getUserAuth(mLogin.get(USERNAME_PREF), mLogin.get(API_KEY_PREF));

        //Create an error listener to handle errors appropriately.
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, URL,
                response -> {
                    Log.d("clint", "submit: res -> "+response);
                    try {
                        String newcustomer_id ="";

                        JSONObject resJson = new JSONObject(response);
                        String result = resJson.getString("result");
                        String customers = resJson.getString("customer_id");

                         this.mail_customer2=customers;

                            newcustomer_id= customers;
                           update_customer2(newcustomer_id,sign);
                            Thread.sleep(2000);
                            sendMail_id_2(mail_customer2);


                        Log.d("somthing", "submit: error -> "+newcustomer_id);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //This code is executed if the server responds, whether or not the response contains data.
            //The String 'response' contains the server's response.
                }, error -> {
            Log.d("somthing", "submit: error -> "+error);
                    //This code is executed if there is an error.
                }

        ){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("Authorization", "Basic "+ auth);
                return headers;
            }

            @Override

            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<>();
                MyData.put("store_id", newstore_id);
                MyData.put("call_id",customerID);//
                MyData.put("customertaxid",tax);//
                MyData.put("name",name);
                MyData.put("email",email);
                MyData.put("address",address);//address
                MyData.put("city",city);//city
                MyData.put("zipcode",code);
                MyData.put("country",country);
                MyData.put("phone",phone);
                MyData.put("mobilephone",mobile);
                MyData.put("fp_image",sign);



                return MyData;
            }
        };
        // Adding request to request queue
        VolleyQueueService.getInstance(getActivity()).addToRequestQueue(MyStringRequest);
    }

//    private void secondApi(String newcustomer) {
//
//
//
//        {
//            String URL = "https://americanasa.officegest.com/api/entities/customers/";
//
//
//            final String auth = Utils.getUserAuth(mLogin.get(USERNAME_PREF), mLogin.get(API_KEY_PREF));
//
//            //Create an error listener to handle errors appropriately.
//            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, URL,
//                    response -> {
//                        Log.d("clint", "submit: res -> "+response+"neapi");
//                        //This code is executed if the server responds, whether or not the response contains data.
//                        //The String 'response' contains the server's response.
//                    }, error -> {
//                Log.d("somthing", "seconddata: error -> "+error+"test test test");
//                //This code is executed if there is an error.
//            }
//
//            ){
//                @Override
//                public Map<String, String> getHeaders() {
//                    HashMap<String, String> headers = new HashMap<>();
//                    headers.put("Content-Type", "application/x-www-form-urlencoded");
//                    headers.put("Authorization", "Basic "+ auth);
//                    return headers;
//                }
//
//                @Override
//
//                protected Map<String, String> getParams() {
//                    Map<String, String> MData = new HashMap<>();
//                    MData.put("call_id",customerID);
//                    MData.put("customer_id",newcustomer);
//
//
//
//                    return MData;
//                }
//            };
//            // Adding request to request queue
//              VolleyQueueService.getInstance(getActivity()).addToRequestQueue(MyStringRequest);
//        }
//
//
//    }


   public void sendMail_id_1( final String customer_id){


        {
            String URL = "https://americanasa.officegest.com/api/addon/evaluation/send_email_rgdp_sign_customer";


            final String auth = Utils.getUserAuth(mLogin.get(USERNAME_PREF), mLogin.get(API_KEY_PREF));

            //Create an error listener to handle errors appropriately.
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, URL,
                    response -> {
                        Log.d("clint", "submit: res -> "+response+"neapi");





                        //This code is executed if the server responds, whether or not the response contains data.
                        //The String 'response' contains the server's response.
                    }, error -> {
                Log.d("somthing", "seconddata: error -> "+error+"test test test");
                //This code is executed if there is an error.
            }

            ){
                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/x-www-form-urlencoded");
                    headers.put("Authorization", "Basic "+ auth);
                    return headers;
                }

                @Override

                protected Map<String, String> getParams() {
                    Map<String, String> MData = new HashMap<>();
                   // MData.put("id",customerID);
                    MData.put("customer_id",updatecustomer);
                    MData.put("",updatecustomer);



                    return MData;
                }
            };
            // Adding request to request queue
            VolleyQueueService.getInstance(getActivity()).addToRequestQueue(MyStringRequest);
        }


    }
    public void sendMail_id_2( final String newcustomer_id){


        {
            String URL = "https://americanasa.officegest.com/api/addon/evaluation/send_email_rgdp_sign_customer";


            final String auth = Utils.getUserAuth(mLogin.get(USERNAME_PREF), mLogin.get(API_KEY_PREF));

            //Create an error listener to handle errors appropriately.
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, URL,
                    response -> {
                        Log.d("clint", "submit: res -> "+response+"neapi");



                        //sponse contains data.
                        //The String 'response' contains the server's response.

                        //This code is executed if the server responds, whether or not the re
                    }, error -> {
                Log.d("somthing", "seconddata: error -> "+error+"test test test");
                //This code is executed if there is an error.
            }

            ){
                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/x-www-form-urlencoded");
                    headers.put("Authorization", "Basic "+ auth);
                    return headers;
                }

                @Override

                protected Map<String, String> getParams() {
                    Map<String, String> MData = new HashMap<>();
                    // MData.put("id",customerID);
                    MData.put("customer_id",mail_customer2);
                    MData.put("",mail_customer2);



                    return MData;
                }
            };
            // Adding request to request queue
            VolleyQueueService.getInstance(getActivity()).addToRequestQueue(MyStringRequest);
        }


    }
      public void checkTaxid( ) {
          Log.d("this is runnig ", "check ststement : res -> ");
          String URL = "https://americanasa.officegest.com/api/entities/customers";//?filter[customertaxid]="+customer.getTax()+"&filter[active]=T";
///?filter[customertaxid]="+customer.getTax()+"&filter[active]=T"
          final String auth = Utils.getUserAuth(mLogin.get(USERNAME_PREF), mLogin.get(API_KEY_PREF));

          StringRequest MyStringRequest = new StringRequest(Request.Method.GET, URL,

                          response -> {


                              try {
                                  JSONObject resJson = new JSONObject(response);
                                  String result = resJson.getString("result");

                                  JSONObject customers = resJson.getJSONObject("customers");

                                  String customerId = "";
                                  String customer_id ="";
                                  String test = customer.getTax();
                                  for (Iterator<String> i = customers.keys(); i.hasNext();) {
                                      final String key = i.next();
                                      customerId = customers.getJSONObject(key).getString("customertaxid");
                                      customer_id= customers.getJSONObject(key).getString("id");
                                      //customer_id = customers.getJSONObject(key).getString("customer_id");
                                    //  updatecustomer = customers.getJSONObject(key).getString("customer_id");
                                  }
//                                  for (Iterator<String> i = customers.keys(); i.hasNext();) {
//                                      final String key = i.next();
//                                      customer_id= customers.getJSONObject(key).getString("id");
//                                  }

                                  Log.d("customer", "res -> "+resJson);
                                  Log.d("customer", "customer -> "+customer);
                                  Log.d("customer", "customer id -> "+customerId);

                                  Log.d("customer", "customer__id -> "+customer_id);


                                 this.updatecustomer = customer_id;

                                  if ( customerId.equals(customer.getTax()) ) {
                                      String tesdt = customer_id;

                                      update_customer(tesdt,sign);
                                      Thread.sleep(2000);
                                      sendMail_id_1(customer_id);

                                      Log.d("sucesses", "newway_id -> "+customer_id);
                                  }else{


                                      submit(newstore_id,
                                              customer.getCustomerID(),
                                              customer.getTax(),
                                              customer.getName(),
                                              customer.getEmail(),
                                              customer.getAddress(),
                                              customer.getCity() ,
                                              customer.getCode(),
                                              customer.getCountry(),
                                              customer.getPhone(),
                                              customer.getMobile(),
                                              sign);

                                  }
                              } catch (JSONException e) {

                              } catch (InterruptedException e) {
                                  e.printStackTrace();
                              }


                          }, error -> {
                      Log.d("somthing", "seconddata: error -> "+error+"test test test");
                      //This code is executed if there is an error.
                  }

                  ){
                      @Override
                      public Map<String, String> getHeaders() {
                          HashMap<String, String> headers = new HashMap<>();
                          headers.put("Content-Type", "application/x-www-form-urlencoded");
                          headers.put("Authorization", "Basic "+ auth);
                          return headers;
                      }

                      @Override

                      protected Map<String, String> getParams() {
                          Map<String, String> MData = new HashMap<>();
                          // MData.put("id",customerID);
                          //  MData.put("customer_id");



                          return MData;
                      }
                  };
                  // Adding request to request queue
          VolleyQueueService.getInstance(getActivity()).addToRequestQueue(MyStringRequest);
              }


    public void  update_customer(final String customer_id, String sign){
        String URL = "https://americanasa.officegest.com/api/entities/customers/"+customer_id;


        final String auth = Utils.getUserAuth(mLogin.get(USERNAME_PREF), mLogin.get(API_KEY_PREF));

        //Create an error listener to handle errors appropriately.
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, URL,
                response -> {
                    Log.d("sexy", ": res -> "+response);

                }, error -> {
            Log.d("somthing", "submit: error -> "+error);
            //This code is executed if there is an error.
        }

        ){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("Authorization", "Basic "+ auth);
                return headers;
            }

            @Override

            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<>();

                MyData.put("fp_image", sign);



                return MyData;
            }
        };
        // Adding request to request queue
        VolleyQueueService.getInstance(getActivity()).addToRequestQueue(MyStringRequest);
    }
    public void  update_customer2(final String newid, String sign){
        String URL = "https://americanasa.officegest.com/api/entities/customers/"+newid;


        final String auth = Utils.getUserAuth(mLogin.get(USERNAME_PREF), mLogin.get(API_KEY_PREF));

        //Create an error listener to handle errors appropriately.
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, URL,
                response -> {
                    Log.d("sexy", ": res -> "+response);

                }, error -> {
            Log.d("somthing", "submit: error -> "+error);
            //This code is executed if there is an error.
        }

        ){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("Authorization", "Basic "+ auth);
                return headers;
            }

            @Override

            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<>();

                MyData.put("fp_image", sign);



                return MyData;
            }
        };
        // Adding request to request queue
        VolleyQueueService.getInstance(getActivity()).addToRequestQueue(MyStringRequest);
    }

}

