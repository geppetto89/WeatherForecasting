package android.mobile.micmen.nicedayforecasting.features.forecast

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.mobile.micmen.nicedayforecasting.R
import android.mobile.micmen.nicedayforecasting.features.forecast.adapter.ForecastAdapter
import android.mobile.micmen.nicedayforecasting.features.preference.WeatherPreferenceActivity
import android.mobile.micmen.nicedayforecasting.util.NiceDayUtil
import android.mobile.micmen.nicedayforecasting.widget.HomeMoodCardView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.gms.location.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable


class MainActivity : AppCompatActivity() {

    companion object {
        const val POSITION_PERMISSION_CODE = 1
    }

    private lateinit var forecastViewModel: ForecastViewModel
    private lateinit var progress: ProgressBar
    private lateinit var image: ImageView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var list: RecyclerView
    private lateinit var refreshButton: FloatingActionButton
    private lateinit var moodCardView: HomeMoodCardView
    private lateinit var moodDescr: TextView
    private lateinit var adapter: ForecastAdapter
    private lateinit var compositeDisposable: CompositeDisposable


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.tool_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            val intent = Intent()
            intent.setClass(this, WeatherPreferenceActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        checkLocationPermissionAndGetPosition()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forecastViewModel = ViewModelProviders.of(this).get(ForecastViewModel::class.java)
        setContentView(R.layout.activity_main)
        compositeDisposable = CompositeDisposable()
        image = findViewById(R.id.image)
        list = findViewById(R.id.recycler_view)
        progress = findViewById(R.id.progress)
        refreshButton = findViewById(R.id.floating_button)
        moodCardView = findViewById(R.id.mood)
        moodDescr = findViewById(R.id.mood_descr)
        list.layoutManager = LinearLayoutManager(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        refreshButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.refresh))
        adapter = ForecastAdapter()
        list.adapter = adapter
        ViewCompat.setNestedScrollingEnabled(list, false)
        refreshButton.setOnClickListener {
            compositeDisposable.clear()
            checkLocationPermissionAndGetPosition()
        }
        setSupportActionBar(findViewById(R.id.my_toolbar))
    }

    private fun checkLocationPermissionAndGetPosition() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getPosition()
        } else {
            val perms = Array(1) { android.Manifest.permission.ACCESS_FINE_LOCATION }
            ActivityCompat.requestPermissions(this, perms, POSITION_PERMISSION_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == POSITION_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    || grantResults.isNotEmpty() && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                getPosition()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getPosition() {
        //check the gps is on
        if (isGpsOpen()) {
            //try to retrieve the user's last location
            fusedLocationClient.lastLocation?.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    showLoader()
                    checkTodaysWeather(location)
                } else {
                    //if you don't have the location request it
                    fusedLocationClient.requestLocationUpdates(locationRequest(), object : LocationCallback() {
                        override fun onLocationResult(p0: LocationResult?) {
                            super.onLocationResult(p0)
                            moodCardView.setLoadingState()
                            showLoader()
                            getPosition()
                        }
                    }, null)
                }
            }
        } else {
            //if the gps if off ask the user to set it on
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle(getString(R.string.gps_req))
            builder.setCancelable(true)
            builder.setMessage(getString(R.string.alert_message))
            builder.setPositiveButton(getString(R.string.yes)) { dialog, which ->
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
            builder.setNegativeButton(getString(R.string.no)) { dialog, which ->
                finish()
            }
            builder.show()
        }

    }


    //request the user location once
    private fun locationRequest(): LocationRequest {
        val locationRequest = LocationRequest()
        locationRequest.numUpdates = 1
        locationRequest.fastestInterval = 5000
        locationRequest.interval = 10000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        return locationRequest
    }


    private fun isGpsOpen(): Boolean {
        val manager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun checkTodaysWeather(location: Location?) {
        if (location != null) {
            //retrieve the weather and the appreciation
            showLoader()
            moodCardView.setLoadingState()
            moodDescr.text = getString(R.string.loading_info)

            compositeDisposable.add(
                    forecastViewModel.getForecastOfToday(location.longitude.toString(), location.latitude.toString(), this)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ weatherAppreciation ->
                        refreshButton.visibility = View.VISIBLE
                        hideLoader()
                        //once you got the result show them to the user
                        if (forecastViewModel.forecastModelList != null) {
                            adapter.setForecastModels(forecastViewModel.forecastModelList)
                            val moodIconId = NiceDayUtil.getMoodIcon(weatherAppreciation)
                            val drawable = ContextCompat.getDrawable(this, moodIconId)
                            if (moodIconId != -1) {
                                image.setImageDrawable(drawable)
                                moodDescr.text = weatherAppreciation.toString()
                            } else {
                                moodDescr.text = getString(R.string.problems_fetching)
                            }
                        }
                        moodCardView.setNormalState()

                    }, { t: Throwable? ->
                        //if somethings goes wrong show a feedback to the user
                        run {
                            adapter.clearList()
                            moodDescr.text = getString(R.string.something_went_wrong)
                            moodCardView.setNormalState()
                            hideLoader()
                            Log.d("", t!!.message)
                        }
                    }))
        } else {
            hideLoader()
            adapter.clearList()
            image.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.muted))
            moodDescr.text = getString(R.string.position_not_found)
        }
    }

    private fun showLoader() {
        progress.visibility = View.VISIBLE
    }

    private fun hideLoader() {
        progress.visibility = View.GONE
    }


    override fun onDestroy() {
        super.onDestroy()
        //to avoid memory leak
        compositeDisposable.clear()
    }
}
