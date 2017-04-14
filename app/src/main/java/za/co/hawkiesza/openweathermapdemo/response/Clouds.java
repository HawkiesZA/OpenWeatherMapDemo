package za.co.hawkiesza.openweathermapdemo.response;

import com.google.gson.annotations.SerializedName;

public class Clouds
{
    @SerializedName("all")
    private double cloudiness;

    public Clouds(double cloudiness)
    {
        this.cloudiness = cloudiness;
    }

    public double getCloudiness()
    {
        return cloudiness;
    }
}
