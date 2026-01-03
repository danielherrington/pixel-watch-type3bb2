# Ressence Type 3 BB2 Replica (Pixel Watch 4)

This project contains the source code for a custom **Wear OS Watch Face** designed for the Pixel Watch 4 (API 36). It replicates the unique orbital mechanics and aesthetic of the **Ressence Type 3 "Black Black 2"** using the modern `Watch Face Format` (XML).

## The Mechanism (ROCS Simulation)
This watch face simulates the **Ressence Orbital Convex System (ROCS)** completely in XML:
*   **Main Disc**: Rotates 360° every hour to indicate minutes.
*   **Planetary Sub-dials**: Orbit the center of the watch.
*   **Counter-Rotation Logic**: To keep the sub-dials upright (readable) while they orbit, each sub-dial group is programmed to **counter-rotate** at the exact inverse speed of the main disc (`Rotation = -[MAIN_DISC_ROTATION]`).

## Features
*   **Format**: Native Android `Watch Face Format` (declarative XML).
*   **Assets**: Custom "Black Black 2" VectorDrawables with simulated oil-filled radial effects.
*   **Complications**:
    *   **Minutes**: Indicated by the rotation of the main disc.
    *   **Hours**: Standard 12-hour sub-dial.
    *   **The Runner**: A unique 180-second sweeper hand (Planetary Seconds).
    *   **Day**: 7-segment ring with Orange highlights for the weekend.
    *   **Oil Temp**: Mapped to **Battery Level**, styled as a bellows gauge (Blue = Cold/Full, Red = Hot/Empty logic).

## Installation
1.  Clone this repository.
2.  Open **Android Studio** and create a new **Wear OS Watch Face** project (select "No Activity").
3.  Replace the `res` directory in your new project with the `res` directory from this repository.
4.  Build and Run on your Pixel Watch.

## Project Structure
*   `res/raw/watchface.xml`: The core logic for the planetary system.
*   `res/drawable/*.xml`: Vector graphics for the discs and hands.
