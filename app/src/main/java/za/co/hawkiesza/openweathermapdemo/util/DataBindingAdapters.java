package za.co.hawkiesza.openweathermapdemo.util;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

public class DataBindingAdapters {
    @BindingAdapter("app:srcCompat")
    public static void setImageResource(ImageView imageView, int resource){
        imageView.setImageResource(resource);
    }
}
