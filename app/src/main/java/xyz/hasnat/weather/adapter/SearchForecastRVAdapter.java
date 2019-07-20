package xyz.hasnat.weather.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
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


public class SearchForecastRVAdapter extends RecyclerView.Adapter<SearchForecastRVAdapter.ForecastViewHolder>{

    List<xyz.hasnat.weather.model.ForecastWeather.List> weatherList;
    private Context context;
    private String tempUnit;

    public SearchForecastRVAdapter(List<xyz.hasnat.weather.model.ForecastWeather.List> weatherList, Context context, String tempUnit) {
        this.weatherList = weatherList;
        this.context = context;
        this.tempUnit = tempUnit;
    }



    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_forecast_single_row, parent, false);

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

        Log.e("tag", tempUnit);
        // weather in degree celsius/f
        int temp = weatherList.get(position).getMain().getTemp().intValue();
        if (tempUnit.equals("metric")){
            String tempC = temp+" "+ context.getString(R.string.unit_c);
            holder.tempTV.setText(tempC);
        } else if (tempUnit.equals("imperial")){
            String tempF = temp+" "+ context.getString(R.string.unit_f);
            holder.tempTV.setText(tempF);
        }


        // weather description
        holder.tempDescTV.setText(weatherList.get(position).getWeather().get(0).getDescription());

        //MAX TEMP
        String maxTemp = " " + context.getString(R.string.max_temp).toLowerCase() + ": " +weatherList.get(position).getMain().getTempMax().intValue();
        holder.maxTempTV.setText(maxTemp);

        //MIN TEMP
        String minTemp = " " + context.getString(R.string.min_temp).toLowerCase() + ": " +weatherList.get(position).getMain().getTempMin().intValue();
        holder.minTempTV.setText(minTemp);


    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public class ForecastViewHolder extends RecyclerView.ViewHolder{
        TextView dateNameTV, dateTV, tempTV, tempDescTV
                , maxTempTV, minTempTV, hourFTV;
        ImageView weatherIcon;

        public ForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            dateNameTV = itemView.findViewById(R.id.dateNameFTV);
            hourFTV = itemView.findViewById(R.id.hourFTV);
            dateTV = itemView.findViewById(R.id.dateFTV);
            tempTV = itemView.findViewById(R.id.tempFTV);
            tempDescTV = itemView.findViewById(R.id.tempDescriptionFTV);
            maxTempTV = itemView.findViewById(R.id.tempMaxFTV);
            minTempTV = itemView.findViewById(R.id.tempMinFTV);
            weatherIcon = itemView.findViewById(R.id.weatherIconFTV);
        }
    }

    public void updateCollection(List<xyz.hasnat.weather.model.ForecastWeather.List> weatherList){
        this.weatherList = weatherList;
        notifyDataSetChanged();
    }
}
