package com.example.fleago;
import android.annotation.SuppressLint;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import android.text.Html;

import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;



public class Main2Activity extends AppCompatActivity {

    private Intent intent1;
    private Intent intent2;
    private Intent intent3;
//    ViewFlipper v_flipper;

    ImageView imageview1;


    ViewPager viewPager;
    ViewPagerAdapter adapter;

    FirebaseStorage storage = FirebaseStorage.getInstance("gs://fleago-8b03c.appspot.com");
    //생성된 FirebaseStorage를 참조하는 storage 생성
    StorageReference storageRef1 = storage.getReference("image/9월/");
    StorageReference storageRef2 = storage.getReference("image/10월/");
    StorageReference pathReference;
    StorageReference pathReference2;
    StorageReference pathReference3;
    StorageReference pathReference4;
    StorageReference pathReference5;
    StorageReference pathReference6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        int img1;
        int img2;
        int img3;
        intent1 = getIntent();
        intent2 = getIntent();
        intent3 = getIntent();

        ActionBar ab = getSupportActionBar() ;
        ab.hide();

//        pathReference = storageRef1.child(intent1.getStringExtra("name")+"/" + intent1.getStringExtra("name") + "_1.jpg");
//        if (pathReference == null)
//            storageRef2.child(intent1.getStringExtra("name")+"/" + intent1.getStringExtra("name") + "_1.jpg");
//
//        final long BYTE = 1024*1024;
//        Log.d("pathref",pathReference.getPath());
//
//        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Picasso.with(
//                        Main2Activity.this).
//                        load(uri).
//                        fit().
//                        centerInside().
//                        into((ImageView)findViewById(R.id.v_flipper));
//            }
//        });

        ArrayList<String> photoPath = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            photoPath.add("10월/" + intent1.getStringExtra("name") + "/" + intent1.getStringExtra("name") + "_" + i + ".jpg");
        }

        imageview1 = (ImageView)findViewById(R.id.imageView);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(this, photoPath);

        List<String> banner1 = new ArrayList<>();
        for(String path : photoPath){
            banner1.add(path);
        }
        adapter.add(banner1);
        viewPager.setAdapter(adapter);

        /*try{
        pathReference.getBytes(BYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            Integer[] imgs = new Integer[3];
            StorageReference pathReference;
            public void onSuccess(byte[] b) {
                imgs[0] = (b[0] << 24) + ((b[1] & 0xFF) << 16) + ((b[2] & 0xFF) << 8) + (b[3] & 0xFF);
                *//*pathReference = storageRef1.child(intent1.getStringExtra("name")+"/" + intent1.getStringExtra("name") + "_2.jpg");
                imgs[1] = (b[0] << 24) + ((b[1] & 0xFF) << 16) + ((b[2] & 0xFF) << 8) + (b[3] & 0xFF);
                pathReference = storageRef1.child(intent1.getStringExtra("name")+"/" + intent1.getStringExtra("name") + "_3.jpg");
                imgs[2] = (b[0] << 24) + ((b[1] & 0xFF) << 16) + ((b[2] & 0xFF) << 8) + (b[3] & 0xFF);*//*

                v_flipper=findViewById(R.id.v_flipper);
                *//*for(int image:imgs){
                    flipperImages(image);
                }*//*
                flipperImages(imgs[0]);
            }
        });
        }catch (Exception e){
            e.printStackTrace();
        }*/




        //int images[]={R.drawable.blur_img,R.drawable.img1,R.drawable.ic_info};
        //v_flipper=findViewById(R.id.v_flipper);

        //for(int image:images){
        //    flipperImages(image);
        //}


        final ArrayList<String> gps=(ArrayList<String>)intent1.getSerializableExtra("gps");
        final double gps1;
        final double gps2;

        // gps 정보가 없을 때 처리하는 부분
        if(gps.get(0).equals("N") || gps.get(1).equals("N")) {
            gps1 = Double.parseDouble("131.865077");
            gps2 = Double.parseDouble("37.241828");
            gps.set(0, String.valueOf(gps1));
            gps.set(1, String.valueOf(gps2));
        }

        else{
            gps1 = Double.parseDouble(gps.get(0));
            gps2 = Double.parseDouble(gps.get(1));

        }

        System.out.println("before MainACtivity la" + gps.get(0));
        System.out.println("before MainACtivity long" + gps.get(1));

        System.out.println("MainACtivity la" + gps1);
        System.out.println("MainACtivity long" + gps2);

        TextView textView = (TextView) findViewById(R.id.textView);//name
        TextView textView3 = (TextView) findViewById(R.id.textView3);//discription
        TextView textView4 = (TextView) findViewById(R.id.textView4);//url
        final TextView textView5 = (TextView) findViewById(R.id.textView5);//start_location
        LinearLayout linearlayout1 = (LinearLayout)findViewById(R.id.linearlayout1);//event_type
        TextView textView6 = (TextView) findViewById(R.id.textView6);//start_date
       // TextView textView8 = (TextView) findViewById(R.id.textView8);//end_Date
       // TextView textView9 = (TextView) findViewById(R.id.textView9);//end_time
        TextView textView11 = (TextView) findViewById(R.id.textView11);//start_time
        TextView textView12 = (TextView) findViewById(R.id.textView12);//월 test
//        ImageView  imageView1= (ImageView) findViewById(R.id.image1);
//        ImageView  imageView2= (ImageView) findViewById(R.id.image2);
//        ImageView  imageView3= (ImageView) findViewById(R.id.image3);




        ImageButton button = (ImageButton) findViewById(R.id.button);
        ImageButton button3 = (ImageButton)findViewById(R.id.button3);

//        com.google.android.material.floatingactionbutton.FloatingActionButton button2 = (com.google.android.material.floatingactionbutton.FloatingActionButton)findViewById(R.id.button2);
        TextView button2 = findViewById(R.id.button2);

        textView5.setText(intent1.getStringExtra("start_location"));
        String location= textView5.getText().toString();


        //*지도*//
        MapView mapView = new MapView(this);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(gps2, gps1);//중심점
        mapView.setMapCenterPoint(mapPoint, true);
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(gps2,gps1), true);//중심점변경
        mapView.setZoomLevel(7, true);//줌레벨
        mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(gps2,gps1), 6, true);//중심점, 줌레벨
        mapView.zoomIn(true);//줌인
        mapView.zoomOut(true);//줌아웃
        //*마커*//
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(location);
        marker.setTag(0);
        marker.setMapPoint(mapPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        mapView.addPOIItem(marker);


        // (건대입구역) 교체
        String kk = intent1.getStringExtra("name");
        if (kk.equals("(건대입구역)")) {
            String name = "건대입구 플리마켓";
            textView.setText(name);
        } else
            textView.setText(kk);

//        textView.setText(intent1.getStringExtra("name"));
        textView3.setText(intent1.getStringExtra("discription"));
        textView4.setText(intent1.getStringExtra("url"));
        textView6.setText(intent1.getStringExtra("start_date")+" ~ "+intent1.getStringExtra("end_date"));
        //textView8.setText(intent1.getStringExtra("end_date"));
        //textView9.setText(intent1.getStringExtra("end_time"));
        textView11.setText(intent1.getStringExtra("start_time")+" ~ "+intent1.getStringExtra("end_time"));


        List<String> event_type=(List<String>)intent1.getSerializableExtra("event_type");
        linearlayout1.removeAllViews();
        for(int i = 0; i < event_type.size(); i++){
            TextView textView01 = new TextView(getApplicationContext());
            textView01.setText("#"+event_type.get(i)+" ");  //배열리스트 이용
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            linearlayout1.addView(textView01, params);//linearLayout01 위에 생성
        }

        // 나의 GPS
        final ArrayList<Double> my_location =(ArrayList<Double>)intent1.getSerializableExtra("my location");
        for (Double s : my_location) {
            Log.d("TEST location", "me : " + s);
        }
        //*길찾기 버튼*//
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Intent.ACTION_VIEW);

                try {
                    intent2.setData(Uri.parse("daummaps://route?sp=" + my_location.get(0) + "," + my_location.get(1) + "&ep= + " + gps2 + "," + gps1 + "&by=PUBLICTRANSIT"));
                    startActivity(intent2);
                } catch(Exception e) {
                    e.printStackTrace();
                    intent2.setData(Uri.parse("market://details?id=net.daum.android.map"));
                    startActivity(intent2);
                }
            }
        });

        //*ar버튼*//
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAR = new Intent(getApplicationContext(),ARActivity.class);
                intentAR.putExtra("name", intent1.getStringExtra("name"));

                intentAR.putExtra("gps", gps);

                intentAR.putExtra("latitude", gps2);
                intentAR.putExtra("longitude", gps1);
                startActivity(intentAR);//액티비티 띄우기
            }
        });

        //*클립보드복사버튼*//
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("위치복사",textView5.getText().toString() );
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getApplication(), "위치가 복사되었습니다.",Toast.LENGTH_LONG).show();
            }
        });
    }


//    //*image slider*//
//    private void flipperImages(int image) {
//        ImageView imageView=new ImageView(this);
//        Log.d("image value : ",Integer.toString(image));
//        imageView.setBackgroundResource(image);
//        v_flipper.addView(imageView);
//        v_flipper.setFlipInterval(2000);
//        v_flipper.setAutoStart(true);
//        v_flipper.setInAnimation(this,android.R.anim.slide_in_left);
//        v_flipper.setOutAnimation(this,android.R.anim.slide_out_right);
//    }

    //* Search bar*//
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu) ;

        return true ;
    }
    // *Search bar*//
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_serach :
                // TODO : process the click event for action_search item.
                return true ;
            // ...
            // ...
            default :
                return super.onOptionsItemSelected(item) ;
        }
    }


}
