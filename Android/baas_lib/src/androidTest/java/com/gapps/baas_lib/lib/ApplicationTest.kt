package com.gapps.baas_lib.lib

import android.app.Application
import android.test.ApplicationTestCase
import android.test.suitebuilder.annotation.SmallTest
import android.util.Log

/**
 * [Testing Fundamentals](http://d.android.com/tools/testing/testing_android.html)
 */
public class ApplicationTest : ApplicationTestCase<Application>(javaClass<Application>()) {
	@SmallTest
	fun test1 () {
		val myBackendObject = MyBackendObject()
		myBackendObject.stringProperty = "testValue"
		Log.i("myBackendObject", "myBackendObject: ${myBackendObject.stringProperty}")
		Log.i("myBackendObject", "crashes on art: ${MyBackendObject::stringProperty}")
		Log.i("myBackendObject", "crashes on art name: ${MyBackendObject::stringProperty.name}")
	}
}