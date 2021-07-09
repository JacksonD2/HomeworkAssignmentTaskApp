package com.example.HomeworkAssignmentTaskApp.deprecated;

public class JunkCode {
}

//Priority
        /*spinnerPriority = (Spinner) root.findViewById(R.id.spinnerPriority);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.priorities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPriority.setAdapter(adapter);
        spinnerPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                priorityNum = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Classes
        spinnerClasses = root.findViewById(R.id.spinnerClasses);
        CustomAdapter classesAdapter= new CustomAdapter(requireContext(),
                R.layout.class_row, R.id.class_name_txt, appModel.getClassList().getValue());
        adapter.setDropDownViewResource(R.layout.class_row);
        spinnerClasses.setAdapter(classesAdapter);
        spinnerClasses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                classId =  classesAdapter.data.get(i).getClassId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/


/*
---------------------------------- XML Files -------------------------------------------------
 */

/*<!--<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:id="@+id/ConstraintLayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:fitsSystemWindows="true"
        tools:context=".ui.assignments.Classes.AddClassFragment">

<Button
        android:id="@+id/buttonDeleteClass"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_marginStart="16dp"
                android:layout_marginTop="32dp"
                android:layout_marginEnd="16dp"
                android:backgroundTint="#FF4081"
                android:drawableLeft="@drawable/ic_baseline_delete_outline_24_theme"
                android:text="@string/delete_class"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintHorizontal_bias="0.0"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/buttonStartDate" />

<TextView
        android:id="@+id/textView4"
                android:layout_width="wrap_content"
                android:layout_height="0dp"
                android:layout_marginStart="16dp"
                android:layout_marginTop="16dp"
                android:text="@string/class_start_date"
                android:textSize="14sp"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/editTextInstructorName" />

<TextView
        android:id="@+id/textView6"
                android:layout_width="wrap_content"
                android:layout_height="0dp"
                android:text="@string/class_end_date"
                android:textSize="14sp"
                app:layout_constraintBottom_toBottomOf="@+id/textView4"
                app:layout_constraintStart_toStartOf="@+id/buttonEndDate"
                app:layout_constraintTop_toTopOf="@+id/textView4" />

<TextView
        android:id="@+id/textView8"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="20dp"
                android:layout_marginTop="16dp"
                android:text="@string/class_instructor_name"
                android:textSize="14sp"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/editTextClassName" />

<TextView
        android:id="@+id/textView10"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="20dp"
                android:layout_marginTop="16dp"
                android:text="@string/class_name"
                android:textSize="14sp"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/spinnerColors" />

<androidx.constraintlayout.widget.Guideline
        android:id="@+id/guideline"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintGuide_percent="0.5"
        app:layout_constraintStart_toStartOf="parent" />

<Spinner
        android:id="@+id/spinnerColors"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_marginStart="12dp"
                android:layout_marginTop="16dp"
                app:layout_constraintEnd_toStartOf="@+id/guideline"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent" />

<EditText
        android:id="@+id/editTextClassName"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_marginStart="16dp"
                android:layout_marginEnd="16dp"
                android:ems="10"
                android:gravity="bottom|start"
                android:hint="@string/add_class_name"
                android:inputType="textPersonName"
                android:textAlignment="viewStart"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintHorizontal_bias="0.0"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/textView10" />

<EditText
        android:id="@+id/editTextInstructorName"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:ems="10"
                android:hint="@string/add_class_instructor"
                android:inputType="textPersonName"
                app:layout_constraintEnd_toEndOf="@+id/editTextClassName"
                app:layout_constraintStart_toStartOf="@+id/editTextClassName"
                app:layout_constraintTop_toBottomOf="@+id/textView8" />

<Button
        android:id="@+id/buttonStartDate"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_marginTop="2dp"
                android:layout_marginEnd="16dp"
                android:text="@string/add_class_start_date"
                android:textAppearance="@style/TextAppearance.AppCompat.Body2"
                app:layout_constraintEnd_toStartOf="@+id/guideline"
                app:layout_constraintHorizontal_bias="0.0"
                app:layout_constraintStart_toStartOf="@+id/editTextClassName"
                app:layout_constraintTop_toBottomOf="@+id/textView4" />

<Button
        android:id="@+id/buttonEndDate"
                android:layout_width="0dp"
                android:layout_height="0dp"
                android:layout_marginStart="16dp"
                android:text="@string/add_class_end_date"
                android:textAppearance="@style/TextAppearance.AppCompat.Body2"
                app:layout_constraintBottom_toBottomOf="@+id/buttonStartDate"
                app:layout_constraintEnd_toEndOf="@+id/editTextClassName"
                app:layout_constraintStart_toStartOf="@+id/guideline"
                app:layout_constraintTop_toTopOf="@+id/buttonStartDate" />

<com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/fabAddClass"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginEnd="16dp"
        android:layout_marginBottom="32dp"
        android:clickable="true"
        android:src="@drawable/ic_baseline_check_24"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>--> */

/*
<!--<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/ConstraintLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true"
    tools:context=".ui.assignments.Upcoming.AddAssignmentFragment">

    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="0dp"
        app:layout_constraintBottom_toTopOf="@+id/textView17"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <androidx.constraintlayout.widget.ConstraintLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">

            <androidx.constraintlayout.widget.Guideline
                android:id="@+id/guideline3"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:orientation="vertical"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintGuide_percent="0.5"
                app:layout_constraintStart_toStartOf="parent" />

            <TextView
                android:id="@+id/textView14"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="20dp"
                android:layout_marginTop="16dp"
                android:text="@string/assignment_priority"
                android:textSize="14sp"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/spinnerClasses" />

            <TextView
                android:id="@+id/textView11"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="20dp"
                android:layout_marginTop="16dp"
                android:text="@string/class_name"
                android:textSize="14sp"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/editTextAssignmentName" />

            <TextView
                android:id="@+id/textView12"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="20dp"
                android:layout_marginTop="16dp"
                android:text="@string/assignment_name_r"
                android:textSize="14sp"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent" />

            <Button
                android:id="@+id/buttonDueDate"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_marginStart="16dp"
                android:text="@string/add_assignment_due_date"
                android:textAppearance="@style/TextAppearance.AppCompat.Body2"
                app:layout_constraintEnd_toStartOf="@+id/guideline3"
                app:layout_constraintHorizontal_bias="0.0"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/textView16" />

            <TextView
                android:id="@+id/textView16"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="20dp"
                android:layout_marginTop="16dp"
                android:text="@string/assignment_due_date_r"
                android:textSize="14sp"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/spinnerPriority" />

            <Spinner
                android:id="@+id/spinnerClasses"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_marginStart="12dp"
                android:layout_marginTop="2dp"
                android:layout_marginEnd="16dp"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/textView11" />

            <EditText
                android:id="@+id/editTextAssignmentName"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_marginStart="16dp"
                android:layout_marginTop="2dp"
                android:layout_marginEnd="16dp"
                android:ems="10"
                android:gravity="bottom|start"
                android:hint="@string/add_assignment_name"
                android:inputType="textPersonName"
                android:textAlignment="viewStart"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintHorizontal_bias="0.0"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/textView12" />

            <Spinner
                android:id="@+id/spinnerPriority"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_marginStart="16dp"
                android:layout_marginTop="2dp"
                app:layout_constraintEnd_toStartOf="@+id/guideline3"
                app:layout_constraintHorizontal_bias="0.0"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/textView14" />

            <EditText
                android:id="@+id/editTextAssignmentDescription"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_marginStart="16dp"
                android:layout_marginTop="1dp"
                android:layout_marginEnd="16dp"
                android:ems="10"
                android:gravity="bottom|start"
                android:hint="@string/add_assignment_description"
                android:inputType="textMultiLine"
                android:textAlignment="viewStart"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintHorizontal_bias="1.0"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/textView15" />

            <TextView
                android:id="@+id/textView15"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="20dp"
                android:layout_marginTop="16dp"
                android:text="@string/assignment_description"
                android:textSize="14sp"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/buttonDueDate" />

            <Button
                android:id="@+id/buttonDeleteAssignment"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_marginStart="32dp"
                android:layout_marginTop="16dp"
                android:layout_marginEnd="32dp"
                android:backgroundTint="#FF4081"
                android:drawableLeft="@drawable/ic_baseline_delete_outline_24_theme"
                android:gravity="left|center_vertical"
                android:text="@string/delete_assignment"
                android:textAlignment="center"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/editTextAssignmentDescription" />

        </androidx.constraintlayout.widget.ConstraintLayout>
    </ScrollView>


    <TextView
        android:id="@+id/textView17"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="16dp"
        android:layout_marginBottom="16dp"
        android:text="@string/required_indicator"
        android:textSize="12sp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent" />

    <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/fabAddAssignment"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginEnd="16dp"
        android:layout_marginBottom="32dp"
        android:clickable="true"
        android:src="@drawable/ic_baseline_check_24"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>-->
 */

// Assignments Fragment

    /*<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_behavior="@string/appbar_scrolling_view_behavior"
        tools:showIn="@layout/app_bar_main">

<androidx.viewpager2.widget.ViewPager2
        android:id="@+id/viewPager"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintBottom_toTopOf="@+id/tabLayout"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

<com.google.android.material.tabs.TabLayout
        android:id="@+id/tabLayout"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:tabTextAppearance="@style/TextAppearance.AppCompat.Small">

<com.google.android.material.tabs.TabItem
        android:id="@+id/upcomingTab"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:icon="@drawable/ic_baseline_list_24"
        android:text="@string/tabs_upcoming" />

<com.google.android.material.tabs.TabItem
        android:id="@+id/coursesTab"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:icon="@drawable/ic_baseline_list_alt_24"
        android:text="@string/tabs_courses" />

<com.google.android.material.tabs.TabItem
        android:id="@+id/completedTab"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:icon="@drawable/ic_baseline_date_range_24"
        android:text="@string/tabs_completed" />
</com.google.android.material.tabs.TabLayout>


<com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/fab"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|end"
        android:layout_margin="@dimen/fab_margin"
        android:layout_marginEnd="16dp"
        android:layout_marginBottom="16dp"
        android:contentDescription="@string/app_name"
        app:layout_constraintBottom_toTopOf="@+id/tabLayout"
        app:layout_constraintEnd_toEndOf="parent"
        app:srcCompat="@drawable/ic_baseline_add_24" />

</androidx.constraintlayout.widget.ConstraintLayout>*/

/*<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:backgroundTintMode="screen"
    app:layout_behavior="@string/appbar_scrolling_view_behavior"
    tools:showIn="@layout/app_bar_main">


    <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/fab"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|end"
        android:layout_margin="@dimen/fab_margin"
        android:layout_marginEnd="16dp"
        android:layout_marginBottom="16dp"
        android:contentDescription="@string/app_name"
        app:layout_constraintBottom_toTopOf="@+id/bottom_nav_view"
        app:layout_constraintEnd_toEndOf="parent"
        app:srcCompat="@drawable/ic_baseline_add_24" />

    <com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/bottom_nav_view"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:background="?android:attr/colorBackground"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:menu="@menu/bottom_nav_menu" />

    <androidx.fragment.app.FragmentContainerView
        android:id="@+id/fragmentContainerView"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintBottom_toTopOf="@+id/bottom_nav_view"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>*/
