package cn.net.ntech.lntu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.NumberPicker;
import android.widget.TextView;
import cn.net.ntech.lntu.R;

/**
 * Created by NTECHER on 2016/3/28.
 */
public class TermSelectDialog extends Dialog{
    private Context context;
    private TextView queding;
    private View.OnClickListener listener;
    public TermSelectDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_termselect);
        final NumberPicker np_year = (NumberPicker) findViewById(R.id.np_year);
        final NumberPicker np_term = (NumberPicker)findViewById(R.id.np_term);
        queding = (TextView) findViewById(R.id.tv_dialog_terselect_queding);
        np_year.setMinValue(0);
        np_year.setMaxValue(3);
        np_year.setDisplayedValues(new String[]{"全部", "2014", "2015", "2016"});
        np_term.setMinValue(0);
        np_term.setMaxValue(2);
        np_term.setDisplayedValues(new String[]{"全部", "春", "秋"});
        queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = np_year.getValue();
                int term = np_term.getValue();

                dismiss();
            }
        });
    }
}
