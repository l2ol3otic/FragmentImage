package l2ol3otic.project.fragments;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class TwoFragment extends Fragment {

    private int LayoutID[] = new int[]{R.id.lu1,R.id.lu2,R.id.lu3,R.id.lu4,R.id.lu5,R.id.lu6,R.id.lu7,R.id.lu8,R.id.lu9,R.id.lu10,R.id.lu11,R.id.lu12,R.id.lu13,R.id.lu14,R.id.lu15,R.id.lu16,R.id.lu17,R.id.lu18,R.id.lu19,R.id.lu20,R.id.lu21};
    public View rootView;
    public static TwoFragment newInstance() {
        TwoFragment fragment = new TwoFragment();
        return fragment;
    }

    public TwoFragment() { }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.fragment_two, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        SharedPreferences sp = this.getActivity().getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
        String URl;

        for(int a=10;a<21;a++){

            URl = sp.getString("URl"+ a , "Null");
            Log.i("Check Array", URl);
            LinearLayout Layout = (LinearLayout)rootView.findViewById(LayoutID[a]);
            BitmapDrawable ob = new BitmapDrawable(getResources(), fetchImage(URl));
            Log.i("Check OB", String.valueOf(ob));
            Layout.setBackground(ob);

        }

        return rootView;
    }
    private Bitmap fetchImage(String matrix) {
        try
        {
            URL url = new URL(matrix); // imageUrl คือ url ของรูปภาพ
            InputStream input = null;
            URLConnection conn = url.openConnection();
            HttpURLConnection httpConn = (HttpURLConnection)conn;
            httpConn.setRequestMethod("GET");
            httpConn.setReadTimeout(40000); // ตั้งเวลา  connect timeout
            httpConn.connect(); // connection

            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                input = httpConn.getInputStream(); // จับใส่ InputStream
            }
            Bitmap bitmap = BitmapFactory.decodeStream(input); //แปลงเป็น Bitmap
            input.close();
            httpConn.disconnect();
            return bitmap;

        }
        catch ( MalformedURLException e ){
            Log.d("fetchImage","MalformedURLException invalid URL: " + matrix );
        }catch ( IOException e ){
            Log.d("fetchImage","IO exception: " + e);
        }catch(Exception e){
            Log.d("fetchImage","Exception: " + e);
        }
        return null;
    }
}
