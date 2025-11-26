# ChuXi PetCare 🐾

**ChuXi PetCare** is a comprehensive Android application designed to help pet owners manage their pets' health, monitor vaccination schedules, and get real-time care advice using AI and weather data.

## 📱 Project Overview
* **Purpose:** Helps users manage pet profiles, track health records (vaccination, deworming), and provides smart care tips.
* **Target Audience:** Pet owners who want an all-in-one solution for pet care.
* **Status:** Core features completed.

## 🌟 Key Features
* **Manage Pet Profiles:** CRUD (Create, Read, Update, Delete) pet information including photos, weight, age, and health notes.
* **Virtual Vet Assistant:** Integrated **Google Gemini AI** to answer questions related to pet health and nutrition.
* **Smart Weather Tips:** Fetches real-time weather data via **OpenWeatherMap API** to provide daily care recommendations (e.g., "It's cold, keep your pet warm").
* **Cloud Sync:** All data is stored securely and synced in real-time using **Firebase Firestore**.

## 🛠 Tech Stack
* **Language:** Kotlin
* **Architecture:** MVVM (Model-View-ViewModel)
* **Database:** Firebase Firestore
* **Networking:** Retrofit, OkHttp
* **AI Integration:** Google Generative AI SDK (Gemini)
* **Image Processing:** Glide, Base64 Encoding
* **UI:** XML, RecyclerView, CardView

## 📸 Screenshots
| Home Screen | Pet Detail | AI Chatbot |
|:---:|:---:|:---:|
| <img src="URL_ANH_HOME" width="200"> | <img src="URL_ANH_DETAIL" width="200"> | <img src="URL_ANH_CHAT" width="200"> |

## 🚀 How to Run
1.  Clone this repository:
    ```bash
    git clone [https://github.com/USERNAME/ChuXiPetCare.git](https://github.com/USERNAME/ChuXiPetCare.git)
    ```
2.  Open the project in **Android Studio**.
3.  Sync Gradle to download dependencies.
4.  Add your API Keys (if necessary) in `MainActivity.kt`.
5.  Run on an Emulator or Physical Device.

## 👨‍💻 Author
** Duong Xuan Quy **
* GitHub: https://github.com/quyxhhqcb1708
* Email: quyxhhqcb1708@gmail.com
