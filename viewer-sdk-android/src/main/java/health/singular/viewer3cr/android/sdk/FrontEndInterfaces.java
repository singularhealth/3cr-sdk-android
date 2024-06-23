package health.singular.viewer3cr.android.sdk;

public enum FrontEndInterfaces {
    SCAN_LOADING("scan_loading"),
    FILE_MANAGEMENT("file_management"),
    VIEW_SELECTION("view_selection"),
    LAYOUT("layout"),
    PRESETS("presets"),
    SLIDERS("sliders"),
    SCAN_ORIENTATION("scan_orientation"),
    SCAN_MOVEMENT("scan_movement"),
    INTERACTIVITY("interactivity");

    private final String value;

    FrontEndInterfaces(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}