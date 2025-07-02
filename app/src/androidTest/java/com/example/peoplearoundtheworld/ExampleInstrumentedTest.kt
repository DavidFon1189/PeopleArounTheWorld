package com.example.peoplearoundtheworld

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun addingItemsWorksCorrectly() {
        composeTestRule.activity.setContent {
            MyApp1()
        }
        //Se realizan este test para simular como agregar un usuario desde la vista de compouse
        composeTestRule.onNodeWithContentDescription("Add").performClick()//la etiqueta Label la obtenemos del boton Add en la vista
        composeTestRule.onNodeWithText("Name 0 LastName 0").assertExists()

        composeTestRule.onNodeWithContentDescription("Add").performClick()
        composeTestRule.onNodeWithText("Name 1 LastName 1").assertExists()
    }

    @Test
    fun removeItemsWorksCorrectly() {
        composeTestRule.activity.setContent {
            MyApp1()
        }
        //Se realizan este test para simular como agregar un usuario desde la vista de compouse
        composeTestRule.onNodeWithContentDescription("Add").performClick()//la etiqueta Label la obtenemos del boton Add en la vista
        composeTestRule.onNodeWithText("Name 0 LastName 0").assertExists()

        composeTestRule.onNodeWithContentDescription("Remove").performClick()
        composeTestRule.onNodeWithText("Name 0 LastName 0").assertDoesNotExist()
    }

    @Test
    fun loadingVisibleWhenAddItem() {
        composeTestRule.activity.setContent {
            MyApp(users = emptyList(), isLoading = true)
        }
        //Se realizan este test para simular como agregar un usuario desde la vista de compouse
        composeTestRule.onNodeWithContentDescription("Add").performClick()//la etiqueta Label la obtenemos del boton Add en la vista
        composeTestRule.onNodeWithTag("LoadingCard").assertExists()
    }
}