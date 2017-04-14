package za.co.hawkiesza.openweathermapdemo.response;

import com.google.gson.annotations.SerializedName;

public class Snow
{
    @SerializedName("3h")
    double volume;

    public Snow(double volume)
    {
        this.volume = volume;
    }

    public double getVolume()
    {
        return volume;
    }
}
