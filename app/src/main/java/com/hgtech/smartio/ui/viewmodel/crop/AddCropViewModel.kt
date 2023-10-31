package com.hgtech.smartio.ui.viewmodel.crop

import androidx.lifecycle.MutableLiveData
import com.hgtech.smartio.network.call.other.crop.CropTypeRepository
import com.hgtech.smartio.network.call.other.crop.CropsRepository
import com.hgtech.smartio.network.model.response.crop.AddCropResponse
import com.hgtech.smartio.network.model.response.crop.type.CropTypeListResponse
import com.hgtech.smartio.network.model.response.crop.type.CropTypeResponse
import com.hgtech.smartio.ui.viewmodel.BaseViewModel

class AddCropViewModel(
    private val cropTypeRepository: CropTypeRepository,
    private val cropsRepository: CropsRepository
) : BaseViewModel() {

    var typeId: String? = null
    val cropTypeList = MutableLiveData<CropTypeListResponse>()
    val addTypeResponse = MutableLiveData<CropTypeResponse>()
    val addCropResponse = MutableLiveData<AddCropResponse>()

    fun getCropTypeList() {
        execute {
            cropTypeRepository.listAll()?.let {
                cropTypeList.postValue(it)
            }
        }
    }

    fun addType(type: String) {
        execute {
            cropTypeRepository.insert(type)?.let {
                addTypeResponse.postValue(it)
            }
        }
    }

    fun addCrop(name: String) {
        execute {
            cropsRepository.insert(typeId!!, name)?.let {
                addCropResponse.postValue(it)
            }
        }
    }

}