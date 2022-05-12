package it_academy.enums;

public enum TabName {
    SUMMARY("Summary"),
    GRID_AND_CHART("Grid & chart"),
    HISTOGRAM("Histogram"),
    SCATTER_PLOT("Scatter-plot"),
    REPORTS("Reports");

    private String displayName;

    TabName(String displayName) {

        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

