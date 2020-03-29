package p.gordenyou.pdalibrary.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.InverseBindingMethod;
import androidx.databinding.InverseBindingMethods;

import p.gordenyou.pdalibrary.R;


/**
 * Created by Gordenyou on 2018/3/2.
 */
@InverseBindingMethods({
        @InverseBindingMethod(type = ScannerView.class
                , attribute = "scanner_edit_text"
                , event = "editTextAttrChanged")
})
public class ScannerView extends LinearLayout {
    private EditText editText;
    private TextView title;
    private Button button;

    private int length = 0;

    public ScannerView(Context context) {
        this(context, null);
    }

    public ScannerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("ClickableViewAccessibility")
    public ScannerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_scanner_wide, this);
        editText = view.findViewById(R.id.scanner_edittext);
        title = view.findViewById(R.id.scanner_title);
        button = view.findViewById(R.id.scanner_button);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScannerView);

        title.setText(a.getText(R.styleable.ScannerView_scannertitle));
        editText.setHint(a.getText(R.styleable.ScannerView_scannerhint));
        editText.setText(a.getText(R.styleable.ScannerView_scanner_edit_text));
//        if (a.getBoolean(R.styleable.ScannerView_editable, false)){
//            editText.setFocusable(true);
//        }else{
//            editText.setFocusable(false);
//        }
        if(a.getText(R.styleable.ScannerView_scannerbuttonname) != null && !a.getText(R.styleable.ScannerView_scannerbuttonname).equals("")){
            button.setText(a.getText(R.styleable.ScannerView_scannerbuttonname));
        }
        if (a.getBoolean(R.styleable.ScannerView_buttonvisable, true)) {
            button.setVisibility(VISIBLE);
        } else {
            button.setVisibility(GONE);
        }

//        button.setOnClickListener(v -> button.requestFocus());

        editText.setOnTouchListener((v, event) -> {
//                Log.e(TAG,"触摸监听被触发"+System.currentTimeMillis());
//            editText.requestFocus();
            editText.setSelection(editText.getText().length());
            return false;
        });

        a.recycle();
    }

    public String getTitle() {
        return title.getText().toString();
    }

    public EditText getEditText() {
        return editText;
    }

    public Button getButton() {
        return button;
    }

    public String getText() {
        return editText.getText().toString().trim();
    }

    public void setText(String text) {
        editText.setText(text);
    }

    public void setScanner_edit_text(String text) {
        editText.setText(text);
    }

    public String getScanner_edit_text() {
        return editText.getText().toString();
    }

    public void setEditTextAttrChanged(final InverseBindingListener listener) {
        if (listener != null) {
            editText.setOnFocusChangeListener((view, b) -> listener.onChange());
//            editText.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    listener.onChange();
//                }
//            });
        }
    }
}
