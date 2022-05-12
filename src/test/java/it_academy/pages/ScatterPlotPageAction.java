package it_academy.pages;

public class ScatterPlotPageAction {
    private ScatterPlotPageSnapshot before;
    private ScatterPlotPageSnapshot after;

    public ScatterPlotPageAction(ScatterPlotPageSnapshot before, ScatterPlotPageSnapshot after) {

        this.before = before;
        this.after = after;
    }

    public ScatterPlotPageSnapshot getAfter() {
        return after;
    }

    public String getDifference() {
        if (!before.getXAttribute().equals(after.getXAttribute())){
            return "Change X Attribute state from (" + before.getXAttribute() + "," + after.getXAttribute() + ")";
        } else {
            return "Change Y Attribute state from (" + before.getYAttribute() + "," + after.getYAttribute() + ")";
        }
    }
}
