package com.toddway.sandbox;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.MaterialMenuView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {
    public
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    public
    @InjectView(R.id.toolbar_title)
    TextView toolbarTitle;
    public
    @InjectView(R.id.material_menu_button)
    MaterialMenuView homeButton;
    private MaterialMenuDrawable.IconState currentIconState;
    public
    @InjectView(R.id.content)
    EditText content;
    public
    @InjectView(R.id.save)
    Button btnsave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        ButterKnife.inject(this);
        initToolbar();
        initView();
    }

    private void initView() {
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveData();
            }
        });
    }

    private void SaveData() {
        SQLiteDatabase database = null;
        try {
            database = MyApplication.getDbtestHelper().getWritableDatabase();
            database.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("data", content.getText().toString());
            long save = database.insert("tbdata", null, values);
            if (save == -1) {
                Toasty.error(MainActivity.this, "数据存储出错", Toast.LENGTH_SHORT).show();
            } else {
                database.setTransactionSuccessful();
                Toasty.success(MainActivity.this, "数据存储成功", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.endTransaction();
            }
        }

    }


//    使用数据库存储数据

    private void initToolbar() {
        toolbarTitle.setText("数据传送");
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle("");
            animateHomeIcon(MaterialMenuDrawable.IconState.ARROW);
            homeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }


    public boolean animateHomeIcon(MaterialMenuDrawable.IconState iconState) {
        if (currentIconState == iconState) return false;
        currentIconState = iconState;
        homeButton.animateState(currentIconState);
        return true;
    }

    public void setHomeIcon(MaterialMenuDrawable.IconState iconState) {
        if (currentIconState == iconState) return;
        currentIconState = iconState;
        homeButton.setState(currentIconState);
    }

}
