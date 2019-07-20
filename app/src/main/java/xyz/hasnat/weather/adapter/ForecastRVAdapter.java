package xyz.hasnat.weather.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import xyz.hasnat.weather.R;
import xyz.hasnat.weather.extras.Constant;
import xyz.hasnat.weather.extras.TimeAndDateConverter;


public class ForecastRVAdapter extends RecyclerView.Adapter<ForecastRVAdapter.ForecastViewHolder>{

    List<xyz.hasnat.weather.model.ForecastWeather.List> weatherList;
    private Context context;

    public ForecastRVAdapter(List<xyz.hasnat.weather.model.ForecastWeather.List> weatherList, Context context) {
        this.weatherList = weatherList;
        this.context = context;
    }



    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_single_row, parent, false);

        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        // set icon
        String icon = weatherList.get(position).getWeather().get(0).getIcon();
        String iconUrl = Constant.baseUrl.WEATHER_IMAGE_BASE_URL+icon+".png";
        Picasso.get().load(iconUrl).into(holder.weatherIcon);

        // day name
        String dateNameString = TimeAndDateConverter.getDay(weatherList.get(position).getDt());
        holder.dateNameTV.setText(dateNameString);

        // day name
        String time = TimeAndDateConverter.getTime(weatherList.get(position).getDt());
        holder.hourFTV.setText(time);

        // date
        String dateString = TimeAndDateConverter.getDate(weatherList.get(position).getDt());
        holder.dateTV.setText(dateString);


        // weather in degree celsius
        int tempInC = weatherList.get(position).getMain().getTemp().intValue();
        holder.tempTV.setText(tempInC+" \u2103");

        // weather description
        holder.tempDescTV.setText(weatherList.get(position).getWeather().get(0).getDescription());

        // humidity
        String humidity = " " + context.getString(R.string.humidity) +": "+ weatherList.get(position).getMain().getHumidity() + " %";
        holder.humidityTV.setText(humidity);

        // cloud
        String cloud = " " + context.getString(R.string.clouds) +": "+ weatherList.get(position).getClouds().getAll() + " %";
        holder.cloudTV.setText(cloud);

        //MAX TEMP
        String maxTemp = " " + context.getString(R.string.max_temp) + ": " +weatherList.get(position).getMain().getTempMax().intValue();
        holder.maxTempTV.setText(maxTemp);

        //MIN TEMP
        String minTemp = " " + context.getString(R.string.min_temp) + ": " +weatherList.get(position).getMain().getTempMin().intValue();
        holder.minTempTV.setText(minTemp);

        //pressure
        String pressure = context.getString(R.string.pressure) + ": " + weatherList.get(position).getMain().getPressure().intValue() + " hpa";
        holder.pressureFTV.setText(pressure);

        // clouds
        String clouds = context.getString(R.string.clouds) + ": " +weatherList.get(position).getClouds().getAll() + " %";
        holder.cloudsAllFTV.setText(clouds);

        // winds
        String winds = context.getString(R.string.winds)
                     + ": " + weatherList.get(position).getWind().getSpeed() + " m/s\n"
                     + context.getString(R.string.degree)
                     + ": " + weatherList.get(position).getWind().getDeg();
        holder.windsFTV.setText(winds);


    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public class ForecastViewHolder extends RecyclerView.ViewHolder{
        TextView dateNameTV, dateTV, tempTV, tempDescTV, humidityTV,
                cloudTV, maxTempTV, minTempTV, hourFTV, windsFTV, pressureFTV, cloudsAllFTV;
        ImageView weatherIcon;

        public ForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            dateNameTV = itemView.findViewById(R.id.dateNameFTV);
            hourFTV = itemView.findViewById(R.id.hourFTV);
            dateTV = itemView.findViewById(R.id.dateFTV);
            tempTV = itemView.findViewById(R.id.tempFTV);
            tempDescTV = itemView.findViewById(R.id.tempDescriptionFTV);
            humidityTV = itemView.findViewById(R.id.humidityFTV);
            cloudTV = itemView.findViewById(R.id.cloudFTV);
            maxTempTV = itemView.findViewById(R.id.tempMaxFTV);
            minTempTV = itemView.findViewById(R.id.tempMinFTV);
            weatherIcon = itemView.findViewById(R.id.weatherIconFTV);
            windsFTV = itemView.findViewById(R.id.windsFTV);
            pressureFTV = itemView.findViewById(R.id.pressureFTV);
            cloudsAllFTV = itemView.findViewById(R.id.cloudsAllFTV);
        }
    }

    public void updateCollection(List<xyz.hasnat.weather.model.ForecastWeather.List> weatherList){
        this.weatherList = weatherList;
        notifyDataSetChanged();
    }
}
