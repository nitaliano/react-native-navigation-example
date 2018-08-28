package com.mapboxexample;

import android.app.Activity;
import android.location.Location;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavigationModule extends ReactContextBaseJavaModule {
    public static final String REACT_CLASS = NavigationModule.class.getSimpleName();

    private LocationEngine locationEngine;

    public NavigationModule(ReactApplicationContext reactContext) {
        super(reactContext);

        LocationEngineProvider provider = new LocationEngineProvider(reactContext);
        locationEngine = provider.obtainBestLocationEngineAvailable();
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @ReactMethod
    public void showNavigation(ReadableArray destination) throws SecurityException {
        final Activity activity = getCurrentActivity();
        if (activity == null) {
            return;
        }

        Location lastKnownLocation = locationEngine.getLastLocation();
        NavigationRoute.builder(activity)
                .accessToken(Mapbox.getAccessToken())
                .origin(Point.fromLngLat(lastKnownLocation.getLongitude(), lastKnownLocation.getLatitude()))
                .destination(Point.fromLngLat(destination.getDouble(0), destination.getDouble(1)))
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                                .directionsRoute(response.body().routes().get(0))
                                .shouldSimulateRoute(true)
                                .build();
                        NavigationLauncher.startNavigation(activity, options);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                        Log.d(REACT_CLASS, t.getLocalizedMessage());
                    }
                });
    }
}
