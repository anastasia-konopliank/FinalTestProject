package it_academy.pages;

public class ScatterPlotPageSnapshot {

    private String xAttribute;
    private String yAttribute;
    private String xValue;
    private String yValue;
    private String xName;
    private String yName;

    public ScatterPlotPageSnapshot(
            String xAttribute,
            String yAttribute,
            String xValue,
            String yValue,
            String xName,
            String yName
    ){

        this.xAttribute = xAttribute;
        this.yAttribute = yAttribute;
        this.xValue = xValue;
        this.yValue = yValue;
        this.xName = xName;
        this.yName = yName;
    }

    public String getValue(String axe) {
        if (axe.equals("X")) {
            return xValue;
        } else {
            return yValue;
        }
    }

    public String getXAttribute() {
        return xAttribute;
    }

    public String getAttribute(String axe) {
        if (axe.equals("X")) {
            return xAttribute;
        } else {
            return yAttribute;
        }
    }

    public String getYAttribute() {
        return yAttribute;
    }

    public String getNameOf(String axe) {
        if (axe.equals("X")) {
            return xName;
        } else {
            return yName;
        }
    }
}
