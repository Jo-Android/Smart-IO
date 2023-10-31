package com.hgtech.smartio.koin

import com.hgtech.smartio.network.call.other.assign.AssignRepository
import com.hgtech.smartio.network.call.other.assign.SensorTypeCropRepository
import com.hgtech.smartio.network.call.other.assign.SensorUserRepository
import com.hgtech.smartio.network.call.other.crop.CropTypeRepository
import com.hgtech.smartio.network.call.other.crop.CropsRepository
import com.hgtech.smartio.network.call.other.irrigation.IrrigationRepository
import com.hgtech.smartio.network.call.other.sensor.SensorRepository
import com.hgtech.smartio.network.call.other.sensor.SensorTypeRepository
import com.hgtech.smartio.network.call.other.user.UserRepository
import com.hgtech.smartio.network.call.other.values.ValuesRepository
import com.hgtech.smartio.ui.viewmodel.DashboardViewModel
import com.hgtech.smartio.ui.viewmodel.HomeViewModel
import com.hgtech.smartio.ui.viewmodel.SettingsViewModel
import com.hgtech.smartio.ui.viewmodel.assign.sensor_crop.AssignCropSensorViewModel
import com.hgtech.smartio.ui.viewmodel.assign.sensor_crop.ListCropBySensorViewModel
import com.hgtech.smartio.ui.viewmodel.crop.AddCropViewModel
import com.hgtech.smartio.ui.viewmodel.crop.CropDetailViewModel
import com.hgtech.smartio.ui.viewmodel.sensor.AddSensorViewModel
import com.hgtech.smartio.ui.viewmodel.sensor.AssignSensorUserViewModel
import com.hgtech.smartio.ui.viewmodel.user.LoginViewModel
import com.hgtech.smartio.ui.viewmodel.user.ManageUserViewModel
import com.hgtech.smartio.ui.viewmodel.user.SignUViewModel
import com.hgtech.smartio.ui.viewmodel.user.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val repositories = module {
    single { UserRepository() }
    single { AssignRepository() }
    single { SensorTypeCropRepository() }
    single { SensorUserRepository() }
    single { SensorRepository() }
    single { SensorTypeRepository() }
    single { CropsRepository() }
    single { CropTypeRepository() }
    single { ValuesRepository() }
    single { IrrigationRepository() }
}

val viewModel = module {
    viewModel { LoginViewModel(get()) }
    viewModel { SignUViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
    viewModel { UserViewModel() }
    viewModel { ManageUserViewModel(get()) }
    viewModel { AddCropViewModel(get(), get()) }
    viewModel { AddSensorViewModel(get(), get()) }
    viewModel { AssignSensorUserViewModel(get(), get()) }
    viewModel { AssignCropSensorViewModel(get(), get()) }
    viewModel { CropDetailViewModel(get()) }
    viewModel { DashboardViewModel(get()) }
    viewModel { ListCropBySensorViewModel(get()) }
    viewModel { HomeViewModel(get()) }
}

val ui = module {

}