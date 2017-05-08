package com.toddway.sandbox;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuDrawable;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import es.dmoral.toasty.Toasty;


public class ThingListFragment extends TransitionHelper.BaseFragment {
    @InjectView(R.id.recycler)
    RecyclerView recyclerView;
    //     recyclerView的适配器
    ThingRecyclerAdapter recyclerAdapter = new ThingRecyclerAdapter();

    public ThingListFragment() {

    }

    List<Thing> datas = new ArrayList<Thing>();

    //创建fragment视图函数。
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_thing_list, container, false);
        ButterKnife.inject(this, rootView);
        initRecyclerView();
        return rootView;
    }

    //初始化recycleView函数。
    private void initRecyclerView() {
        recyclerAdapter.updateList(getThing());
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<Thing>() {
            @Override
            public void onItemClick(View view, Thing item, boolean isLongClick) {
                if (isLongClick) {
                    BaseActivity.of(getActivity()).animateHomeIcon(MaterialMenuDrawable.IconState.X);
                } else {
                    Navigator.launchDetail(BaseActivity.of(getActivity()), view, item, recyclerView);
                }
            }
        });

        BaseActivity.of(getActivity()).fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
//从数据库中读取数据。

    private List<Thing> getThing() {
        SQLiteDatabase database = null;
        try {
            datas.clear();
            database = MyApplication.getDbtestHelper().getWritableDatabase();
            database.beginTransaction();
            Cursor cursor = database.query("tbdata", null, null, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int ID = cursor.getInt(cursor.getColumnIndex("id"));
                    String data = cursor.getString(cursor.getColumnIndex("data"));
                    datas.add(new Thing(ID, data));
                }
                database.setTransactionSuccessful();
            } else {
                Toasty.error(MyApplication.getContext(), "数据初始化失败", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.endTransaction();
            }
        }
        return datas;
    }

    @Override
    public boolean onBeforeBack() {
        BaseActivity activity = BaseActivity.of(getActivity());
        if (!activity.animateHomeIcon(MaterialMenuDrawable.IconState.BURGER)) {
            activity.drawerLayout.openDrawer(Gravity.START);
        }
        return super.onBeforeBack();
    }

}

