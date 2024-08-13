package com.dicoding.habitapp.ui.list

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.dicoding.habitapp.ui.add.AddHabitActivity
import org.junit.Before
import org.junit.After
import org.junit.Test
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import com.dicoding.habitapp.R


//TODO 16 : Write UI test to validate when user tap Add Habit (+), the AddHabitActivity displayed
@RunWith(AndroidJUnit4ClassRunner::class)
class HabitActivityTest {
    @Before
    fun setUp() {
        ActivityScenario.launch(HabitListActivity::class.java)
        Intents.init()
    }

    @After
    fun destroy() {
        Intents.release()
    }

    @Test
    fun showAddHabitActivity() {
        onView(withId(R.id.fab)).perform(click())
        intended(hasComponent(AddHabitActivity::class.java.name))
    }
}