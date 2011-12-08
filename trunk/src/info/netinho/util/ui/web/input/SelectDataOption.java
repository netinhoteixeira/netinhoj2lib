package info.netinho.util.ui.web.input;

public class SelectDataOption {

    private String value;
    private String label;

    public SelectDataOption() {
        this.value = "";
        this.label = "";
    }

    public SelectDataOption(String label) {
        this.value = label;
        this.label = label;
    }

    public SelectDataOption(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}