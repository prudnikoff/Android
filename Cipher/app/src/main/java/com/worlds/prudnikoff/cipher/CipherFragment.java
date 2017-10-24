package com.worlds.prudnikoff.cipher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CipherFragment extends Fragment implements View.OnClickListener {

    private Button cryptButton;
    private EditText regStateEditText;
    private TextView generatedKeyTextView;
    private int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.cipher_fragment, container, false);
        Bundle args = getArguments();
        this.position = args.getInt("position");
        cryptButton = (Button)rootView.findViewById(R.id.crypt_button);
        regStateEditText = (EditText)rootView.findViewById(R.id.regState_editText);
        generatedKeyTextView = (TextView)rootView.findViewById(R.id.generatedKey_textView);
        cryptButton.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        String key = regStateEditText.getText().toString();
        try {
            if (key.length() > 0) {
                if (v.getId() == cryptButton.getId()) MainActivity.crypt(position, key);
            } else
                Toast.makeText(getContext(), "Sorry, kye field can't be empty", Toast.LENGTH_SHORT).show();

        } catch (Exception ex) {
            Toast.makeText(getContext(), "Input error", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }

    void addKeyLog(String keyLog) {
        generatedKeyTextView.setText(keyLog);
    }
}
