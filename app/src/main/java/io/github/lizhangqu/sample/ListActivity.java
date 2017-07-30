package io.github.lizhangqu.sample;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import io.github.lizhangqu.fresco.FrescoLoader;

public class ListActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView = (ListView) findViewById(R.id.listview);
        adapter = new ArrayAdapter<String>(this, R.layout.list_item) {
            public final String[] imageUrls = new String[]{
                    "http://img.my.csdn.net/uploads/201508/05/1438760758_3497.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760758_6667.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760757_3588.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760756_3304.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760755_6715.jpeg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760726_5120.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760726_8364.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760725_4031.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760724_9463.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760724_2371.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760707_4653.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760706_6864.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760706_9279.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760704_2341.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760704_5707.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760685_5091.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760685_4444.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760684_8827.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760683_3691.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760683_7315.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760663_7318.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760662_3454.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760662_5113.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760661_3305.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760661_7416.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760589_2946.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760589_1100.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760588_8297.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760587_2575.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760587_8906.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760550_2875.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760550_9517.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760549_7093.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760549_1352.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760548_2780.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760531_1776.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760531_1380.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760530_4944.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760530_5750.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760529_3289.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760500_7871.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760500_6063.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760499_6304.jpeg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760499_5081.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760498_7007.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760478_3128.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760478_6766.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760477_1358.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760477_3540.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760476_1240.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760446_7993.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760446_3641.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760445_3283.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760444_8623.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760444_6822.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760422_2224.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760421_2824.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760420_2660.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760420_7188.jpg",
                    "http://img.my.csdn.net/uploads/201508/05/1438760419_4123.jpg",
            };

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                String url = getItem(position);
                View view;
                ViewHolder viewHolder;
                if (convertView == null) {
                    view = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.image = (ImageView) view.findViewById(R.id.image);
                    view.setTag(viewHolder);
                } else {
                    view = convertView;
                    viewHolder = (ViewHolder) view.getTag();
                }
                FrescoLoader.with(getContext())
                        .fadeDuration(300)
                        .placeholder(new ColorDrawable(Color.GRAY))
                        .load(url)
                        .compatTemporaryDetach(true)//compat
                        .progressiveRenderingEnabled(true)
                        .actualScaleType(ImageView.ScaleType.CENTER_CROP)
                        .into(viewHolder.image);
                return view;
            }

            @Override
            public int getCount() {
                return imageUrls.length;
            }

            @Nullable
            @Override
            public String getItem(int position) {
                return imageUrls[position];
            }

            class ViewHolder {
                ImageView image;
            }

        };
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
