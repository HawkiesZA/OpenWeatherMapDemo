package za.co.hawkiesza.openweathermapdemo.response;

import com.google.gson.annotations.SerializedName;

public class Wind
{
    @SerializedName("speed")
    private double speed;
    @SerializedName("deg")
    private double direction;

    public Wind(double speed, double direction)
    {
        this.speed = speed;
        this.direction = direction;
    }

    public double getSpeed()
    {
        return speed;
    }

    public double getDirection()
    {
        return direction;
    }
}
