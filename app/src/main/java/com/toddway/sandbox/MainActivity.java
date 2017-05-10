package com.toddway.sandbox;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.MaterialMenuView;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, CompoundButton.OnCheckedChangeListener, View.OnClickListener {
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
    @InjectView(R.id.edittime)
    EditText edittime;
    public
    @InjectView(R.id.save)
    Button btnsave;
    public
    @InjectView(R.id.spinner)
    Spinner spinner;
    public
    @InjectView(R.id.money)
    EditText money;
    public
    @InjectView(R.id.switchbutton)
    Switch switchbutton;
    public
    @InjectView(R.id.changetime)
    Button changetime;
    public Boolean isincome = true;
    /*。“支出类别”下拉列表的选项可以包括：餐饮，购物，交通，居家生活，其他等。
    “收入类别”下拉列表的选项可以包括：工资，奖金，其他等。*/
    String[] incomedata = new String[]{"工资", "奖金", "其他等"};
    String[] outdata = new String[]{"餐饮", "购物", "交通", "居家生活", "其他等"};
    ArrayAdapter<String> dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        ButterKnife.inject(this);
        initToolbar();
        initView();
    }

    private void initView() {
        switchbutton.setOnCheckedChangeListener(this);
//      初始化spinner数据。
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss     ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        edittime.setText(str);
        setdata(incomedata);
        btnsave.setOnClickListener(this);
        changetime.setOnClickListener(this);

    }

    private void setdata(String[] data) {
        dataAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, data);
        spinner.setAdapter(dataAdapter);
    }

    private void SaveData() {
        SQLiteDatabase database = null;
        try {
            database = MyApplication.getDbtestHelper().getWritableDatabase();
            database.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("data", content.getText().toString());
            values.put("time",edittime.getText().toString());
            values.put("money",money.getText().toString());
            values.put("class",spinner.getSelectedItem().toString());
            if (switchbutton.isChecked()){
               values.put("income","支出");
            }
            else{
                values.put("income","收入");
            }
            long save = database.insert("tbdatatest", null, values);
            if (save == -1) {
                Toasty.error(MainActivity.this, "账本存储出错", Toast.LENGTH_SHORT).show();
            } else {
                database.setTransactionSuccessful();
                Toasty.success(MainActivity.this, "账本存储成功", Toast.LENGTH_SHORT).show();
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
        toolbarTitle.setText("新建账本");
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

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
          Calendar now = Calendar.getInstance();
          edittime.setText(now.get(Calendar.YEAR)+"年"+(now.get(Calendar.MONTH)+1)+"月"+now.get(Calendar.DAY_OF_MONTH)+"日"+"   "+hourOfDay+":"+minute+":"+second);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (switchbutton.isChecked()) {
            switchbutton.setText("新建支出账单");
            isincome = false;
            setdata(outdata);
        } else {
            switchbutton.setText("新建收入账单");
            setdata(incomedata);
            isincome = true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                SaveData();
                break;
            case R.id.changetime:
                ChangeDateandTime();
                break;
        }
    }

    public void ChangeDateandTime() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                MainActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }
}
