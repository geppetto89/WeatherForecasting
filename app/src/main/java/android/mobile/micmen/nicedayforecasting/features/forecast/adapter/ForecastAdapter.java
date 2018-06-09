package android.mobile.micmen.nicedayforecasting.features.forecast.adapter;

import android.mobile.micmen.nicedayforecasting.R;
import android.mobile.micmen.nicedayforecasting.features.forecast.ForecastModel;
import android.mobile.micmen.nicedayforecasting.util.DateUtil;
import android.mobile.micmen.nicedayforecasting.util.NiceDayUtil;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {

    private List<ForecastModel> forecastModels;

    public ForecastAdapter(List<ForecastModel> forecastModels) {
        this.forecastModels = forecastModels;
    }

    public ForecastAdapter() {
    }

    public void setForecastModels(List<ForecastModel> forecastModels) {
        this.forecastModels = forecastModels;
        notifyDataSetChanged();
    }

    public void clearList(){
        if (forecastModels != null) {
            forecastModels.clear();
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ForecastViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_element, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        holder.bind(forecastModels.get(position));
    }

    @Override
    public int getItemCount() {
        if (forecastModels != null) {
            return forecastModels.size();
        }
        else {
            return 0;
        }
    }

    class ForecastViewHolder extends RecyclerView.ViewHolder {

        private TextView dateTime;
        private ImageView image;
        private TextView temperature;

        private void assignViews(View view) {
            dateTime = view.findViewById(R.id.date_time);
            image = view.findViewById(R.id.image);
            temperature = view.findViewById(R.id.temperature);
        }

        public ForecastViewHolder(View itemView) {
            super(itemView);
            assignViews(itemView);
        }

        public void bind(ForecastModel forecastModel) {
            dateTime.setText(String.format("time: %s", DateUtil.foreCastDateHour.format(forecastModel.getForecastDate())));
            temperature.setText(String.format("%s°", forecastModel.getTemperature()));
            int weatherType = NiceDayUtil.getWeatherType(forecastModel.getWeatherType(), NiceDayUtil.isNight(forecastModel.getForecastDate()));
            if (weatherType != -1) {
                image.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), weatherType));
            }

        }
    }


}
