package com.cforlando.streetartandroid;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.media.ExifInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cforlando.streetartandroid.Models.Installation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import me.kaede.tagview.Tag;
import me.kaede.tagview.TagView;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

public class NewInstallationActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    public static final String TAG = "NewInstallationActivity";

    private static final int REQUEST_PLACE_PICKER_CODE = 1;

    protected GoogleApiClient mGoogleApiClient;
    private Installation mInstallation = new Installation(this);
    private ImageView headerImage;
    private TagView tagView;
    private EditText addTagsEditText;
    private ImageButton searchLocationButton;
    private TextView locationText;
    FloatingActionButton fab;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mInstallation.setCreator(ParseUser.getCurrentUser());

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Places.GEO_DATA_API)
                .build();

        setContentView(R.layout.activity_new_installation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab = (FloatingActionButton) findViewById(R.id.fab_speed_dial);

        headerImage = (ImageView) findViewById(R.id.header_image);
        locationText = (TextView) findViewById(R.id.tv_location);

        searchLocationButton = (ImageButton) findViewById(R.id.button_search_location);
        searchLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permissionCheck = ContextCompat.checkSelfPermission(NewInstallationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    displayPlacePicker();
                } else {
                    Nammu.askForPermission(NewInstallationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION, new PermissionCallback() {
                        @Override
                        public void permissionGranted() {
                            displayPlacePicker();
                        }

                        @Override
                        public void permissionRefused() {
                            Snackbar
                                    .make(getCurrentFocus(), R.string.rationale_fine_location, Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            }
        });

        tagView = (TagView) findViewById(R.id.tagview);
        addTagsEditText = (EditText) findViewById(R.id.edit_tag);
        addTagsEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    addTag();
                    handled = true;
                }
                return handled;
            }
        });

        Intent i = this.getIntent();
        File imageFile = (File) i.getSerializableExtra("image_file");

        Glide.with(this)
                .load(imageFile)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(headerImage);

        String path = imageFile.getAbsolutePath();
        final Bitmap bitmap = BitmapFactory.decodeFile(path);


        extractLocationFromExif(path);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInstallation.addTags(tagView.getTags());
                try {
                    mInstallation.addPhoto(bitmap);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void extractLocationFromExif(String path) {
        try {
            ExifInterface exif = new ExifInterface(path);
            if (exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE) != null) {
                float[] coords = new float[2];
                exif.getLatLong(coords);

                LatLng point = new LatLng(coords[0], coords[1]);
                mInstallation.setLocation(point);

                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                List<Address> listAddresses = geocoder.getFromLocation(coords[0], coords[1], 1);
                String address = listAddresses.get(0).getAddressLine(0);
                locationText.setText(address);
                mInstallation.setAddress(address);

            } else {
                locationText.setText("Add Location");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void displayPlacePicker() {

        // Construct an intent for the place picker
        try {
            PlacePicker.IntentBuilder intentBuilder =
                    new PlacePicker.IntentBuilder();
            Intent intent = intentBuilder.build(NewInstallationActivity.this);
            // Start the intent by requesting a result,
            // identified by a request code.
            startActivityForResult(intent, REQUEST_PLACE_PICKER_CODE);

        } catch (GooglePlayServicesRepairableException e) {
            Log.e(TAG, e.toString());
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.e(TAG, e.toString());
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == REQUEST_PLACE_PICKER_CODE && resultCode == Activity.RESULT_OK) {
            // The user has selected a place. Extract the name and address.
            final Place place = PlacePicker.getPlace(resultData, this);

            final CharSequence address = place.getAddress();
            final String street = address.toString().split(",")[0];

            String attributions = PlacePicker.getAttributions(resultData);
            if (attributions == null) {
                attributions = "";
            }

            locationText.setText(street);
            mInstallation.setAddress(street);

            LatLng latLng = place.getLatLng();
            mInstallation.setLocation(latLng);
            mInstallation.saveInBackground();

        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void addTag() {

        if (!addTagsEditText.getText().toString().equals("")) {
            Tag tag = new Tag(addTagsEditText.getText().toString());
            tag.isDeletable = true;
            tagView.addTag(tag);
            addTagsEditText.setText("");
        }
    }

}

