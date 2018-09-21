package bd.com.infobox.weather.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import bd.com.infobox.weather.Constants.Constant;
import bd.com.infobox.weather.R;
import bd.com.infobox.weather.Utils.TimeAndDateConverter;

public class ForecastRVAdapter extends RecyclerView.Adapter<ForecastRVAdapter.ForecastViewHolder>{

    List<bd.com.infobox.weather.Model.ForecastWeatherPick.List> weatherList;
    private Context context;

    public ForecastRVAdapter(List<bd.com.infobox.weather.Model.ForecastWeatherPick.List> weatherList, Context context) {
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
        String maxTemp = " " + context.getString(R.string.max_temp) + ": " +weatherList.get(position).getMain().getTempMax().intValue() + " \u2103";
        holder.maxTempTV.setText(maxTemp);

        //MIN TEMP
        String minTemp = " " + context.getString(R.string.min_temp) + ": " +weatherList.get(position).getMain().getTempMin().intValue() + " \u2103";
        holder.minTempTV.setText(minTemp);

    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public class ForecastViewHolder extends RecyclerView.ViewHolder{
        TextView dateNameTV, dateTV, tempTV, tempDescTV, humidityTV, cloudTV, maxTempTV, minTempTV;
        ImageView weatherIcon;

        public ForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            dateNameTV = itemView.findViewById(R.id.dateNameFTV);
            dateTV = itemView.findViewById(R.id.dateFTV);
            tempTV = itemView.findViewById(R.id.tempFTV);
            tempDescTV = itemView.findViewById(R.id.tempDescriptionFTV);
            humidityTV = itemView.findViewById(R.id.humidityFTV);
            cloudTV = itemView.findViewById(R.id.cloudFTV);
            maxTempTV = itemView.findViewById(R.id.tempMaxFTV);
            minTempTV = itemView.findViewById(R.id.tempMinFTV);
            weatherIcon = itemView.findViewById(R.id.weatherIconFTV);

        }
    }

    public void updateCollection(List<bd.com.infobox.weather.Model.ForecastWeatherPick.List> weatherList){
        this.weatherList = weatherList;
        notifyDataSetChanged();
    }
}
