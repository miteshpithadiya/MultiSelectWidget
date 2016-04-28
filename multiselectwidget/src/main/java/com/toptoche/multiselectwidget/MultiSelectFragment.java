package com.toptoche.multiselectwidget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiSelectFragment extends DialogFragment {

    private ArrayAdapter _listAdapter;

    private List _listSelectedItems;

    private String _strTitle;

    private String _strPositiveButtonText;
    private String _strNegativeButtonText;

    private MultiSelectCallback _multiSelectCallback;
    private Map<Integer, Object> _mapSelectedItems;
    private MultiSelectedItemsAdapter _selectedItemsAdapter;
    private OnNoItemSelected _onNoItemSelected;
    private OnPositiveButtonClicked _onPositiveButtonClicked;
    private OnNegativeButtonClicked _onNegativeButtonClicked;

    public MultiSelectFragment() {

    }

    public static MultiSelectFragment newInstance() {
        return new MultiSelectFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Getting the layout inflater to inflate the view in an alert dialog.
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        View rootView = inflater.inflate(R.layout.multiselect, null);
        setData(rootView);

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setView(rootView);

        String strPositiveButton = _strPositiveButtonText == null ? "OK" : _strPositiveButtonText;
        alertDialog.setPositiveButton(strPositiveButton, null);

        String strNegativeButton = _strNegativeButtonText == null ? "CANCEL" :
                _strNegativeButtonText;
        alertDialog.setNegativeButton(strNegativeButton, null);

        alertDialog.setNeutralButton("CLEAR", null);

        String strTitle = _strTitle == null ? "Select Item" : _strTitle;
        alertDialog.setTitle(strTitle);

        final AlertDialog dialog = alertDialog.create();

        dialog.setCancelable(false);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface d) {
                Button btnSave = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _mapSelectedItems.clear();
                        if (_onNoItemSelected != null && _listSelectedItems.isEmpty()) {
                            _onNoItemSelected.onNoItemSelected(getDialog());
                        } else if (_onPositiveButtonClicked != null) {
                            for (Object object : _listSelectedItems) {
                                _mapSelectedItems.put(object.hashCode(), object);
                            }
                            _multiSelectCallback.onItemsSelected(_listSelectedItems);
                            _onPositiveButtonClicked.onPositiveButtonClicked(getDialog());
                        } else {
                            for (Object object : _listSelectedItems) {
                                _mapSelectedItems.put(object.hashCode(), object);
                            }
                            _multiSelectCallback.onItemsSelected(_listSelectedItems);
                            getDialog().dismiss();
                        }
                    }
                });

                Button btnCancel = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _listSelectedItems.clear();
                        _listSelectedItems.addAll(_mapSelectedItems.values());
                        _multiSelectCallback.onItemsSelected(_listSelectedItems);
                        if (null != _onNegativeButtonClicked) {
                            _onNegativeButtonClicked.onNegativeButtonClicked(getDialog());
                        } else {
                            getDialog().dismiss();
                        }
                    }
                });

                Button btnClear = dialog.getButton(DialogInterface.BUTTON_NEUTRAL);
                btnClear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _listSelectedItems.clear();
                        _selectedItemsAdapter.notifyDataSetChanged();
                        _multiSelectCallback.onItemsSelected(_listSelectedItems);
                    }
                });
            }
        });

        return dialog;
    }

    private void setData(View rootView) {
        final TextView txtSelected = (TextView) rootView.findViewById(R.id.txtSelected);

        GridView lnrSelectedItems = (GridView) rootView.findViewById(R.id.lnrSelectedItems);

        _selectedItemsAdapter = new MultiSelectedItemsAdapter
                (getActivity(), R.layout.removable_cell, _listSelectedItems);
        lnrSelectedItems.setAdapter(_selectedItemsAdapter);
        txtSelected.setText("Selected " + " (" + _selectedItemsAdapter.getCount() + ")");

        ListView listView = (ListView) rootView.findViewById(R.id.expandableList);

        listView.setAdapter(_listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (!_listSelectedItems.contains(_listAdapter.getItem(position))) {
                    _listSelectedItems.add(_listAdapter.getItem(position));
                    _selectedItemsAdapter.notifyDataSetChanged();
                }
            }
        });

        _selectedItemsAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                txtSelected.setText("Selected " + " (" + _selectedItemsAdapter.getCount() + ")");
            }
        });
    }

    public void setAdapter(ArrayAdapter adapter, List selectedItems) {
        _listAdapter = adapter;

        _mapSelectedItems = new HashMap<>();
        for (Object object : selectedItems) {
            _mapSelectedItems.put(object.hashCode(), object);
        }

        _listSelectedItems = selectedItems;
    }

    public void setTitle(String strTitle) {
        _strTitle = strTitle;
    }

    public void setPositiveButton(String strPositiveButtonText) {
        _strPositiveButtonText = strPositiveButtonText;
    }

    public void setPositiveButton(String strPositiveButtonText, OnPositiveButtonClicked onClickListener) {
        _strPositiveButtonText = strPositiveButtonText;
        _onPositiveButtonClicked = onClickListener;
    }

    public void setNegativeButton(String strNegativeButtonText) {
        _strNegativeButtonText = strNegativeButtonText;
    }

    public void setNegativeButton(String strNegativeButtonText, OnNegativeButtonClicked onClickListener) {
        _strNegativeButtonText = strNegativeButtonText;
        _onNegativeButtonClicked = onClickListener;
    }

    public void setOnMultiSelectItemListener(MultiSelectCallback multiSelectCallback) {
        this._multiSelectCallback = multiSelectCallback;
    }

    public void setOnNoItemSelectedListener(OnNoItemSelected onNoItemSelected) {
        this._onNoItemSelected = onNoItemSelected;
    }

    public interface MultiSelectCallback<T> {
        void onItemsSelected(List<T> items);
    }

    public interface OnNoItemSelected {
        void onNoItemSelected(Dialog dialog);
    }

    public interface OnPositiveButtonClicked {
        void onPositiveButtonClicked(Dialog dialog);
    }

    public interface OnNegativeButtonClicked {
        void onNegativeButtonClicked(Dialog dialog);
    }
}