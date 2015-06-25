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

/**
 * Created by l2ol3otic2 on 6/25/2015.
 */
public class OneFragment extends Fragment {

    public static final String matrix = "http://www.borktor.com/rest/smartscreen.php";
    public int countData1 = 0;
    //private int ImageviewID[] = new int[]{R.id.imageView5};
    private int LayoutID[] = new int[]{R.id.lu1,R.id.lu2,R.id.lu3,R.id.lu4,R.id.lu5,R.id.lu6,R.id.lu7,R.id.lu8,R.id.lu9,R.id.lu10};
    public View rootView;

    public Integer[] IDs = new Integer[32];
    public String[] Urls = new String[32];
    public Integer[] Pages = new Integer[32];



    public static OneFragment newInstance() {
        OneFragment fragment = new OneFragment();
        return fragment;
    }

    public OneFragment() { }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_one, container, false);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        SharedPreferences sp = this.getActivity().getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
        String URl;

        for(int a=0;a<10;a++){

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
