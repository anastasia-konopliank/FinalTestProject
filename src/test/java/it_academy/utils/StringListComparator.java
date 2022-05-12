package it_academy.utils;

public class StringListComparator {
    private String[] first;
    private String[] second;
    private String reason;
    private boolean result;

    public StringListComparator(String[] first, String[] second) {

        this.first = first;
        this.second = second;

        if (first.length != second.length) {
            result = false;
            reason = "Collections are with different size: " + first.length + " and " + second.length;
            return;
        }

        for (var i = 0; i < first.length; i++) {
            if (!first[i].equals(second[i])) {
                result = false;
                reason = "Collection different at " + i + " [" + first[i] + "] and [" + second[i] + "]";
                return;
            }
        }

        result = true;
        reason = "";
    }

    public boolean areSameByContent() {
        return result;
    }

    public String getReason() {
        return reason;
    }
}
