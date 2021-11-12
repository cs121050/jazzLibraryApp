
package com.example.jazzLibrary;

        import android.Manifest;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.StrictMode;
        import android.widget.TextView;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.app.ActivityCompat;

        import java.util.List;
        import java.util.Random;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN= 6000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);



        TextView splash_screen_quote_TextView=findViewById(R.id.splash_screen_quote_TextView);

        List<SplashScreenQuotes> splashScreenQuotes =JazzLibraryDAO.retriveSplashScreenQuotes();

        int randomID = randomIdGenerator(splashScreenQuotes.size());

        splash_screen_quote_TextView.setText(splashScreenQuotes.get(randomID).getQuote_txt());


        //edo tha fortonei olh thn bash dedomenon mesa se pinaka ,, kai den tha kanei pote 3ana DAO
        // kai tha trexei mono apo tous pinakes ta query ,,,






        new Handler().postDelayed(new Runnable() {
            @Override
                public void run(){
                    Intent intent= new Intent(SplashScreenActivity.this, UIListenersActivity.class);
                    startActivity(intent);
                    finish();
                }
            },SPLASH_SCREEN);


    }



    public int randomIdGenerator(int maxID){

        Random r = new Random();
        int low = 1;
        int high = maxID;
        int randomID = r.nextInt(high-low) + low;

        return randomID;
    }





}