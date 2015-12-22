package activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import pl.pawelfrydrych.CHAPS.R;

public class SimpleAuthFragment extends Fragment {

    private static final String Login = "chaps";
    private static final String Password = "chaps";
    private EditText loginEditText, passwordEditText;
    private Button zalogujButton;
    private CheckBox checkBox;
    private String LoginFromSharedPreferenes, PasswordFromSharedPreferences;

    public SimpleAuthFragment()
    {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_auth,container,false);
         loginEditText = (EditText) rootView.findViewById(R.id.loginEditText);
         passwordEditText = (EditText) rootView.findViewById(R.id.passwordEditText);
        zalogujButton = (Button) rootView.findViewById(R.id.zalogujButton);
        checkBox = (CheckBox) rootView.findViewById(R.id.checkBoxauth);

        loadPreferences();

        zalogujButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loginEditText.getText().toString().equalsIgnoreCase(Login) && passwordEditText.getText().toString().equalsIgnoreCase(Password))
                {
                    if(checkBox.isChecked())
                    {
                        savePreferences();;
                    }

                    Fragment nuty = new PodzialNutFragment();
                    if(nuty != null)
                    {

                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container_body,nuty);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                    }
                }
                else
                {
                    Toast.makeText(getActivity(),"Nieprawid³owe dane logowania",Toast.LENGTH_LONG).show();
                }
            }
        });

        return rootView;
    }

    private void savePreferences()
    {

        SharedPreferences preferences = getActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        if(checkBox.isChecked() == true)
        {
            editor.putString("LOGIN", loginEditText.getText().toString());
            editor.putString("PASSWORD", passwordEditText.getText().toString());
            editor.putBoolean("checkbox",true);

            checkBox.setChecked(true);
        }else
        {
            editor.putString("LOGIN","");
            editor.putString("PASSWORD","");
            editor.putBoolean("checkbox",false);

            checkBox.setChecked(false);
        }

        editor.commit();
    }

    private void loadPreferences()
    {
        SharedPreferences preferences = getActivity().getSharedPreferences("preferences",Context.MODE_PRIVATE);
        LoginFromSharedPreferenes = preferences.getString("LOGIN","");
        PasswordFromSharedPreferences = preferences.getString("PASSWORD","");


        loginEditText.setText(preferences.getString("LOGIN",""));
        passwordEditText.setText(preferences.getString("PASSWORD",""));
        checkBox.setChecked(preferences.getBoolean("checkbox",true));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
