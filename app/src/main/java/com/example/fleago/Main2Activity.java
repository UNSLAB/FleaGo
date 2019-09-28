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

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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
    ViewFlipper v_flipper;

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

        ActionBar ab = getSupportActionBar() ;
        ab.hide();


        int images[]={R.drawable.blur_img,R.drawable.img1,R.drawable.ic_info};
        v_flipper=findViewById(R.id.v_flipper);

        for(int image:images){
            flipperImages(image);
        }

        intent1 = getIntent();
        intent2 = getIntent();
        intent3 = getIntent();

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

//
//    public void setImage(final ImageView image1 , final ImageView image2, final ImageView image3) {
//        pathReference = storageRef1.child(intent1.getStringExtra("name")+ "/" + intent1.getStringExtra("name") + "_1.jpg");
//        pathReference2 = storageRef1.child(intent1.getStringExtra("name") + "/" + intent1.getStringExtra("name") + "_2.jpg");
///       pathReference3= storageRef1.child(intent1.getStringExtra("name") + "/" + intent1.getStringExtra("name")+ "_3.jpg");
////        pathReference4 = storageRef2.child(intent1.getStringExtra("name")+ "/" + intent1.getStringExtra("name") + "_1.jpg");
////        pathReference5 = storageRef2.child(intent1.getStringExtra("name") + "/" + intent1.getStringExtra("name") + "_2.jpg");
////        pathReference6= storageRef2.child(intent1.getStringExtra("name") + "/" + intent1.getStringExtra("name")+ "_3.jpg");
//        //pathReference = storageRef1.child("(건대입구역)/(건대입구역)_1.jpg");
//        if (pathReference == null)
//            storageRef1.child(intent1.getStringExtra("name") + "/" + intent1.getStringExtra("name") + "_1.jpg");
//
//        if (pathReference2 == null)
//            storageRef1.child(intent1.getStringExtra("name") + "/" + intent1.getStringExtra("name") + "_2.jpg");
//
//        if (pathReference3 == null)
//            storageRef1.child(intent1.getStringExtra("name") + "/" + intent1.getStringExtra("name") + "_3.jpg");
////
////        if (pathReference4 == null)
////            storageRef2.child(intent1.getStringExtra("name") + "/" + intent1.getStringExtra("name") + "_1.jpg");
////
////        if (pathReference5 == null)
////            storageRef2.child(intent1.getStringExtra("name") + "/" + intent1.getStringExtra("name") + "_2.jpg");
////
////        if (pathReference6 == null)
////            storageRef2.child(intent1.getStringExtra("name") + "/" + intent1.getStringExtra("name") + "_3.jpg");
//
//
//        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Picasso.with(
//                        Main2Activity.this).
//                        load(uri).
//                        fit().
//                        centerInside().
//                        into(image1);
//            }
//        });
//        pathReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Picasso.with(
//                        Main2Activity.this).
//                        load(uri).
//                        fit().
//                        centerInside().
//                        into(image2);
//            }
//        });
//        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Picasso.with(
//                        Main2Activity.this).
//                        load(uri).
//                        fit().
//                        centerInside().
//                        into(image3);
//            }
//        });
//        pathReference3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Picasso.with(
//                        Main2Activity.this).
//                        load(uri).
//                        fit().
//                        centerInside().
//                        into(image);
//            }
//        });
//
//
//    }

    //*image slider*//
    private void flipperImages(int image) {
        ImageView imageView=new ImageView(this);
        imageView.setBackgroundResource(image);
        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(2000);
        v_flipper.setAutoStart(true);
        v_flipper.setInAnimation(this,android.R.anim.slide_in_left);
        v_flipper.setOutAnimation(this,android.R.anim.slide_out_right);
    }

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
