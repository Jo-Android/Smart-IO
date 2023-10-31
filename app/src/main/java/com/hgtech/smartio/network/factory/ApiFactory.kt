package com.hgtech.smartio.network.factory

import com.hgtech.smartio.network.api.IrrigationApi
import com.hgtech.smartio.network.api.UserApi
import com.hgtech.smartio.network.api.assign.AssignApi
import com.hgtech.smartio.network.api.assign.SensorCropApi
import com.hgtech.smartio.network.api.assign.SensorUserApi
import com.hgtech.smartio.network.api.crop.CropTypeApi
import com.hgtech.smartio.network.api.crop.CropsApi
import com.hgtech.smartio.network.api.sensor.SensorApi
import com.hgtech.smartio.network.api.sensor.SensorTypeApi
import com.hgtech.smartio.network.api.values.ValuesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object ApiFactory {

    private var BASE_URL: String = "http://smartioagriculture.atwebpages.com/api/"


    private val client: OkHttpClient

    init {
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        client = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()
    }

    val usersApi: UserApi = RetrofitFactory.create(BASE_URL + "user/")
        .create(UserApi::class.java)

    val sensorApi: SensorApi = RetrofitFactory.create(BASE_URL + "sensor/")
        .create(SensorApi::class.java)

    val sensorTypeApi: SensorTypeApi = RetrofitFactory.create(BASE_URL + "sensor_type/")
        .create(SensorTypeApi::class.java)

    val cropsApi: CropsApi = RetrofitFactory.create(BASE_URL + "crop/")
        .create(CropsApi::class.java)

    val cropsTypeApi: CropTypeApi = RetrofitFactory.create(BASE_URL + "crop_type/")
        .create(CropTypeApi::class.java)

    val sensorCropApi: SensorCropApi = RetrofitFactory.create(BASE_URL + "sensor_crop/")
        .create(SensorCropApi::class.java)

    val sensorUserApi: SensorUserApi = RetrofitFactory.create(BASE_URL + "sensor_user/")
        .create(SensorUserApi::class.java)

    val assignApi: AssignApi = RetrofitFactory.create(BASE_URL + "state/")
        .create(AssignApi::class.java)


    val valuesApi: ValuesApi = RetrofitFactory.create(BASE_URL + "values/")
        .create(ValuesApi::class.java)

    val irrigationApi: IrrigationApi = RetrofitFactory.create(BASE_URL + "irrigation/")
        .create(IrrigationApi::class.java)

}