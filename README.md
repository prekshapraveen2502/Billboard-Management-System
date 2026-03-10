# Billboard Management System

A Java Swing enterprise application utilizing Object-Oriented Design (OOD) principles to simulate a real-world billboard advertising ecosystem. The application demonstrates complex interactions between advertising agencies, billboard operators, city officials, and utility companies.

## Features

- **Enterprise Ecosystem:** Simulates a localized network containing 4 distinct Enterprises:
  - **SkyView Billboards** (Billboard Operators)
  - **AdSpark Agency** (Ad Agencies)
  - **Boston City Services** (City Approvals & Inspections)
  - **MassPower Utility** (Power Grid Maintenance)
- **Workflow & Queue Management:** Cross-departmental communication utilizing a robust `WorkQueue` structure to handle booking requests, permit approvals, and power maintenance tickets.
- **Embedded Database:** Uses `db4o` for native, seamless Java object persistence.
- **Data Generation:** Uses the `javafaker` library to synthesize mock operational data upon initial runtime.
- **Role-Based Access Control:** Dedicated interfaces tailored to 10+ distinct roles.

## Tech Stack

- **Language:** Java 17
- **UI Framework:** Java Swing
- **Database:** `db4o-nbm` (Object Database v2.0.0)
- **Dependency Management:** Maven (`pom.xml`)

## Getting Started

1. Clone the repository and open the project in your preferred IDE (IntelliJ, Eclipse, NetBeans).
2. Ensure you have **Java 17** and **Maven** installed.
3. Build the project to resolve the dependencies (specifically `db4o` and `AbsoluteLayout`).
4. Run `src/main/java/ui/MainJFrame.java`.
5. Login using default system credentials (e.g., username: `sysadmin` | password: `admin`).
