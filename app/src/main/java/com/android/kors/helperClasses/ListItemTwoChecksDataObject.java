package com.android.kors.helperClasses;

public class ListItemTwoChecksDataObject {
    private String itemText = "";
    private boolean checked1 = false;
    private boolean checked2= false;

    public String getItemText() {
        return itemText;
    }
    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    public boolean isChecked1() {
        return checked1;
    }
    public void setChecked1(boolean checked) {
        this.checked1 = checked;
    }

    public boolean isChecked2() {
        return checked2;
    }
    public void setChecked2(boolean checked) {
        this.checked2 = checked;
    }
}
