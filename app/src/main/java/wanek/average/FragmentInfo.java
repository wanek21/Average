package wanek.average;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentInfo extends Fragment {

    ConstraintLayout infoLayout;
    SharedPreferences sharedPreferences;
    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    static FragmentInfo newInstance(int page) {
        FragmentInfo pageFragment = new FragmentInfo();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        setRetainInstance(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.info_fragment
                ,container,false);
        infoLayout = view.findViewById(R.id.infoLayout);
        setColorViews();
        return view;
    }
    private void setColorViews() {
        switch (sharedPreferences.getInt("colorItem",0)) {
            case 0: infoLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark0));
                break;
            case 1: infoLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark1));
                break;
            case 2: infoLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark2));
                break;
            case 3: infoLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark3));
                break;
            case 4: infoLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark4));
                break;
            case 5: infoLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark5));
                break;
            case 6: infoLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark6));
                break;
            case 7: infoLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark7));
                break;
        }
    }
}
