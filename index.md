# Blog Updates

### 6/19/2021: Major Fixes
Since my last blog update, I have implemented and fixed everything I described in that post. 

First I added the edit class functionality to edit a class. It was relatively simple, I extended `AddClassFragment`, using and overriding methods I set as `protected` and displaying the Delete Assignment button. However, after implementing it, I found that it had the same issue as deleting classes, where I could not edit the assignment/class after I deleted one. Instead, every time I clicked on the edit button, the app would exit back into the `AssignmentsFragment` (where the classes/assignments fragments are). The assignment list would also not update until the fragment view was recreated. 

The issue was with the listeners, which was starting to become a theme for many of my application's issues. Turns out, every time I would open the `EditClassFragment`, the listener that acts when the user chooses to delete the class would trigger and exit the fragment. Luckily, it owuld not delete the assignment because the listener was attached before getting the assignment information. I fixed this by having another `MutableLiveData` object that stored a boolean, which would indicate whether the user actually wanted to delete the assignment. I also implemented this for classes as well.

The issue remained where the assignment list would not update when an assignment was inserted or deleted. First thing I did was add functionionality for  when a user taps to open an assignment and that assignment does not actually exist, the app will show a toast message saying "Error finding assignment" and then update the list to get rid of the element. It is still not a fix though, so I added and updated a couple listeners. 

Whenever an assignment was deleted or added, the app would trigger a listener in `UpcomingFragment` (where the assignment list is). My previous attempts tried to have the UI update its data from the database, but more often than not the database would not have finished its query yet. To circumvent that, I had the UI manually insert the new object or delete it without having to call on the database. Effectively the UI would update itself even before the database finished its query. 

To fix the assignment list not loading when the app started, I chose to learn a little about multithreading. So, Android's `Room` class runs asynchronously on different threads from the main, UI thread of the application. My`AppDatabase`, which extends `Room`, uses Java's `java.util.concurrent` class ot run asynchronously, including `Executors`. The code I used for it was mostly based on Google's guides, so I first made the database I did not understand how this all worked. 

I learned about the Java class `Handler`, and it works similarly to `sighandler`s for processes in C. When a new thread is created, the handler can then call on the main thread to update the UI. So what I did was create a new thread that would keep looping until the database would stop returning null (meaning it was instantiated) and then trigger a listener to update the assignment list with the database's values.

I made some visual improvements and theme color changes, However, I am still dissatisfied with the app's appearance, so I will look into how to make custom views and drawables. The app also has very poor performance, and I have to wait a few seconds even after the home page is rendered to navigate, or else the app may crash. I also want to implement a calendar that will show assignments for each day, but I may need to heavily customise Android's `CalendarView` to achieve this. 

Here are some screenshots:

<img src= "https://user-images.githubusercontent.com/44488917/122657412-6307f280-d131-11eb-9141-2e02c8692798.png" width="300" /><img src= "https://user-images.githubusercontent.com/44488917/122657432-8763cf00-d131-11eb-8ec8-a13b76b33094.png" width="300" /><img src= "https://user-images.githubusercontent.com/44488917/122821819-4d1e3d00-d2ab-11eb-9513-dc01495ab11c.png" width="300" />
<img src= "https://user-images.githubusercontent.com/44488917/122822014-8c4c8e00-d2ab-11eb-8592-2d1ac9c9b3f4.png" width="300" /><img src= "https://user-images.githubusercontent.com/44488917/122822614-493eea80-d2ac-11eb-9df6-9c21645a3191.png" width="300" /><img src= "https://user-images.githubusercontent.com/44488917/122822816-9327d080-d2ac-11eb-9414-858c4f7c8628.png" width="300" />

### 5/26/2021: More Features
Over the past few days I have fully implemented the dialogs for adding a class and priority level  to an assignment with dialogs, and Toasts for when a user has not entered a due date or assignment name respective. I have also updated the AddClassFragment with new button styles, but I still must add a dialog for choosing a class color and displaying that color. I also want to add colors to the priority list. The app also needs a new appearance. I don’t want it to look like the default template forever.

I have also successfully implemented a swipe-to-complete option for classes. It extends the `ItemTouchHelper.SimpleCallback`, making it where the headers are un-swipeable, and calls the recyclerview adapter (`AssignmentListAdapter`) when an item has been swiped away. If the assignment is incomplete, it adds it to the complete list, and if the item is complete and swiped, it is added to the incomplete list. 

However, there are still issues. When a new assignment is added, the task list does not immediately update. The fragment has to be opened then closed again. I also still need the “splash screen” to prevent the database from crashing the app on startup. Deleting a class causes issues where I can not edit other classes. There are multiple other little things I need to fix as well. 

Speaking of editing, I also need to add functionality to edit assignments, although I can use the functionality of editing a class as an example to follow. 

### 5/22/2021
I have made a few changes to the `UpcomingFragment` and `AddAssignment` classes, along with some new SQLite queries. After some brief research, I found how to make an SQL query that can select an assignment’s isComplete (aka if the assignment has been marked complete) is true or false, so I added functions for that throughout my code. I also learned how to add headers into my recyclerview, and made the AssignmentData object implement Comparable, so assignments are now sorted by due date. With some text reformatting and name changes, the Tasks page/fragment, formerly the Upcoming page, looks like this:

![image](https://user-images.githubusercontent.com/44488917/122618272-ea833200-d05b-11eb-8681-9b30868050f3.png)

I changed the spinners in the `AddAssignmentFragment` to buttons that open dialogs. I found that it felt inconsistent having some things open dropdown items while adding a date would open a calendar dialog, so having all assignment details either be a test input or dialog would make it be easier to use. It is not fully implemented, but I plan on making the same change to the AddClass fragment when I am done. 

### 3/20/2021
I fixed the “Complete Assignment” and “Delete Class” buttons, and now they have their own colors too. The assignments details now also displays  the priority of the assignment. I had noticed some issues when the description of an assignment exceeded a certain number of lines, so now the layout for adding assignments has a ScrollView. I plan on adding that to other fragments as well. 

### 3/14/2021
I added toe ability to tap on the assignment and see some of its details, like name and due date. I added a “Complete Assignment” button in the assignment info fragment wit ha checkmark symbol, but there are some issues with showing text. I also added a trash can symbol to the “Delete Class” button, but now I also have the same issue.

I changed some of the constructors for `AssignmentData` so it includes a priority number by default, and I also learned how to store the index of a spinner, so now the user is able to store the priority level of their assignment. I need to learn how to use the AssignmentDao to get a list of homework assignments that are not marked as complete to I can fully implement a completion system. I also want to add a custom cardview for priorities so it can display the colors of different priorities, and eventually different class colors too. 

### 1/26/2021
Today I added the ability to add assignments. I must set up spinners so that I can store the class that the assignment is tied to, along with the assignment’s urgency/priority level. I also added the ability to delete a class, but the app navigates to the upcoming tab instead of the classes one. The more and more I work with the app, I realize that I should switch to a bottom navigation view instead of the top one I am using right now. For now, though, I want to complete the upcoming fragment. 

![image](https://user-images.githubusercontent.com/44488917/122824483-b5225280-d2ae-11eb-8f72-e615d8c73c7b.png)

### 1/21/2021
I added a pseudo-splash screen, so now there are not any issues with the app crashing when I wipe its data. Its still only a temporary fix, and ideally I would add a proper database and splash screen in the future. Now finally I can get to work on adding functions to the app that allow for adding and completing assignments.


### 1/20/2021:
Today I managed to fully add the ability to edit a class’s details. The only aspects left missing that I want to address for classes is deleting them and properly storing and displaying their custom colors. 

I spent the rest of my time trying to create a proper assignment list displayed from the assignment data in my app’s database, along with all the queries and columns for information I want to store. However, for some reason the assignment part of the database is not properly prepopulating like the classes part, and I cannot figure out why. My database is currently dependent on the application context, and is a singleton, so this may be impeding me in figuring this out. 

Note: I think(?) I might have managed to fix this temporarily. I think the origin of the error was that the app was not able to create the database information before creating the view for the Upcoming fragment. This is evidenced by when I clear the app’s internal storage it crashes on launch. I might add a splash screen to but time for the app or try to shift to Kotlin coroutines for faster loading. 

### 1/19/2021:
Today I learned the basics of how actions/animations work with **Android**. I can now add smoother transitions between different fragments in my app. However, I still am perplexed as to how to fix the issue when I exit the fragments that add classes/get class details and the tablayout sets the wrong tab but the correct classes fragment.

I also started using bundles so when you click on one of the classes it opens the proper class’s information. I tried using safe args but I found using bundles much simpler for what I wanted. Next is to touch it up and add a proper functionality for editing a class and removing one. I also want to add animations and more functions to the floating action button on the bottom right (perhaps a single, universal floating action button for all fragments?).




