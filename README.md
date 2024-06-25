# Zen-Alarm

## Overview

Zen-Alarm allows users to schedule alarms at given times, cancel alarms, and update alarm times. When an alarm triggers, the app displays a full-screen notification, even if the app is not active or has been killed. The device vibrates until the user dismisses the notification.

## Features

- Schedule alarms at specified times
- Cancel existing alarms
- Update alarm times
- Full-screen notification when an alarm triggers
- Device vibrates until the alarm is dismissed

## Tech Stack

- **UI**: [Jetpack Compose](https://developer.android.com/jetpack/compose)
- **Alarm Scheduling**: [Alarm Manager](https://developer.android.com/reference/android/app/AlarmManager)
- **Notifications**: [Notification Manager](https://developer.android.com/reference/android/app/NotificationManager)
- **Broadcast Receivers**: [BroadcastReceiver](https://developer.android.com/reference/android/content/BroadcastReceiver)
- **Vibration**: [Vibrator Service](https://developer.android.com/reference/android/os/Vibrator)

## Screenshots

[images]

## Getting Started

### Prerequisites

- Android Studio
- Android SDK

### Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/kunals741/ZenAlarm.git
    ```
2. Open the project in Android Studio.
3. Build and run the project on an Android device or emulator.

## Usage

1. **Schedule an Alarm**: Open the app and set the desired time for the alarm.
2. **Cancel an Alarm**: Navigate to the alarm list and cancel the desired alarm.
3. **Update an Alarm**: Select an existing alarm and update the time.
4. When the alarm triggers, a full-screen notification will appear, and the device will vibrate until the notification is dismissed.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for any improvements or bug fixes.

## Contact

If you have any questions, feel free to contact me at [kunal.satpute.1905@gmail.com](mailto:kunal.satpute.1905@gmail.com).
