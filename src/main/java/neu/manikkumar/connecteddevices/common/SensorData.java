package neu.manikkumar.connecteddevices.common;

import java.util.Date;
import java.sql.Timestamp;

public class SensorData{

    private float currentValue = 0.0f;
    private int totalCount     = 0;
    private float totalValue   = 0.0f;
    private float maxValue     = 0.0f;
    private float minValue     = 99;
    private String timestamp;
    private String name = "Not Set";

    public SensorData() {
    }

    public boolean addValue(float newVal) {
        try {
            this.totalValue   = this.totalValue + newVal;
            this.currentValue = newVal;
            this.timestamp    = String.valueOf(new Timestamp(new Date().getTime()));
            this.totalCount++;
            if (newVal > this.maxValue) { this.maxValue = newVal;}
            if (newVal < this.minValue) { this.minValue = newVal;}
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public float getCurrentValue() {
        return this.currentValue;
    }

    public float getAverageValue() {
        return this.totalValue/this.totalCount;
    }

    public float getMaxValue() {
        return this.maxValue;
    }

    public int getTotal() {
        return this.totalCount;
    }
    public float getMinValue() {
        return this.minValue;
    }

    public String getName() {
        return this.name;
    }

    public boolean setName(String name) {
        this.name = name;
        return true;
    }
 
    
}