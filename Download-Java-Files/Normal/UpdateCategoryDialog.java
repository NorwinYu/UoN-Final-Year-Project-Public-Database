package com.msproject.myhome.mydays;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;

public class UpdateCategoryDialog extends Dialog implements View.OnClickListener{
    private static final int LAYOUT = R.layout.dialog_update_category;
    private Context context;
    private EditText categoryEditText;
    private LinearLayout categoryColorSetectButton;
    private Button submitButton;
    private Button cancelButton;
    private String pickedColor;
    private static final int DIALOG_ID = 0;

    private MyDialogListener dialogListener;
    private boolean isModify;//ture이면 버튼을 수정으로 변경, textView를 readOnly로!
    private int modifyIndex;
    private Category modifyCategory;

    public UpdateCategoryDialog(Context context) {
        super(context);
        this.context = context;
        this.pickedColor = "#61efc6";
    }

    public UpdateCategoryDialog(Context context, Category category, int index){//수정인지 판별 필요
        super(context);
        this.context = context;
        this.isModify = true;
        this.modifyIndex = index;
        this.modifyCategory = category;
    }

    public String getPickedColor() {
        return pickedColor;
    }

    public void setPickedColor(String pickedColor) {
        this.pickedColor = pickedColor;
        categoryColorSetectButton.setBackgroundColor(Color.parseColor(this.pickedColor));
    }

    public void setDialogListener(MyDialogListener myDialogListener){
        this.dialogListener = myDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        categoryEditText = findViewById(R.id.category_edittext);
        categoryColorSetectButton = findViewById(R.id.color_selec_bt);
        submitButton = findViewById(R.id.submit_bt);
        cancelButton = findViewById(R.id.cancel_bt);

        if(isModify){//카테고리 수정을 통해 Dialog에 접근할 경우, 확인 버튼을 수정으로 변경, 카테고리 이름을 입력하는 TextView를 readOnly로 변경, 기본 색상을 변경 전 색상으로 초기화.
            submitButton.setText("수정");
            categoryEditText.setText(modifyCategory.getCategoryName());
            categoryEditText.setEnabled(false);
            setPickedColor(modifyCategory.getColor());
        }
        submitButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        categoryColorSetectButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit_bt://카테고리를 추가할 경우, null스트링이면 메세지 출력
                String categoryName = categoryEditText.getText().toString();
                if(categoryName.equals("")){
                    Toast.makeText(context, "카테고리의 이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(isModify){//카테고리 수정일 경우 SetCategoryActivity로 onModifyClicked event 발생
                    dialogListener.onModifyClicked(new Category(categoryName, pickedColor), modifyIndex);
                }
                else{//새로운 카테고리 추가일 경우 onPostClicked event 발생
                    dialogListener.onPostClicked(new Category(categoryName, pickedColor));
                }
                dismiss();
                break;
            case R.id.cancel_bt:
                dismiss();
                break;
            case R.id.color_selec_bt://색을 변경하기 위한 view를 클릭할 경우, ColorPickerDialog 출력. SetCategoryActivity의 ColorPickerDialogListener를 통해 색의 변화를 콜백받는다.
                ColorPickerDialog.Builder cp = ColorPickerDialog.newBuilder();
                cp.setDialogType(ColorPickerDialog.TYPE_CUSTOM);
                cp.setAllowPresets(false);
                cp.setColor(Color.BLACK);
                cp.setDialogId(DIALOG_ID);
                cp.setShowAlphaSlider(true);
                cp.show((Activity)context);

                break;
        }
    }

}
