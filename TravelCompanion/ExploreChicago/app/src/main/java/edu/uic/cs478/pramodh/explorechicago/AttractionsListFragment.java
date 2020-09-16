package edu.uic.cs478.pramodh.explorechicago;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import java.util.Objects;

public class AttractionsListFragment extends ListFragment {

    private ListSelectionListener mListener = null;

    public interface ListSelectionListener {
        void onListSelection(int index);
    }

    @Override
    public void onListItemClick(@NonNull ListView lstView, @NonNull View view, int pos, long id) {
        mListener.onListSelection(pos);
        lstView.setItemChecked(pos, true);
    }

    @Override
    public void onAttach(@NonNull Context activity) {
        super.onAttach(activity);
        try {
            mListener = (ListSelectionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement ListSelectionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);
        assert getArguments() != null;
        setListAdapter(new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.list_item, Objects.requireNonNull(getArguments().getStringArray("list_data"))));
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }
}
