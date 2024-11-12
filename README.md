**Assignment 3**

Android Skills you will learn 🎖️
In this assignment, you will develop a more comprehensive event management Android application. This application project will be the next phase of the project you implemented as part of Assignment 2. Please read Assignment 1 to find out more about the theme for this unit's assignments (event management app).

Note: as emphasised previously, the relationship between category and events is one-to-many. i.e. one category can contain one or more events. But one event can only be part of one category.

You will extend your previous assignment to add the following new functionality:

Store events & event categories using the Room Database library. This will allow you to understand how database CRUD (Create Read Update & Delete) operations work in a database.

Host external webpages within the app using WebView, without relying on the default web browser on the phone.

Use the embedded Google Map libraries to show a category's location on the Map and leverage Geocoder to decode user-provided input to a physical address.

Allow users to interact with your app using Gestures. (by ingesting TouchEvent(s) and translating them into Android Gestures) 

By combining these elements, you'll create a more advanced Android application.

Assignment 3 Specifications
Assignment 3 is due on Friday of Week 11, 11:55 PM. This is an individual assignment, submission via Moodle only. What to submit on Moodle?

Assignment 3 interview will be conducted in labs in week 12. Please note, a "no interview no marks" policy is applied across all assignments.

Generative AI tools cannot be used in this assessment task
In this assessment, you must not use generative artificial intelligence (AI) to generate any materials or content in relation to the assessment task.

As mentioned in the Assignment Theme section, this assignment is about developing the next phase of the Events Management App (EMA) and building upon Assignment 2's output by adding the functionalities listed below under the tasks section.

This is an advanced assignment, worth 40% of your overall score. Assessed Lab tasks 9 & 10 will contribute 8% to this (4% each)

Planning your ideal grade
Look at the table below to plan ahead for your desired Grade for this assignment


Notes: 

You can get a credit by coding and completing A, B + attempting C partially.

If you want to aim for HD in this assignment, you need to code and complete A, B, C and at a minimum, have attempted or completed Section D. 

If you want to aim for HD++, you need to code and complete A, B, C and D and have attempted or completed Section E (Gestures).

You are encouraged to plan ahead for your desired grade boundary and start working on your assignment in advance. 

Do not start working on later sections before completing the earlier sections (i.e. do not start working on Section D, before completing A, B, and C in that order). 


B. Database (15 marks)
Notes on implementing Data Storage (Entities)
As part of Assignment 2, entities must be implemented as Room database entities (Event & Event Category).

LiveData of type List<Entity> will be used to observe changes and receive multiple events & event categories to display.

The data stored for user details remains SharedPreferences. In other words, you do not need to create an entity to store User details. 

1. Database Configuration (2 marks)
1.1 New Database Class
Import required dependencies for the room database library.

Create a new EMA Database class to ensure the two entities are specified and the appropriate database names.

1.2 DAO, Repository & ViewModel
New Interface DAO to hold all CRUD operation methods listed below under other tasks.

The new Repository & ViewModel class is used to interact with the database.


2. Event Category (5 marks)
2.1 Event Category - Room Entity (1 mark)
Transform previously created EventCategory entity to a room database entity.

Add a new unique field "id" (Primary Key), this will be auto-generated on save of the new EventCategory record.

Add another field called "eventLocation" (String). This will be received from the user entering location of the category in the Add Category form (see 2.2).

The CategoryId field previously created should stay as is (eg: CZX-1213) and must be stored in the database. It's just another value saved in the database table as a String.

Required Getter & Setter methods are used instead to access event category attributes.


Figure 1: Modifying 'New Category Form' to ask user for Location when creating new categories. We will use this in the assignment to search and display the location on a map using Google Maps.


2.2 Event Category - Save (1 mark)
Modify the "New Category Form" UI to add a EditText to fetch location of new categories. See Figure 1.

Update this Event Category Form to save category data into the database.

Use the ViewModel method to save a new record.

2.3 Event Category - Read All (2 mark)
Update the FragmentListCategory to read data from the database and remove/comment out old code of reading data from Shared Preferences. Similar to Assignment 2, this fragment is loaded inside Dashboard Activity.

Use the ViewModel method to read data from the database into FragmentListCategory.

2.4 Event Category - Delete All (1 mark)
Update the "Delete All Categories" option in the options menu to delete all category records from the EventCategory database table.

You do not need to delete events of the deleted categories. If you'd like to, feel free to code this for your own knowledge but this is not required as part of the assessment and there are no marks for this behaviour.

3. Event  (8 marks)
3.1 Event - Room Entity (1 mark)
Transform previously created Event entity to a room database entity.

Add a new unique field "id" (Primary Key), this will be auto-generated on save of the new Event record.

The EventId field previously created should stay as is (eg: EME-10776) and must be stored in the database. It's just another value saved in the database table as a String.

Required Getter & Setter methods are used instead to access Event attributes.

3.2 Event - Save (1 mark)
Update previously implemented Dashboard Activity where you had a New Event Form to save event data into the database.

Use the ViewModel method to save a new record.

3.3 Event - Update Category Count (3 mark)
Before saving a new Event record to the database, update the validation logic implemented as part of A2 ("Category does not exist") to validate against the list of categories saved in the database. In other words, before saving an Event you should read the list of all categories from the database and match user-provided input for the CategoryId field. If the user provided input match, only then allow a new Event record to be saved in the database.

Once an Event record is saved, increment the value of EventCount by one of the specified category record, identified by CategoryId input provided by the user. Similar to A2, however, you need to update the Category record saved in the database.

3.4 Event - Read All (2 marks)
Update the FragmentListEvent to read data from the database and remove/comment out old code of reading data from Shared Preferences.

Use the ViewModel method to read data from the database into FragmentListEvent.

3.5 Event - Delete All (1 mark)
Update the "Delete All Events" option in the options menu to delete all event records from the Event database table.

Note: Do not update the EventCount of deleted event's categories, feel free to try for your knowledge but not required as part of the assessment.


C. Google Maps (7 Marks)
4.1 Google Maps Activity (2 marks)
Create a new Activity to display Google Maps, you can call it GoogleMapActivity

On click of each category record (each card in the RecyclerView of FragmentListCategory), launch GoogleMapActivity with the location of the clicked category passed on to GoogleMapActivity.

4.2 Geocoder (3 marks)
Inside GoogleMapActivity, find the category location using Geocoder and move the map to the specified location. 

4.3 User Experience (2 marks): 
Add a new Marker to identify category location on the map with title = location name value from the New Event Category (either a city or country).

Set the zoom level of the map to 10.

If the user specified location does not yield any addresses, keep the state of the map as default and show a Toast message "Category address not found".


Figure: Clicking on a Category (each card in the RecyclerView of FragmentListCategory) results in the Category location being shown on a Google Map embedded within the app. 

D. Web Results
5.1 Event Google Result Activity (6 marks)
Create a new Activity and use a WebView to display the Google results of the Event, you can call it EventGoogleResult. Remember to add the necessary permissions to access the internet.

On click of each event record (each card in the RecyclerView of FragmentListEvent), launch EventGoogleResult with the name of the clicked event's name passed on to EventGoogleResult.

Once on EventGoogleResult, build the URL by appending the Event name to the URL as shown below.

https://www.google.com/search?q=Melbourne Cup

In the above example your app will put the the name of the event being clicked where 'Melbourne Cup' is.

Make sure when user clicks on Event card, it is opening in the embedded WebView (5.1.1) and not in Android's default browser.


Figure: Clicking on an event name makes the app open an activity with google search results for that event's name.



E. Gestures
6. Gestures (4 marks)
On the Dashboard activity add a new touchpad towards the bottom of your screen for your gestures, this touchpad will be used to identify user gestures. 

Implement Double Tap Gesture: When the user double taps on the touchpad area, save new event record (i.e. do the same behaviour as FAB).

Implement Long Press Gesture: When user long presses on the touchpad area, Clear All fields (just the EditTexts & Switch reset to false).
