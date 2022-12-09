# CSC207 Project - Group 95

#### Group Members: Tushar, John, Zeeshan, Mayushan, Arsal, Wahhab, Maroosh, Jeffery
#### Project: UofT Timetable Generator

About our Project: The program allows users to get auto-generated timetables based on their inputted Arts & Science courses. The user should be given the option to filter/prioritize by certain traits and tags  (e.g. by preferred day, time, professor ratings, and course-density) for the timetable options.

## Project Details

- **Entities**
    - **Course.java**: Data Class that stores the information for a given course.
    - **Meeting.java**: Data Class that stores the information for a given meeting.
    - **Session.java**: Data Class that stores the information for a given session.
    - **Timetable.java**: Data Class that stores the information for a given timetable.
    - **Collapsible.java**: Interface which checks if two Meetings can be treated as a single object.
    - **StGArtSciMeeting.java**: A class that extends meeting and implements collapsible.
    - **Filterer.java**: Class that returns a sublist of the ArrayList containing all timetables that have certain tags.
    - **TSort.java**: Class that returns a timetable which has a specific score as the value given.
    - **Tagger.java**: This class takes in a generated timetable and returns a HashSet of tags.
    - **TimetableFactory.java**: Class that helps generate timetables.
    - **TimetableReaderWriter.java**: Class that saves Timetable as .json file and returns it as a List.
    - **TimetableValidator.java**: Class that checks if a timetable has no time conflicts.
    - **TimetableViewer.java**: Class that allows the user to view timetables.


- **Use Cases**
    - **RateMyProfSorter**
    - **auto_complete**
    - **course_builder**
    - **filter**
    - **persistence**
    - **timetable_generation**
    - **timetable_view**
    - **waitlist**


- **Presenters**
    - **ErrorWindow.java** - Displays error


- **Gateways**
    - **CourseAPI.java**
    - **StGArtSciAPI.java**
    - **RateMyProfGateway**


- **Controllers**
    - **AppController.java**


## Features
- Search feature equipped with autocomplete for all UofT Arts & Science Courses based on selected year and semester
- Timetable Persistence to save preferred timetables
- Visualization of timetables and tags/filters through UI
- Generation of timetables based on input courses
- Access to the Arts & Science API to access courses
- Access to the RateMyProf API to access professor ratings
- Sort timetables by Timetable Tags and Filters
- Sort timetables by Waitlist and RateMyProf features

## How to run
**Terminal**

Ensure that a JDK version `18` or above and Gradle are installed on your machine.

```
git clone https://github.com/CSC207-2022F-UofT/course-project-group-95-lightning-mcqueen-ba2240.git
cd course-project-group-95-lightning-mcqueen-ba2240
```

Once you are in the directory build the project with Gradle

```
gradle wrapper
```

This should install all the required dependencies. Run the project with Gradle

```
./gradlew run
```

**Intellij**

After cloning the project using Git or making a new project from Version Control with the repository URL, open the project in Intellij. Intellij should automatically install the required dependencies. Then open the Gradle toolbar/shelf and go to: 

Tasks -> application -> run

This will build and run the program
