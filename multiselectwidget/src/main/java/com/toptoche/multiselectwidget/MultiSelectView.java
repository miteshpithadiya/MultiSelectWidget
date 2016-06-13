package com.toptoche.multiselectwidget;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.toptoche.multiselectwidget.MultiSelectFragment;

import java.util.ArrayList;
import java.util.List;

public class MultiSelectView extends TextView implements View.OnClickListener,
        MultiSelectFragment.MultiSelectCallback {

    private Context _context;
    private List _selectedItems;
    private MultiSelectFragment _multiSelectFragment;
    private String _strDelimiter;

    public MultiSelectView(Context context) {
        super(context);
        this._context = context;
        init();
    }

    public MultiSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this._context = context;
        init();
    }

    public MultiSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this._context = context;
        init();
    }

    private void init() {
        _selectedItems = new ArrayList();
        _multiSelectFragment = MultiSelectFragment.newInstance();
        _multiSelectFragment.setOnMultiSelectItemListener(this);
        if (null == getHint() || TextUtils.isEmpty(getHint().toString())) {
            setHint("Tap to select");
        } else {
            setHint(getHint());
        }
        setOnClickListener(this);
    }

    private Activity scanForActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof Activity)
            return (Activity) cont;
        else if (cont instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper) cont).getBaseContext());

        return null;
    }

    @Override
    public void onClick(View v) {
        _multiSelectFragment.show(scanForActivity(_context).getFragmentManager(), "TAG");
    }

    public void setAdapter(ArrayAdapter adapter, List selectedItems) {
        _selectedItems.addAll(selectedItems);

        final StringBuilder strItems = new StringBuilder();

        final String strDelimiter = _strDelimiter == null?" | ":_strDelimiter;
        for (Object object : selectedItems) {
            strItems.append(object.toString()).append(strDelimiter);
        }

        if (strItems.length() > 0) {
            // Trimming the last semicolon.
            setText(strItems.substring(0, strItems.length() - strDelimiter.length()));
        } else {
            setText("");
            if (null == getHint() || TextUtils.isEmpty(getHint().toString())) {
                setHint("Tap to select");
            } else {
                setHint(getHint());
            }
        }

        _multiSelectFragment.setAdapter(adapter, selectedItems);
    }

    @Override
    public void onItemsSelected(List items) {
        _selectedItems.clear();
        _selectedItems.addAll(items);
        final StringBuilder strItems = new StringBuilder();

        final String strDelimiter = _strDelimiter == null?" | ":_strDelimiter;
        for (Object object : items) {
            strItems.append(object.toString()).append(strDelimiter);
        }

        if (strItems.length() > 0) {
            // Trimming the last semicolon.
            setText(strItems.substring(0, strItems.length() - strDelimiter.length()));
        } else {
            setText("");
            if (null == getHint() || TextUtils.isEmpty(getHint().toString())) {
                setHint("Tap to select");
            } else {
                setHint(getHint());
            }
        }
    }

    public void setDelimiter(String strDelimiter){
        this._strDelimiter = strDelimiter;
    }

    public void setTitle(String strTitle) {
        _multiSelectFragment.setTitle(strTitle);
    }

    public void setPositiveButton(String strPositiveButtonText) {
        _multiSelectFragment.setPositiveButton(strPositiveButtonText);
    }

    public void setPositiveButton(String strPositiveButtonText, MultiSelectFragment.OnPositiveButtonClicked onClickListener) {
        _multiSelectFragment.setPositiveButton(strPositiveButtonText, onClickListener);
    }

    public void setNegativeButton(String strNegativeButtonText) {
        _multiSelectFragment.setNegativeButton(strNegativeButtonText);
    }

    public void setNegativeButton(String strNegativeButtonText, MultiSelectFragment.OnNegativeButtonClicked onClickListener) {
        _multiSelectFragment.setNegativeButton(strNegativeButtonText, onClickListener);
    }

    public void setOnNoItemSelectedListener(MultiSelectFragment.OnNoItemSelected onNoItemSelected) {
        _multiSelectFragment.setOnNoItemSelectedListener(onNoItemSelected);
    }

    /**
     * @return The data corresponding to the currently selected item, or
     * null if there is nothing selected.
     */
    public List getSelectedItems() {
        return _selectedItems;
    }
}
