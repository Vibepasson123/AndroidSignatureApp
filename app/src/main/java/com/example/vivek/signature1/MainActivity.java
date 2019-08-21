package com.example.vivek.signature1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.vivek.signature1.Remote.VolleyQueueService;
import com.example.vivek.signature1.model.User;
import com.google.gson.Gson;
import com.kyanogen.signatureview.SignatureView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.vivek.signature1.SaveSharedPreferences.API_KEY_PREF;
import static com.example.vivek.signature1.SaveSharedPreferences.DOMAIN_PREF;
import static com.example.vivek.signature1.SaveSharedPreferences.USERNAME_PREF;
import static com.example.vivek.signature1.SaveSharedPreferences.USER_ID_PREF;
import static com.example.vivek.signature1.SaveSharedPreferences.WAREHOUSE_ID_PREF;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener {


    Thread newThread;
    View signatureView;
    Button bt ;
    EditText id;
    EditText pass;
    EditText storeid;


    private Map<String, String> mLogin;

    ServerSocket CC=null;

    Map<String, String> login = new HashMap<>();
    private DatagramSocket ButterKnife;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id =findViewById(R.id.userID);
        pass=findViewById(R.id.userPassword);
       // storeid =findViewById(R.id.store);
        //storeid.getText().clear();
//        storeid.append("1");
       // storeid.getText().toString();



        pass.getText().clear();
        pass.append("scTd7vbV8P2K");

        bt = findViewById(R.id.openDialogBtn);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override




            public void onClick(View v) {
                test();
            }
        });

//        Thread rest = new Thread( new CsharpThread());
//       rest.start();

        pass.setOnEditorActionListener(this);

        bt.setOnClickListener(this);


        checkLoginPref();
        attemptLogin();

    }




        private String appTag;
    @SuppressLint("HandlerLeak")
    Handler myUpdateHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            Log.d(appTag, "setting textview");


        }
    };


//    class CsharpThread implements Runnable{
//
//
//
//        @Override
//        public void run() {
//            try{
//                Looper.prepare();
//                Socket C;
//                ServerSocket CC;
//                InputStreamReader inn;
//                BufferedReader bufferedReader;
//                Handler h = new Handler();
//              //  final String message;
//
//              CC = new ServerSocket(7891);
//                while(true)
//                {
//                    C = CC.accept();
//                    if (C== null) C = CC.accept();
//                    openDialog();
//                    ObjectInputStream ss = new ObjectInputStream(C.getInputStream());
//                    String[] object = (String[]) ss.readObject();
////                    ArrayList<String> customerList = (ArrayList<String>) object;
//                   // String[] myObjects = (String[]) ss.readObject();
//                    String customerId=(object[0]);
//                    String store=(object[1]);
//
//
//
//
//
//                    Intent myIntent = new Intent(getApplicationContext(), review.class);
//                    myIntent.putExtra("cusID", customerId);
//                    myIntent.putExtra("strore",store );
//
//
//                  //  InputStream stream =C.getInputStream();
//
//                   // String[] myObjects = (String[])inObjectStream.readObject();
//
//                    //byte[] data = new byte[32];
//                    //int count = stream.read(data);
//                    //System.out.println(data);
//                   // tnn = bufferedReader.readLine();
//                  // test();
//                  //  System.out.println("inn"+ Array.get(int.class.isArray());
//                    //BufferedReader d
//                           // = new BufferedReader(new InputStreamReader(stream));
//
//                    //
//
//
//
//
//
//                }
//            }catch (IOException e){
//                e.printStackTrace();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//    }

    @Override
    protected void onStop() {
        super.onStop();
       // newThread.interrupt();
    }
    public void test() {
        Intent Intent = new Intent(this, review.class);
        startActivity(Intent);
    }

    public void openDialog(){
        FragmentManager fm = getSupportFragmentManager();
        signdialog myDialogFragment = new signdialog();
        myDialogFragment.show(fm, "sign_dialog_fragment");

    }

  public void feeddialog(){
        FragmentManager rt =getSupportFragmentManager();
        feeddialog mynewdialog = new feeddialog();
        mynewdialog.show(rt,"feeddialog ");

  }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == bt.getId()) {
            attemptLogin();
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
        if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
            attemptLogin();
            return true;
        }
        return false;
    }

    private void checkLoginPref() {
        mLogin = new HashMap<>();
        Map<String, String> temp = SaveSharedPreferences.getLogin(getApplicationContext());
        if (temp != null) {
//            mDomainView.setText(temp.get(DOMAIN_PREF));
            id.setText(temp.get(USERNAME_PREF));
        }
    }

    private void attemptLogin() {
//        if (mBarcode != null) {
//            doLogin();
//        } else {
            // Reset errors.
//            mDomainView.setError(null);
            id.setError(null);
            pass.setError(null);

            // Store values at the time of the mLogin attempt.
//            String domain = mDomainView.getText().toString();
            String username = id.getText().toString();
            String password = pass.getText().toString();

            boolean cancel = false;
            View focusView = null;

            // Check for a valid password, if the user entered one.
            if (TextUtils.isEmpty(password)) {
                pass.setError(getString(R.string.error_field_required));
                focusView = pass;
                cancel = true;
            } else if (!isPasswordValid(password)) {
                pass.setError(getString(R.string.error_invalid_password));
                focusView = pass;
                cancel = true;
            }

            // Check for a valid username address.
            if (TextUtils.isEmpty(username)) {
                id.setError(getString(R.string.error_field_required));
                focusView = id;
                cancel = true;
            }

            // Check for a valid domain address.
//            if (TextUtils.isEmpty(domain)) {
//                mDomainView.setError(getString(R.string.error_field_required));
//                focusView = mDomainView;
//                cancel = true;
//            } else if (!isDomainValid(domain)) {
//                mDomainView.setError(getString(R.string.error_invalid_domain));
//                focusView = mDomainView;
//                cancel = true;
//            }

            if (cancel) {
                // There was an error; don't attempt mLogin and focus the first
                // form field with an error.
                focusView.requestFocus();
            } else {
                // Show a progress spinner, and kick off a background task to
                // perform the user mLogin attempt.
                // Utils.hideSoftKeyboard(this);
                doLoginForm();
            }
//        }
    }

    private boolean isDomainValid(String domain) {
        return domain.contains(".officegest.com");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

//    private void getLoginFromQRCode() {
//        if (mBarcode != null) {
//            if (TextUtils.isEmpty(mBarcode)) {
//                Toast.makeText(getApplicationContext(), String.valueOf(R.string.barcode_empty), Toast.LENGTH_LONG).show();
//                finish();
//            } else if (Utils.isBase64Encoded(mBarcode)) {
//                byte[] decodeValue = Base64.decode(mBarcode, Base64.DEFAULT);
//
//                String url = new String(decodeValue);
//                String[] urlSplit = url.split("\\|");
//
//                mLogin.put(DOMAIN_PREF, urlSplit[1]);
//                mLogin.put(USERNAME_PREF, urlSplit[2]);
//                mLogin.put(API_KEY_PREF, urlSplit[3]);
//            }
//        }
//    }

 private void doLoginForm() {
        final String urlLogin = "https://americanasa.officegest.com/api/";
        final String auth = Utils.getUserAuth(String.valueOf(id.getText()), String.valueOf(pass.getText()));

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, urlLogin, null, null,
                error -> {
                    if (error.networkResponse.statusCode == 401) {
                        try {
                            String response = new String(error.networkResponse.data, "UTF-8");
                            JSONObject responseJSON = new JSONObject(response);
                            String apiKey = Utils.getSha1(responseJSON.getString("hash"), responseJSON.getString("lic"));

                            mLogin.put(API_KEY_PREF, apiKey);
                            mLogin.put(USERNAME_PREF, String.valueOf(id.getText()));
                            mLogin.put(DOMAIN_PREF, urlLogin);

                            doLogin();

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Network Error!", Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Basic "+ auth);
                return headers;
            }
        };
        // Adding request to request queue
        VolleyQueueService.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
    }

    private void doLogin() {
//        getLoginFromQRCode();
        final String urlLogin = "https://americanasa.officegest.com/api/entities/employees?filter[login]=" + mLogin.get(USERNAME_PREF) + "&filter[active]=1&operator==";
        final String auth = Utils.getUserAuth(mLogin.get(USERNAME_PREF), mLogin.get(API_KEY_PREF));

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, urlLogin, null,
                response -> {
                    try {
                        JSONObject employee = response.getJSONObject("employees");

                        String user_id = employee.names().getString(0);
                        User user = new Gson().fromJson(employee.getJSONObject(user_id).toString(), User.class);

                        mLogin.put(USER_ID_PREF, user.getUserId());
                        mLogin.put(WAREHOUSE_ID_PREF, user.getWarehouseId());


                        Log.d("DO LOGIN", "doLogin: -> "+user.getWarehouseId());

//                        if (persistUser(mLogin) != -1) {
                        SaveSharedPreferences.setLoggedIn(getApplicationContext(), mLogin,  true);
                        Intent de = new Intent(MainActivity.this,confirmation.class);
                        de.putExtra("warehouse",user.getWarehouseId());

                        Intent myIntent = new Intent(MainActivity.this, review.class);
//                        Intent test= new Intent(MainActivity.this,signdialog.class);
//                        test.putExtra("warehouse2",  user.getWarehouseId());
                        myIntent.putExtra("warehouse",  user.getWarehouseId());
                        startActivity(myIntent);

//                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Utils.handleVolleyError(getApplicationContext(), error);
                })
        {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Basic "+ auth);
                return headers;
            }
        };
        // Adding request to request queue
        VolleyQueueService.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
    }

}