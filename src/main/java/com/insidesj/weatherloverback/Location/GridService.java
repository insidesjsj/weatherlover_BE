package com.insidesj.weatherloverback.Location;

import org.springframework.stereotype.Service;

@Service
public class GridService {

    private String nx;
    private String ny;

    public void setGridValues(String nx, String ny) {
        this.nx = nx;
        this.ny = ny;
    }

    public String getNx() {
        return nx;
    }
    public String getNy() {
        return ny;
    }
}
