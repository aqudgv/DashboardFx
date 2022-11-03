package io.github.gleidsonmt.dashboardfx.core.app.view_wrapper;

public enum BreakPoints {

    X_SMALL(576),
    SMALL(576),
    MEDIUM(768),
    LARGE(992),
    x_LARGE(1200),
    XX_LARGE(1400);

    private final int size;

    BreakPoints(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

}
