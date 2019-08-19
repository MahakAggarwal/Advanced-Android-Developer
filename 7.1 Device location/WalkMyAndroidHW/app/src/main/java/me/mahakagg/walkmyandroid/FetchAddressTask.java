package me.mahakagg.walkmyandroid;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.text.TextUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// this class is used for reverse geo-coding
public class FetchAddressTask extends AsyncTask <Location, Void, String> {
    private OnTaskCompleted onTaskCompleted;
    private Context mContext;

    public FetchAddressTask(Context context, OnTaskCompleted taskCompleted) {
        this.mContext = context;
        this.onTaskCompleted = taskCompleted;
    }

    @Override
    protected String doInBackground(Location... locations) {
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        // get location from the passed in parameter
        Location location = locations[0];
        // list of addresses needed for geo-coder
        List<Address> addresses = null;
        // empty string to hold final address
        String resultMessage = "";



        // reverse geo-coding
        try{
            // get one address result from geo-coder
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            // if no addresses are found, set result message
            if (addresses == null || addresses.size() == 0){
                if (resultMessage.isEmpty()){
                    resultMessage = mContext.getString(R.string.no_address_found);
                }
            }
            else{
                // if address is found, read the address into resultMessage string
                Address address = addresses.get(0);
                ArrayList<String> addressParts = new ArrayList<>();
                // get address lines and put them in array list
                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++){
                    addressParts.add(address.getAddressLine(i));
                }
                // concatenate array list contents into a string
                resultMessage = TextUtils.join("\n", addressParts);
            }
        }
        catch (IOException e){
            resultMessage = mContext.getString(R.string.service_not_available);
        }
        catch (IllegalArgumentException e){
            resultMessage = mContext.getString(R.string.invalid_lat_long_used);
        }
        return resultMessage;
    }

    @Override
    protected void onPostExecute(String s) {
        onTaskCompleted.onTaskCompleted(s);
        super.onPostExecute(s);
    }

    interface OnTaskCompleted{
        void onTaskCompleted(String result);
    }
}
