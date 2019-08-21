package com.example.vivek.signature1;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;

import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.vivek.signature1.Remote.VolleyQueueService;
import com.example.vivek.signature1.model.Customer;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


import static android.app.PendingIntent.getActivity;
import static com.example.vivek.signature1.SaveSharedPreferences.API_KEY_PREF;
import static com.example.vivek.signature1.SaveSharedPreferences.USERNAME_PREF;
import static com.example.vivek.signature1.SaveSharedPreferences.WAREHOUSE_ID_PREF;


public class review extends AppCompatActivity {
    private Map<String, String> mLogin;
    String newstore_id= "";
    private int customerReview= 0;
    private String signature;

    private Button mBadImgBtn;
    private Button mGoodImgBtn;
    private Button mGreatImgBtn;
    private Button mGreatAngryBtn;
    private Button mSadBtn;

    private Customer customer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.screenBrightness = 1.0f;
        getWindow().setAttributes(params);




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
       // Thread rest = new Thread( new CsharpThread());
       // rest.start();
        mLogin = new HashMap<>();
        mLogin = SaveSharedPreferences.getLogin(this);
//        newstore_id = mLogin.get(WAREHOUSE_ID_PREF);
//        Log.d("REVIEW", "onCreate: "+newstore_id);

        mBadImgBtn = findViewById(R.id.normal);
        mGoodImgBtn = findViewById(R.id.goodBtn);
        mGreatImgBtn = findViewById(R.id.greatBtn);
        mGreatAngryBtn= findViewById(R.id.verysad);
        mSadBtn=findViewById(R.id.sad);


        Log.d("REVIEW", "onCreate: "+mLogin);
        Thread rest = new Thread( new CsharpThread());
        rest.start();

        Bundle intent = getIntent().getExtras();
        if (intent != null) {
           signature = intent.getString("sign");
           newstore_id=intent.getString("warehouse");
        }

        Log.d("REVIEW", "onCreate: "+newstore_id);
        Intent storeint = new Intent(getApplicationContext(), signdialog.class);
        storeint.putExtra("store",newstore_id);


        mBadImgBtn.setOnClickListener(view -> {
            submitsmiley(String.valueOf(30), newstore_id);
            feeddialog();

        });

        mGoodImgBtn.setOnClickListener(view -> {
            submitsmiley(String.valueOf(40), newstore_id);
            feeddialog();
        });
        mSadBtn.setOnClickListener(view -> {
           // float deg = mSadBtn.getRotation() + 360F;
         //   mSadBtn.animate().rotation(deg).setInterpolator(new AccelerateDecelerateInterpolator());
            submitsmiley(String.valueOf(20), newstore_id);
            feeddialog();
        });
        mGreatAngryBtn.setOnClickListener(view -> {
            
            submitsmiley(String.valueOf(10), newstore_id);
            feeddialog();
        });

        mGreatImgBtn.setOnClickListener(view -> {
            submitsmiley(String.valueOf(50), newstore_id);
           // openDialog(customer);
            feeddialog();

        });



//
//        SmileRating smileRating =(SmileRating) findViewById(R.id.smile_rating);
//    smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
//
//            @Override
//            public void onSmileySelected(@BaseRating.Smiley int  smiley, boolean reselected) {
//
//                switch (smiley) {
//                    case SmileRating.BAD:
//
//                        customerReview=20;
//                        submitsmiley(String.valueOf(customerReview), newstore_id);
//                        feeddialog();
//                        break;
//                    case SmileRating.GOOD:
//                        customerReview=40;
//                        submitsmiley(String.valueOf(customerReview), newstore_id);
//                       feeddialog();
//                       break;
//                    case SmileRating.GREAT:
//
//                        customerReview=50;
//                        submitsmiley(String.valueOf(customerReview), newstore_id);
//                        feeddialog();
//                        break;
//                    case SmileRating.OKAY:
//                        customerReview=30;
//                        submitsmiley(String.valueOf(customerReview), newstore_id);
//                        feeddialog();
//                        break;
//                    case SmileRating.TERRIBLE:
//                      break;
//                    default:
//                      throw new IllegalArgumentException("Invalid selection: " );
//                }
//            }
//      });

    }

    public void onActivityResult(int requsetCode, int resultCode, Intent data){

        if (requsetCode== 1){
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentByTag("sign_dialog_fragment");
            fm.beginTransaction().remove(fragment).commit();

        }
    }
    class CsharpThread implements Runnable{



        @Override
        public void run() {
            try{
                Looper.prepare();
                Socket C;
                ServerSocket CC;
                InputStreamReader inn;
                BufferedReader bufferedReader;
                Handler h = new Handler();
                //  final String message;

                CC = new ServerSocket(7891);
                while(true)
                {
                    C = CC.accept();
                    if (C== null) C = CC.accept();

                    ObjectInputStream ss = new ObjectInputStream(C.getInputStream());
                    String[] object = (String[]) ss.readObject();
//                    ArrayList<String> customerList = (ArrayList<String>) object;
                    // String[] myObjects = (String[]) ss.readObject();
                    String customerId=(object[0]);
                    String taxid =(object[1]);
                    String name=(object[2]);
                    String email=(object[3]);
                    String address=(object[4]);
                    String city=(object[5]);
                    String code=(object[6]);
                    String country=(object[7]);
                    String phone=(object[8]);
                    String mobile=(object[9]);


                    customer = new Customer(customerId, taxid, name, email, address, city,code,country,phone,mobile);




                    openDialog(customer);
                   // confirmd();
                    cusinfo(customer);

                }
            }catch (IOException e){
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

    }
    public void cusinfo(Customer customer){
        FragmentManager st = getSupportFragmentManager();
        customer_information cuDialogFragment = customer_information.newIntace(newstore_id,customer);
        cuDialogFragment.show(st,"customer_information");
    }
    public void openDialog(Customer customer){
        FragmentManager fm = getSupportFragmentManager();
        signdialog myDialogFragment = signdialog.newIntace(newstore_id, customer);
        myDialogFragment.show(fm, "sign_dialog_fragment");

      //  getSupportFragmentManager().executePendingTransactions();
    }
//public void confirmd(){
//
//
//    Intent confirm = new Intent(review.this, confirmation.class);
//    confirm.putExtra("cus_NAME",customer.getName());
//    confirm.putExtra ("cus_add",customer.getAddress());
//    confirm.putExtra("wareHouse",newstore_id);
//    confirm.putExtra("cit",customer.getCity());
//   // confirm.putExtra("phone",customer.getMobile());
//    startActivity(confirm);
//
//}




    public void  submitsmiley(final String rate, final String store_id){
        String URL = "https://americanasa.officegest.com/api/addon/evaluation/eval_store";
        final String auth = Utils.getUserAuth(mLogin.get(USERNAME_PREF), mLogin.get(API_KEY_PREF));

        //Create an error listener to handle errors appropriately.
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, URL,
                response -> {
                    Log.d("Chuut", "submitsmiley: res -> "+response);
                    //This code is executed if the server responds, whether or not the response contains data.
                    //The String 'response' contains the server's response.
                }, error -> {
            Log.d("Chuut", "submitsmiley: error -> "+error);
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
                if (store_id != null)
                    MyData.put("store_id",store_id);
                        //
                if (rate != null)
                    MyData.put("classification", rate);




                return MyData;
            }
        };
        // Adding request to request queue
        VolleyQueueService.getInstance(getApplicationContext()).addToRequestQueue(MyStringRequest);
    }

    public void feeddialog(){
        FragmentManager rt =getSupportFragmentManager();
        feeddialog mynewdialog = new feeddialog();
         mynewdialog.setCancelable(false);
        mynewdialog.show(rt,"feeddialog ");

        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                mynewdialog.dismiss(); // when the task active then close the dialog
                t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
            }
        }, 9999);

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        return false;
    }
    @Override
    protected void onPause() {
        super.onPause();

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);

        activityManager.moveTaskToFront(getTaskId(), 0);
    }
}
