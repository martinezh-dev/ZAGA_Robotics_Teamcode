# FTC Lead Programmer Design Journal

**Author:** Andrew Matelis  
**Team:** Gonzaga Eagles Robotics  
**Season:** 2025-2026  
**Role:** Lead Programmer

## Problem Statement

The team needed a robust **autonomous system** for our FTC robot that could perform the following:

- Navigate from four potential starting positions (**Left Corner, Left Edge, Right Corner, Right Edge**) reliably
- Execute precise movements using **mecanum drivetrain** with encoder-based timing
- Control game mechanisms including the **elevator, grabber, and ball shooter** to score points autonomously
- Be flexible for future modifications and allow other team members to understand and build upon the code

The challenge was compounded by the fact that **the previous lead programmer had left**, leaving partially documented code that required analysis, refactoring, and testing

## Challenges and Iterations

### Challenge 1: Understanding Legacy Code
- **Issue:** Previous autonomous routines were poorly documented. Movement sequences, motor powers, and timing were scattered across multiple classes
- **Action:** Analyzed each OpMode to map out the logic. Created a **visual flow diagram** of robot motion and servo activations
- **Result:** Identified redundant or conflicting code sequences and extracted a **BaseAuto class** to unify common functions (**motors, elevator, grabber**)

### Challenge 2: Multi-Mode Autonomous
- **Issue:** Each starting position had its own separate OpMode, making testing and maintenance difficult
- **Action:** Consolidated the logic into a **FourModeAuto** class with a **pre-match selection menu** using the gamepad D-Pad
- **Iteration:**  
  - Added **encoder-based movement functions** with adjustable speed and duration  
  - Standardized **elevator and grabber sequences**  
  - Added **telemetry outputs** for debugging during competition
- **Result:** Reduced autonomous routines from six separate classes to one flexible, maintainable, and fully tested OpMode

### Challenge 3: Ensuring Reliability
- **Issue:** Initial runs had inconsistent shooting performance and mecanum drift
- **Action:**  
  - Normalized motor powers to prevent exceeding hardware limits  
  - Tuned sleep durations for timed movements and shooting sequences  
  - Introduced **telemetry logging** for real-time monitoring
- **Result:** Achieved consistent autonomous behavior under different field conditions and robot loads

### Challenge 4: Collaborative Code Base
- **Issue:** Team members had varying levels of programming experience
- **Action:** Refactored code with **clear comments, descriptive function names, and standardized units**
- **Result:** Allowed other team members to modify autonomous routines safely and confidently

## Mentorship

- Guided a **junior programmer** through interpreting existing code and repurposing motor and servo functions
- Created mini-tutorials on:  
  - Normalizing mecanum wheel powers  
  - Writing reusable functions for elevator and grabber  
  - Debugging autonomous sequences using telemetry
- Encouraged team members to **test small sections individually** before integrating into full routines
- Outcome: Two newer programmers were able to independently run, modify, and extend autonomous code by mid-season

## Lessons Learned

1. **Leadership in Engineering**  
   - Leading the autonomous programming required both technical and interpersonal skills: delegating tasks, reviewing code, and mentoring teammates  
   - Learned to balance **speed of iteration** with maintaining **code quality** for competition reliability

2. **Troubleshooting and Iteration**  
   - Autonomous programming is inherently iterative. Small timing or power changes can have a large effect on real-world performance  
   - Learned to use **telemetry effectively** to debug unexpected behavior in real time

3. **Collaboration and Knowledge Transfer**  
   - Clear documentation and standardized methods enabled knowledge transfer even when previous leads left the team  
   - Writing **modular and readable code** benefits both current and future team members

4. **Reflective Thinking**  
   - Recording each iteration, challenge, and decision clarified the engineering process and improved problem-solving  
   - Developing a design journal reinforced the value of **reflecting on process**, not just final results

## Conclusion

This project was more than coding a robot. It involved **engineering thinking, leadership, mentorship, and iterative problem-solving**. By creating a documented autonomous system with clear iterations and lessons learned, I was able to improve team capability, ensure competition reliability, and demonstrate high-level **engineering and leadership skills**
