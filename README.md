
# Tutoring Application Using Linked List

## Project Overview

This project is a tutoring management system developed for a Data Structures and Algorithms class. It utilizes a custom linked list to manage the queue of students, store student history, and process student login information. The project demonstrates the use of linked lists, file handling (via CSV), and Java object-oriented programming principles.

## Features

- **Student Management**: Add, remove, and manage students using a linked list.
- **Queue Management**: Maintain a queue of students for tutoring sessions.
- **CSV Integration**: Import and export student data using CSV files.
- **Student History**: Track student activities and session history.
- **Admin and Student Login**: Simulated login functionality for both admins and students.

## Technologies Used

- **Programming Language**: Java
- **Build Tool**: Maven
- **Data Structure**: Custom Linked List (`StudentLinkList.java`)
- **File Handling**: CSV export and import (via `csvExport.java`, `CSVReader.java`, `CSVWriter.java`)
- **Framework**: None (Standalone Java Application)

## Setup

### Prerequisites

- Ensure you have **Java** and **Maven** installed on your system.

### Steps to Run the Project

1. Clone the repository:
   ```bash
   git clone <your-repo-url>
   cd Project1
   ```

2. Build the project using Maven:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   java -jar target/<project-jar>.jar
   ```

## Data Structure: Linked List

This application uses a custom implementation of a linked list to manage student information. The `StudentLinkList.java` file provides methods to:

- Add a student to the queue.
- Remove a student after a session.
- Traverse through the list to access student details.
  
The linked list allows dynamic management of student records and is optimized for adding and removing students in real time.

## CSV Integration

CSV files are used for data persistence, allowing easy import and export of student records. The following utility classes handle CSV operations:

- `csvExport.java`: Exports student data to CSV format.
- `CSVReader.java`: Reads student data from CSV files.
- `CSVWriter.java`: Writes data into CSV files.

## Usage

- **Adding Students**: Students can be added to the queue by entering their details.
- **Viewing Student History**: Admins can view the history of students in previous tutoring sessions.
- **Managing Queues**: Tutors can manage the current queue of students waiting for a session.

## License

[Add your license here if applicable]
