package com.android.kors.helperClasses;

public class ListViewItemDataObject {
    private boolean checked;
    private String itemText;

    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getItemText() {
        return itemText;
    }
    public void setItemText(String itemText) {
        this.itemText = itemText;
    }
}
