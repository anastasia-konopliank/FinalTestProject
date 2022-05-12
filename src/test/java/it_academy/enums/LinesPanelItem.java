package it_academy.enums;

public enum LinesPanelItem {
    AVG_FILL_PRICE("Avg fill price"),
    MID_PRICE("Mid price");

    private String displayName;

    LinesPanelItem(String displayName) {

        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
