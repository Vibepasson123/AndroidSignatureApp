package com.example.vivek.signature1;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import static com.example.vivek.signature1.SaveSharedPreferences.WAREHOUSE_ID_PREF;
import com.example.vivek.signature1.model.Customer;
import static com.example.vivek.signature1.SaveSharedPreferences.API_KEY_PREF;
import static com.example.vivek.signature1.SaveSharedPreferences.USERNAME_PREF;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class confirmation extends AppCompatActivity {
    String newstore_id= "";
    //private TextView cusname;
    private TextView cusadd;
    private TextView cusname2;
    //private TextView mob;
    private TextView citee;
    Button accept;
    Button cancel;

    private Customer customer;
    private Map<String, String> mLogin;
    public static final int SCREEN_ORIENTATION_REVERSE_LANDSCAPE =  (0x00000008);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.screenBrightness = 1.0f;
        getWindow().setAttributes(params);
       // setRequestedOrientation(.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

          accept=findViewById(R.id.accept);
          cancel=findViewById(R.id.can);

        mLogin = new HashMap<>();
        mLogin = SaveSharedPreferences.getLogin(this);
        newstore_id = mLogin.get(WAREHOUSE_ID_PREF);
       // cusname=findViewById(R.id.cusname);
        cusadd=findViewById(R.id.cusaa);
        cusname2=findViewById(R.id.cusname2);
       // mob=findViewById(R.id.mob);
        citee=findViewById(R.id.cit);




        Intent intent = getIntent();
        Bundle cd = intent.getExtras();
        if(cd !=null)
        {
            String Cname= (String)cd.get("cus_NAME");
           // cusname.setText(Cname);
            cusname2.setText(Cname);

            String cADD = (String)cd.get("cus_add");
            cusadd.setText(cADD);

            String cCit = (String)cd.get("cit");
            citee.setText(cCit);

           // String cMob =(String)cd.get("phone");
           // mob.setText(cMob);







        }
        accept.setOnClickListener(view -> {
            this.finish();

            //openDialog(customer);
        });
        cancel.setOnClickListener(view -> {
            test();
            this.finish();

            //openDialog(customer);
        });





    }
    public void test() {

        android.support.v4.app.Fragment fragment = getSupportFragmentManager().findFragmentByTag("sign_dialog_fragment");
        if (fragment != null) {
            DialogFragment dialog = (DialogFragment) fragment;
            dialog.dismiss();
        }
    }

    private void restartSelf() {
        AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis() + 10, // one second
                PendingIntent.getActivity(this, 0, getIntent(), PendingIntent.FLAG_ONE_SHOT
                        | PendingIntent.FLAG_CANCEL_CURRENT));
        finish();
    }

}
