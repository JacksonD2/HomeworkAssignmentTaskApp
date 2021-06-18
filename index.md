# Blog Updates

### 1/19/2021:
Today I learned the basics of how actions/animations work with Android. I can now add smoother transitions between different fragments in my app. However, I still am perplexed as to how to fix the issue when I exit the fragments that add classes/get class details and the tablayout sets the wrong tab but the correct classes fragment.

I also started using bundles so when you click on one of the classes it opens the proper class’s information. I tried using safe args but I found using bundles much simpler for what I wanted. Next is to touch it up and add a proper functionality for editing a class and removing one. I also want to add animations and more functions to the floating action button on the bottom right (perhaps a single, universal floating action button for all fragments?).

### 1/20/2021:
Today I managed to fully add the ability to edit a class’s details. The only aspects left missing that I want to address for classes is deleting them and properly storing and displaying their custom colors. 

I spent the rest of my time trying to create a proper assignment list displayed from the assignment data in my app’s database, along with all the queries and columns for information I want to store. However, for some reason the assignment part of the database is not properly prepopulating like the classes part, and I cannot figure out why. My database is currently dependent on the application context, and is a singleton, so this may be impeding me in figuring this out. 

Note: I think(?) I might have managed to fix this temporarily. I think the origin of the error was that the app was not able to create the database information before creating the view for the Upcoming fragment. This is evidenced by when I clear the app’s internal storage it crashes on launch. I might add a splash screen to but time for the app or try to shift to Kotlin coroutines for faster loading. 

### 1/21/2021
I added a pseudo-splash screen, so now there are not any issues with the app crashing when I wipe its data. Its still only a temporary fix, and ideally I would add a proper database and splash screen in the future. Now finally I can get to work on adding functions to the app that allow for adding and completing assignments.

### 1/26/2021
Today I added the ability to add assignments. I must set up spinners so that I can store the class that the assignment is tied to, along with the assignment’s urgency/priority level. I also added the ability to delete a class, but the app navigates to the upcoming tab instead of the classes one. The more and more I work with the app, I realize that I should switch to a bottom navigation view instead of the top one I am using right now. For now, though, I want to complete the upcoming fragment. 

### 3/14/2021
I added toe ability to tap on the assignment and see some of its details, like name and due date. I added a “Complete Assignment” button in the assignment info fragment wit ha checkmark symbol, but there are some issues with showing text. I also added a trash can symbol to the “Delete Class” button, but now I also have the same issue.

I changed some of the constructors for AssignmentData so it includes a priority number by default, and I also learned how to store the index of a spinner, so now the user is able to store the priority level of their assignment. I need to learn how to use the AssignmentDao to get a list of homework assignments that are not marked as complete to I can fully implement a completion system. I also want to add a custom cardview for priorities so it can display the colors of different priorities, and eventually different class colors too. 

### 3/20/2021
I fixed the “Complete Assignment” and “Delete Class” buttons, and now they have their own colors too. The assignments details now also displays  the priority of the assignment. I had noticed some issues when the description of an assignment exceeded a certain number of lines, so now the layout for adding assignments has a ScrollView. I plan on adding that to other fragments as well. 

### 5/22/2021
I have made a few changes to the Upcoming and Add Assignment fragments, along with some new SQLite queries. After some brief research, I found how to make an SQL query that can select an assignment’s isComplete (aka if the assignment has been marked complete) is true or false, so I added functions for that throughout my code. I also learned how to add headers into my recyclerview, and made the AssignmentData object implement Comparable, so assignments are now sorted by due date. With some text reformatting and name changes, the Tasks page/fragment, formerly the Upcoming page, looks like this:

I changed the spinners in the **AddAssignmentFragment** to buttons that open dialogs. I found that it felt inconsistent having some things open dropdown items while adding a date would open a calendar dialog, so having all assignment details either be a test input or dialog would make it be easier to use. It is not fully implemented, but I plan on making the same change to the AddClass fragment when I am done. 

### 5/26/2021
Over the past few days I have fully implemented the dialogs for adding a class and priority level  to an assignment with dialogs, and Toasts for when a user has not entered a due date or assignment name respective. I have also updated the AddClassFragment with new button styles, but I still must add a dialog for choosing a class color and displaying that color. I also want to add colors to the priority list. The app also needs a new appearance. I don’t want it to look like the default template forever.

I have also successfully implemented a swipe-to-complete option for classes. It extends the **ItemTouchHelper.SimpleCallback**, making it where the headers are un-swipeable, and calls the recyclerview adapter (AssignmentListAdapter) when an item has been swiped away. If the assignment is incomplete, it adds it to the complete list, and if the item is complete and swiped, it is added to the incomplete list. 

However, there are still issues. When a new assignment is added, the task list does not immediately update. The fragment has to be opened then closed again. I also still need the “splash screen” to prevent the database from crashing the app on startup. Deleting a class causes issues where I can not edit other classes. There are multiple other little things I need to fix as well. 

Speaking of editing, I also need to add functionality to edit assignments, although I can use the functionality of editing a class as an example to follow. 

