package adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import com.example.fleago.Main2Activity;
import com.example.fleago.Market;
import com.example.fleago.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListViewAdapter extends BaseSwipeAdapter {

    private Context mContext;
    private ArrayList<Market> list;
    private ImageView image;

    //firebaseStorage 인스턴스 생성
    //하나의 Storage와 연동되어 있는 경우, getInstance()의 파라미터는 공백으로 두어도 됨
    //하나의 앱이 두개 이상의 Storage와 연동이 되어있 경우, 원하는 저장소의 스킴을 입력
    //getInstance()의 파라미터는 firebase console에서 확인 가능('gs:// ... ')
    FirebaseStorage storage = FirebaseStorage.getInstance();
    //생성된 FirebaseStorage를 참조하는 storage 생성
    StorageReference storageRef = storage.getReference();
    //Storage 내부의 images 폴더 안의 image.jpg 파일명을 가리키는 참조 생성
    StorageReference pathReference = storageRef.child("/FLEAGO.png");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    private String urii;


    public ListViewAdapter(Context mContext, ArrayList list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        // 리스트의 아이템 표현하는데 item_view.xml 사용
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_view, null);

//       SwipeLayout swipeLayout = view.findViewById(getSwipeLayoutResourceId(position));
        // 더블클릭
//        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
//            @Override
//            public void onDoubleClick(SwipeLayout layout, boolean surface) {
//                Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
//            }
//        });

        // 상세정보(돋보기) 클릭 시
        view.findViewById(R.id.magnifier).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(mContext, "click magnifier", Toast.LENGTH_SHORT).show();

                Intent intent1=new Intent(view.getContext(), Main2Activity.class);
                intent1.putExtra("name", list.get(position).getName());
                intent1.putExtra("district", list.get(position).getDistrict());
                intent1.putExtra("event_type", list.get(position).getEvent_type());
                intent1.putExtra("location", list.get(position).getLocation());
                intent1.putExtra("introduction", list.get(position).getIntroduction());
                intent1.putExtra("page_url", list.get(position).getPage_url());
                mContext.startActivity(intent1);
            }
        });

        return view;
    }

    @Override
    public void fillValues(int position, View convertView) {
        // 그 position의 item 에다가 데이터 채우기
        TextView t = (TextView)convertView.findViewById(R.id.position);
        image = (ImageView)convertView.findViewById(R.id.marketImage);
        t.setText((position + 1) + ".");
        setImage();

        ((TextView) convertView.findViewById(R.id.text_data)).setText(list.get(position).getName());
    }



    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setImage() {
        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                urii = uri.toString();
                Log.d("uri",urii);
                Picasso.with(
                        mContext).
                        load(urii).
                        fit().
                        centerInside().
                        into(image);
            }
        });
    }

}
