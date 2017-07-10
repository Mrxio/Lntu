package cn.net.ntech.lntu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import cn.net.ntech.lntu.R;

public class AddClassActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        initView();
    }

    private void initView() {
        setTitle("添加课程");
        findViewById(R.id.bt_addclass_add).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_addclass_add:
                Toast.makeText(this, "开发中", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
