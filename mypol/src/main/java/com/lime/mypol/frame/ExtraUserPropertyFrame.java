package com.lime.mypol.frame;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.lime.mypol.R;

import java.util.HashMap;
import java.util.Map;

public class ExtraUserPropertyFrame extends FrameLayout {

    /**
     * debug TAG
     */
    private final String TAG = "KakaoExtraUserPropertyLayout";

    // property key
    private  static final String ADDRESS_KEY = "address";
    private  static final String BIRTHDAY_KEY = "birthday";
    private  static final String GENDER_KEY = "gender";

    private EditText address;
    private EditText birthday;
    private Spinner gender;

    public ExtraUserPropertyFrame(Context context) {
        super(context);
    }

    public ExtraUserPropertyFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExtraUserPropertyFrame(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onAttachedToWindow () {
        super.onAttachedToWindow();
        final View view = inflate(getContext(), R.layout.kakao_extra_user_property, this);
        address = (EditText) view.findViewById(R.id.address);
        birthday = (EditText) view.findViewById(R.id.birthday);
        gender = (Spinner) view.findViewById(R.id.gender);
    }

    public HashMap<String, String> getProperties(){
        String strAddress = address.getText().toString();

        String strBirthday = birthday.getText().toString();
//        Date date = new Date(birthday.getYear(), birthday.getMonth(), birthday.getDayOfMonth());
//        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String strBirthday = transFormat.format(date);

        String strGenger = String.valueOf(gender.getSelectedItem());

        HashMap<String, String> properties = new HashMap<String, String>();
        if(strAddress != null)
            properties.put(ADDRESS_KEY, strAddress);
        if(strBirthday != null)
            properties.put(BIRTHDAY_KEY, strBirthday);
        if(strGenger != null)
            properties.put(GENDER_KEY, strGenger);

        return properties;
    }

    public void showProperties(final Map<String, String> properties) {
        final String strAddress = properties.get(ADDRESS_KEY);
        if (strAddress != null)
            address.setText(strAddress);

        final String strBirthday = properties.get(BIRTHDAY_KEY);
//        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = null;
//        try {
//            date = transFormat.parse(strBirthday);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        if (strBirthday != null)
            birthday.setText(strAddress);
//            birthday.init(date.getYear(), date.getMonth(), date.getDay(), new DatePicker.OnDateChangedListener() {
//                @Override
//                public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
//
//                }
//            });

        final String strGenger = properties.get(GENDER_KEY);
        if (strGenger != null) {
            ArrayAdapter<String> myAdap = (ArrayAdapter<String>) gender.getAdapter(); //cast to an ArrayAdapter
            int spinnerPosition = myAdap.getPosition(strGenger);
            gender.setSelection(spinnerPosition);
        }
    }


}
