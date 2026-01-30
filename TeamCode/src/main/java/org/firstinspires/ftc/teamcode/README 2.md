# FTC Robotics Code – Gonzaga Eagles 2025-2026 Season

This repository contains the full control code for the Gonzaga Eagles FTC robot for the 2025-2026 season. It powers the robot through both **autonomous and tele-operated phases**, managing drivetrain movement, game mechanisms, and other subsystems. The codebase was designed for **reliability, readability, and rapid iteration** during build and competition periods.

## Project Overview

Our team structured the code around clear subsystems and operation modes so that each part of the robot could be **developed, tested, and debugged independently**. The goal was to support both autonomous and driver-controlled phases while maintaining consistent performance across practice and competition fields.

During this season, I contributed to **developing a four-mode autonomous system**, analyzing and repurposing previous code, and mentoring newer team members on both programming logic and testing procedures. The work involved repeated iteration, debugging on the field, and coordinating updates with other programmers.

Core priorities of the project:

* Clean and modular architecture
* Easy tuning and debugging
* Hardware safety and fault tolerance
* Fast deployment during matches

## Robot Capabilities

Depending on configuration, the robot can:

* Drive with precise mecanum control
* Navigate autonomously using encoders, timers, and sensors
* Operate game mechanisms such as intakes, slides, elevators, grabbers, and ball shooters
* Respond consistently in TeleOp mode with adjustable driver control
* Integrate sensors including IMU, distance sensors, and limit switches

### Folder Roles

* `autonomous` – pre-programmed match routines
* `teleop` – real-time driver control logic
* `subsystems` – isolated physical mechanisms
* `hardware` – hardware mapping for motors, servos, and sensors
* `util` – math helpers, PID controllers, and timing tools

## Setup Instructions

### Requirements

* Android Studio
* FTC SDK installed
* REV Control Hub or Expansion Hub
* Properly configured robot hardware

### Installation

1. Clone this repository into your FTC workspace
2. Open the project in Android Studio
3. Allow Gradle to sync completely
4. Verify hardware names match the configuration file
5. Build and deploy to the Control Hub or Driver Station

## Configuration

All hardware names and tuning constants are centralized for safety and convenience.

* Motor and servo names are defined in the hardware mapping classes
* PID values and movement constants are stored in `RobotConstants`
* Game-specific values can be adjusted without modifying core logic

Always double-check hardware mappings before running on a real robot.

## Autonomous Mode

Autonomous routines are written as independent OpModes. Each routine:

* Initializes required subsystems
* Executes a defined sequence of actions
* Uses encoders, timers, or sensors for control

The code is designed to be **deterministic and repeatable**, even under varying field conditions.

## TeleOp Mode

TeleOp code emphasizes **driver responsiveness and consistency**:

* Adjustable drive speed scaling
* Independent control of each mechanism
* Safety limits to prevent hardware damage
* Clear mapping between gamepad input and robot behavior

## Testing and Iteration

The team tested and iterated code continuously:

1. Analyzed legacy routines and mapped movement sequences
2. Refactored and modularized code for clarity
3. Added telemetry for debugging and tuning
4. Conducted repeated field tests, recording outcomes
5. Mentored new programmers to replicate testing and make safe updates

This workflow ensured reliable performance and **team-wide understanding of robot behavior**.

## Coding Standards

* Descriptive variable and method names
* One responsibility per class
* No hard-coded values in logic
* Comments explain reasoning and intent

## Safety Notes

* Always lift the robot off the ground for first tests
* Verify motor directions before full-power operation
* Keep hands clear during autonomous testing
* Stop immediately if behavior is unexpected

## Contributing

Team members are encouraged to:

* Create new branches for major changes
* Test code thoroughly before merging
* Document new subsystems and features

Clear communication and version control discipline are essential for team collaboration.

## Reflection

Contributing to this codebase gave me hands-on experience in **engineering iteration, collaborative problem solving, and mentorship**. Working as part of a team to analyze legacy code, implement a four-mode autonomous system, and guide new programmers strengthened both my technical skills and leadership abilities.

## License

This project is intended for educational and competition use within the **FIRST Tech Challenge** framework.

## Acknowledgments

Developed by the **Gonzaga Eagles Robotics Team** through iterative design, testing, and competition experience. Based on the official FTC SDK and community best practices. Guided by Gonzaga faculty: **Mr. Nardella and Mr. Horan**.
