<div align="center">
  <img src="app/src/main/res/mipmap-xxxhdpi/ic_launcher.png" alt="logo" width="120">
  <h3>OP9MaxHz</h3>
  <p>A tiny, beginner-friendly tool to unlock and force 120 Hz on OnePlus 9.</p>
</div>

<hr>

>WIP: This project is a work-in-progress. Interfaces, usage, and behavior may change. Use at your own risk.

## ADB (one-time setup)

You need to grant the WRITE_SECURE_SETTINGS permission via ADB.

1) Verify device connection: `adb devices`

2) Install the APK: `adb install --bypass-low-target-sdk-block OP9MaxHz.apk`

3) Grant the permission: `adb shell pm grant siva1danil.op9maxhz android.permission.WRITE_SECURE_SETTINGS`

## Usage

Install and open OP9MaxHz.

- Turn <i>Unlock</i> switch on to unlock the highest refresh rate.

- Turn it off to let system manage refresh rate.

- Tap <i>Default refresh rate</i> to switch between 60 and 120 Hz (when unlock is inactive).
