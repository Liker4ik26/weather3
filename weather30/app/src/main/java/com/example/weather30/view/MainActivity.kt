package com.example.weather30.view


import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide


import com.example.weather30.databinding.ActivityMainBinding
import com.example.weather30.viewmodel.MainViewModel



class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var GET:SharedPreferences
    private lateinit var SET:SharedPreferences.Editor
    private lateinit var binding:ActivityMainBinding

    //val API: String = "34ae534ae4ff9829f963590e080ea518"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //setContentView(R.layout.activity_main)
        GET=getSharedPreferences(packageName, MODE_PRIVATE)
        SET=GET.edit()

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        var cName =GET.getString("cityName","omsk")
        binding.edtCityName.setText(cName)

        viewModel.refreshData(cName!!)

        getLiveData()
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.llDataView.visibility=View.GONE
            binding.tvError.visibility=View.GONE
            binding.pbLoading.visibility=View.GONE

            var cityName = GET.getString("cityName",cName)
            binding.edtCityName.setText(cityName)
            viewModel.refreshData(cityName!!)
            binding.swipeRefreshLayout.isRefreshing=false
        }
        binding.imgSearchCityName.setOnClickListener{
            val cityName =binding.edtCityName.text.toString()
            SET.putString("cityName",cityName)
            SET.apply()
            viewModel.refreshData(cityName)
            getLiveData()
        }

    }

    private fun getLiveData() {
        viewModel.weather_data.observe(this, Observer { data ->
        data.let {
            binding.llDataView.visibility= View.VISIBLE
            binding.pbLoading.visibility=View.GONE

            binding.tvDegree.text = data?.main?.temp.toString()
            binding.tvCountryCode.text = data?.sys?.country.toString()
            binding.tvCityName.text = data?.name.toString()
            binding.tvHumidity.text = data?.main?.humidity.toString()
            binding.tvSpeed.text = data?.wind?.speed.toString()
            binding.tvLat.text =data?.coord?.lat.toString()
            binding.tvLon.text = data?.coord?.lon.toString()

            Glide.with(this).load("http://openweathermap.org/img/wn/"+data?.weather?.get(0)?.icon+"2@x.png")
                .into(binding.imgWeatherIcon)
        }
        })
        viewModel.weather_load.observe(this, Observer { load->
            load.let {
                if(it == true){
                   binding.pbLoading.visibility=View.VISIBLE
                   binding.tvError.visibility=View.GONE
                   binding.llDataView.visibility=View.GONE
            }else{
                binding.pbLoading.visibility=View.GONE

                }
            }
        })
        viewModel.weather_error.observe(this, Observer { error->
            error?.let {
                if(it){
                    binding.tvError.visibility = View.VISIBLE
                    binding.llDataView.visibility = View.GONE
                    binding.pbLoading.visibility = View.GONE
                }else{
                    binding.tvError.visibility =View.GONE
                }
            }
        })
    }


}





