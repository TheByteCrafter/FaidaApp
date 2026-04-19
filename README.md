Faida App 🇰🇪

Faida (Swahili for "Profit") is a modern, high-performance personal finance tracker built with Jetpack Compose. It features a sleek "Neon-Glassmorphism" UI design, allowing users to manage their income, track expenses, and gain insights into their financial health with ease.

✨ Features

Dashboard Overview: Real-time calculation of total profit, income, and expenses.

Neon-Glassmorphism UI: A premium dark-themed interface with vibrant neon accents and glass-like components.

Transaction History: Categorized list of all financial activities with sticky date headers for easy navigation.

Financial Insights: Visual breakdown of spending by category with percentage-based progress indicators.

Dynamic Navigation: Seamless bottom navigation flow between Home, History, and Insights.

Instant Entry: Modal bottom sheet for quick transaction logging without leaving the main screen.

🛠 Tech Stack

UI: Jetpack Compose (Declarative UI)

Architecture: MVVM (Model-View-ViewModel)

Navigation: Compose Navigation

Database: Room Persistence Library (Local SQLite)

State Management: Kotlin Coroutines & StateFlow

Theme: Material 3 with custom color palettes

📸 Screenshots

Home

History

Insights

Dashboard & Recent Activity

Chronological Transactions

Spending Breakdown

🚀 Getting Started

Prerequisites

Android Studio Ladybug (or newer)

JDK 17+

Android SDK 34+

Installation

Clone the repository:

git clone [https://github.com/TheByteCrafter/faida-app.git](https://github.com/TheByteCrafter/FaidaApp).git)


Open in Android Studio:
Wait for Gradle to sync all dependencies.

Run the app:
Select your emulator or physical device and click the Run icon.

📂 Project Structure

components/: Reusable UI elements (Headers, Nav Bars, Transaction Rows).

models/: Data classes and Room entities.

screens/: Top-level screen composables (Main, History, Insights).

viewModels/: Business logic and UI state management.

repository/: Data layer for abstraction between Room and UI.

utilities/: Database configuration and helpers.

🤝 Contributing

Contributions are welcome! If you have ideas for new features or UI improvements:

Fork the Project.

Create your Feature Branch (git checkout -b feature/AmazingFeature).

Commit your Changes (git commit -m 'Add some AmazingFeature').

Push to the Branch (git push origin feature/AmazingFeature).

Open a Pull Request.

📄 License

Distributed under the MIT License. See LICENSE for more information.

Built with ❤️ for better financial management.
