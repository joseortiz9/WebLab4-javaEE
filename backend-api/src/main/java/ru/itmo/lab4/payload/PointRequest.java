package ru.itmo.lab4.payload;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PointRequest {
    @NotNull
    @Min(-5)
    @Max(3)
    private Double x, r;

    @NotNull
    @Min(-5)
    @Max(5)
    private Double y;

    public PointRequest() {}

    public PointRequest(Double x, Double y, Double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = r;
    }
}
