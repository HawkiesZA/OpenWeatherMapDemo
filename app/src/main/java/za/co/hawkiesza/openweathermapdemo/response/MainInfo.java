package za.co.hawkiesza.openweathermapdemo.response;

import com.google.gson.annotations.SerializedName;

public class MainInfo
{
    @SerializedName("temp")
    private double temp;
    @SerializedName("pressure")
    private double pressure;
    @SerializedName("humidity")
    private double humidity;
    @SerializedName("temp_min")
    private double tempMin;
    @SerializedName("temp_max")
    private double tempMax;
    @SerializedName("sea_level")
    private double seaLevel;
    @SerializedName("grnd_level")
    private double groundLevel;

    public MainInfo(double temp, double pressure, double humidity, double tempMin, double tempMax, double seaLevel, double groundLevel)
    {
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.seaLevel = seaLevel;
        this.groundLevel = groundLevel;
    }

    public double getTemp()
    {
        return temp;
    }

    public double getPressure()
    {
        return pressure;
    }

    public double getHumidity()
    {
        return humidity;
    }

    public double getTempMin()
    {
        return tempMin;
    }

    public double getTempMax()
    {
        return tempMax;
    }

    public double getSeaLevel()
    {
        return seaLevel;
    }

    public double getGroundLevel()
    {
        return groundLevel;
    }
}
