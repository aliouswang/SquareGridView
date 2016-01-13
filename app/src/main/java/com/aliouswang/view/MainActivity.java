package com.aliouswang.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.aliouswang.view.library.SquareGridView;
import com.aliouswang.view.library.SquareViewAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private ArrayList<Diary> mDiaryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initData();
        initView();
    }

    private void initData() {
        mDiaryList = new ArrayList<>(50);
        for (int i = 0; i < 50; i++) {
            mDiaryList.add(new Diary(i));
        }
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.listview);
        DemoListAdapter listAdapter = new DemoListAdapter();
        mListView.setAdapter(listAdapter);
    }

    private class DemoListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 50;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this)
                        .inflate(R.layout.nine_grid_list_item_layout, null);
                viewHolder = new ViewHolder();
                viewHolder.squareGridView = (SquareGridView) convertView
                        .findViewById(R.id.square_grid_view);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final Diary diary = mDiaryList.get(position);
            SquareViewAdapter<Diary> adapter = new SquareViewAdapter<Diary>() {
                @Override
                public int getCount() {
                    return diary.photos.size();
                }

                @Override
                public Diary getItem(int position) {
                    return diary;
                }

                @Override
                public String getImageUrl(int position) {
                    return diary.photos.get(position);
                }

                @Override
                public void onItemClick(View view, int index, Diary t) {
                    Toast.makeText(MainActivity.this,
                            "index :" + index, Toast.LENGTH_SHORT).show();
                }
            };
            viewHolder.squareGridView.setAdapter(adapter);
            return convertView;
        }

        class ViewHolder {
            SquareGridView squareGridView;
        }
    }

}
