package siva1danil.op9maxhz

import android.os.Build
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.N)
class MaxHzTileService : TileService() {
    private lateinit var _repository: RefreshRateRepository

    override fun onCreate() {
        super.onCreate()
        _repository = RefreshRateRepository(applicationContext)
    }

    override fun onStartListening() {
        super.onStartListening()
        updateState()
    }

    override fun onClick() {
        super.onClick()

        if (!MaxHzUtil.hasWriteSecureSettingsPermission(applicationContext)) return

        val enabled = !MaxHzUtil.isEnabled(applicationContext)
        val rate = _repository.getRate()
        if (enabled) MaxHzUtil.setEnabled(applicationContext)
        else when (rate) {
            60.0f -> MaxHzUtil.setDisabled60(applicationContext)
            120.0f -> MaxHzUtil.setDisabled120(applicationContext)
            else -> {}
        }

        updateState()
    }

    private fun updateState() {
        val tile = qsTile ?: return
        if (!MaxHzUtil.hasWriteSecureSettingsPermission(applicationContext)) tile.state = Tile.STATE_UNAVAILABLE
        else if (MaxHzUtil.isEnabled(applicationContext)) tile.state = Tile.STATE_ACTIVE
        else tile.state = Tile.STATE_INACTIVE
        tile.updateTile()
    }
}
