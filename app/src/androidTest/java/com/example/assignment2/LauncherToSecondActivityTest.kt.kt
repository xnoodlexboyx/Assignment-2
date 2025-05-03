package com.example.assignment2

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

private const val APP_PACKAGE = "com.example.assignment2"
private const val LAUNCH_TIMEOUT = 5000L
private val CHALLENGES = listOf(
    "1. Battery & Resource Optimization",
    "2. Supporting Various Screen Sizes",
    "3. Security & Data Privacy",
    "4. Network Connectivity & Performance",
    "5. Lifecycle & Background Task Management"
)

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)  // UI Automator requires API 18+++++++++++++++++
class LauncherToSecondActivityTest {

    private lateinit var device: UiDevice

    @Before
    fun startFromHomeScreen() {
        // 1) Initialize
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        // 2) Go to home
        device.pressHome()
        val launcherPackage = device.launcherPackageName
        assertNotNull("Cannot find launcher package", launcherPackage)
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT)

        // 3) Find me app icon (by its label or content-desc) and click it

        val appIcon: UiObject2? =
            device.wait(Until.findObject(By.desc("Assignment2")), LAUNCH_TIMEOUT)
                ?: device.wait(Until.findObject(By.text("Assignment2")), LAUNCH_TIMEOUT)

        assertNotNull("App icon not found on home screen", appIcon)
        // click and wait for me package to appear
        appIcon!!.clickAndWait(
            Until.hasObject(By.pkg(APP_PACKAGE).depth(0)),
            LAUNCH_TIMEOUT
        )
    }

    @Test
    fun testExplicitIntentShowsATLeastOneChallenge() {
        // 4) Click the “Start Activity Explicitly” button
        val explicitBtn = device.wait(
            Until.findObject(By.text("Start Activity Explicitly")),
            2000L
        )
        assertNotNull("Explicit intent button not found", explicitBtn)
        explicitBtn!!.click()

        // 5) Wait for SecondActivity’s title
        val loaded = device.wait(
            Until.hasObject(By.text("Mobile Software Engineering Challenges:")),
            2000L
        )
        assertTrue("SecondActivity did not load", loaded)

        // 6) Assert at least one challenge line is visible
        val foundAny = CHALLENGES.any { text ->
            device.hasObject(By.text(text))
        }
        assertTrue("No challenge text found on SecondActivity", foundAny)
    }
}

private fun UiObject2.clickAndWait(
    condition: SearchCondition<Boolean>,
    lng: Long
) {
}
