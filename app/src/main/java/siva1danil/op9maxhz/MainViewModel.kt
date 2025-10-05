package siva1danil.op9maxhz

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _repository = RefreshRateRepository(application)
    private val _enabled = mutableStateOf(false)
    private val _rate = mutableFloatStateOf(0.0f)
    private val _permission = mutableStateOf(false)

    val enabled: State<Boolean> = _enabled
    val rate: State<Float> = _rate
    val permission: State<Boolean> = _permission
    val packageName: String = getApplication<Application>().packageName

    init {
        _enabled.value = MaxHzUtil.isEnabled(getApplication())
        _rate.floatValue = _repository.getRate()
        _permission.value = MaxHzUtil.hasWriteSecureSettingsPermission(getApplication())
    }

    fun setEnabled(value: Boolean) {
        _enabled.value = value
        apply()
    }

    fun setRate(value: Float) {
        _rate.floatValue = value
        apply()

        _repository.setRate(value)
    }

    private fun apply() {
        if (_enabled.value) MaxHzUtil.setEnabled(getApplication())
        else when (_rate.floatValue) {
            60.0f -> MaxHzUtil.setDisabled60(getApplication())
            120.0f -> MaxHzUtil.setDisabled120(getApplication())
            else -> {}
        }
    }
}