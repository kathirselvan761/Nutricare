# 🥗 NutriCare AI — AI-Based Nutrition Decision Support System

[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-blue.svg)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-brightgreen.svg)](https://developer.android.com/jetpack/compose)
[![Firebase](https://img.shields.io/badge/Backend-Firebase-FFCA28.svg)](https://firebase.google.com)

**NutriCare AI** is an intelligent, personalized nutrition and health support Android application designed to help patients understand their health conditions, track dietary habits, and receive tailored AI-powered nutritional recommendations.

---

## 📱 Features

- **Interactive Onboarding Carousel**: Introduces new users to personalized health tracking and AI guidance.
- **Firebase Authentication**:
  - **Sign Up**: Register with Full Name, Email, Password ($\ge$ 8 characters), and Confirm Password validation.
  - **Login**: Authenticate securely using Firebase Email/Password.
  - **Session Persistence**: Maintains user login across app restarts.
  - **Password Reset**: Send password reset links directly to registered email addresses.
  - **Logout**: Secure user sign-out functionality.
- **Cloud Firestore Database Integration**:
  - Automatically creates a patient document under the `users` collection upon registration.
  - Document ID matches the Firebase Auth `UID`.
  - Default role assigned: `patient`.
  - Passwords are never saved in Firestore.
- **Patient Profile Setup**: Collects specific health conditions, goals, and dietary preferences.
- **Patient Home Dashboard**: Displays personalized AI nutrition guidance cards and profile status.

---

## 🏗️ Architecture & Tech Stack

NutriCare AI is built following Google's recommended **MVVM (Model-View-ViewModel)** architecture and the **Repository Pattern** for a clean separation of concerns.

```
com.example.nutricare
│
├── data/                      # Data Layer (Repositories)
│   ├── AuthRepository.kt       # Firebase Authentication API calls
│   └── UserRepository.kt       # Cloud Firestore Database CRUD operations
│
├── model/                     # Data Models
│   └── User.kt                 # User data class for Firestore schema
│
├── ui/                        # UI Layer (Jetpack Compose & ViewModels)
│   ├── auth/
│   │   ├── AuthViewModel.kt             # UI state management & business logic
│   │   ├── LoginScreen.kt               # Login UI with Material 3 styling
│   │   ├── SignupScreen.kt              # Registration UI with input validation
│   │   ├── PatientProfileSetupScreen.kt # Patient health onboarding UI
│   │   └── ForgotPasswordDialog.kt      # Password reset dialog component
│   ├── home/
│   │   └── HomeScreen.kt                # Patient Dashboard UI
│   └── theme/                           # Color Palette, Typography & Styling
│
└── navigation/
    └── AppNavigation.kt        # Jetpack Compose State Navigation
```

### Key Technologies

* **Language**: Kotlin `2.4.21`
* **UI Toolkit**: Jetpack Compose with Material 3 Design
* **Architecture**: MVVM with `ViewModel` and `StateFlow` / Compose `State`
* **Authentication**: Firebase Authentication (`firebase-auth`)
* **Database**: Cloud Firestore (`firebase-firestore`)
* **Dependency Management**: Gradle Version Catalog (`libs.versions.toml`) & Kotlin DSL

---

## 🔐 Cloud Firestore Database Schema & Security Rules

### Database Collection: `users`

Each document ID is the user's **Firebase UID**.

| Field Name | Type | Description |
| :--- | :--- | :--- |
| `uid` | String | Firebase Authentication User ID |
| `name` | String | Full Name of the patient |
| `email` | String | Email address of the patient |
| `role` | String | User role (Default: `"patient"`) |
| `createdAt` | Number | Timestamp of account creation (epoch ms) |

### Security Rules (`firestore.rules`)

Add these rules to your **Firebase Console > Firestore Database > Rules**:

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /users/{userId} {
      // Users can read and update only their own profile
      // Prevents users from arbitrarily changing their role
      allow read, update: if request.auth != null && request.auth.uid == userId
        && (!request.resource.data.diff(resource.data).affectedKeys().hasAny(['role']) || request.resource.data.role == resource.data.role);
      
      // Allow creation during sign up if authenticated uid matches userId and default role is patient
      allow create: if request.auth != null && request.auth.uid == userId
        && request.resource.data.role == 'patient';
    }
  }
}
```

---

## 🚀 Getting Started

### Prerequisites

* **Android Studio**: Android Studio Ladybug / Jellyfish (2024.2+) or newer.
* **JDK**: Version 11 or 17.
* **Android SDK**: `compileSdk = 37`, `minSdk = 24`.

### Installation & Setup

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/kathirselvan761/Nutricare.git
   cd Nutricare
   ```

2. **Add `google-services.json`**:
   Ensure `google-services.json` is placed in the `app/` directory (`app/google-services.json`) with your matching package name (`com.example.nutricare`).

3. **Build the Application**:
   ```bash
   ./gradlew assembleDebug
   ```

4. **Run on Emulator or Physical Device**:
   Open the project in **Android Studio** and click **Run 'app'** (`Shift + F10`).

---

## 🧪 Testing Scenarios

| Scenario | Expected Behavior |
| :--- | :--- |
| **New User Sign Up** | Input fields validated; user created in Firebase Auth & Firestore; navigates to Profile Setup. |
| **Input Validation** | Empty fields, invalid email format, short passwords (< 8 chars), or mismatched confirm passwords trigger friendly inline errors. |
| **User Login** | Authenticates with Firebase; navigates to Patient Home Dashboard. |
| **Session Persistence** | User stays logged in upon app restart without needing to sign in again. |
| **Forgot Password** | Sends password reset email link to the entered email address. |
| **Logout** | Signs user out of Firebase Auth and returns to Login screen. |

---

## 📄 License

This project is developed for **NutriCare AI**. All rights reserved.
