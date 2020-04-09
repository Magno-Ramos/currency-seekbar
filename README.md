[![](https://jitpack.io/v/magno-ramos/currency-seekbar.svg)](https://jitpack.io/#magno-ramos/currency-seekbar)

# Currency Seekbar

### Screenshot

!['ScreenShot'](https://raw.githubusercontent.com/Magno-Ramos/currency-seekbar/master/images/screenshot.png)

### Setup

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

````gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
````

Step 2. Add the dependency

	
````gradle
dependencies {
	implementation 'com.github.magno-ramos:currency-seekbar:version'
}
````


### Usage

```xml
<com.app.currency_seekbar.CurrencySeekBar
	android:id="@+id/cs_default"
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	android:layout_marginTop="8dp"
	android:theme="@style/CurrencySeekBarTheme"
	app:cs_max="100.43"
	app:cs_min="60.30"
	app:cs_progress="80.65" />
