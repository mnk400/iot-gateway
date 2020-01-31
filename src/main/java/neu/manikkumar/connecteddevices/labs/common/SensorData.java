package neu.manikkumar.connecteddevices.labs.common;

import java.util.Date;
import java.sql.Timestamp;

public class SensorData{

    private float currentValue = 0.0f;
    private int totalCount     = 0;
    private float totalValue   = 0.0f;
    private float maxValue     = 99.99f;
    private float minValue     = 0.0f;
    private String timestamp;
    private String name;

    public SensorData() {
    }

    public void addValue(float newVal) {
        this.totalValue   = this.totalValue + newVal;
        this.currentValue = newVal;
        this.totalCount++;
        this.timestamp = String.valueOf(new Timestamp(new Date().getTime()));
        if (newVal > this.maxValue) { this.maxValue = newVal;}
        if (newVal < this.minValue) { this.minValue = newVal;}
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