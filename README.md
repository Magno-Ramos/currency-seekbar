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
```

### Customize ?

>Create your custom style

```xml
    <!--custom theme-->
    <style name="CurrencySeekBarTheme.Custom">
        <item name="colorPrimary">#222222</item>
        <item name="cs_seek_bar_style">@style/CsSeekBarStyle.Custom</item>
        <item name="cs_label_min_style">@style/CsMinLabelStyle.Custom</item>
        <item name="cs_label_max_style">@style/CsMaxLabelStyle.Custom</item>
    </style>

    <!--custom seek bar style-->
    <style name="CsSeekBarStyle.Custom" parent="CsSeekBarStyle">
        <item name="android:layout_marginStart">16dp</item>
        <item name="android:layout_marginEnd">16dp</item>
    </style>

    <!--custom min label style-->
    <style name="CsMinLabelStyle.Custom" parent="CsLabelStyle">
        <item name="android:textColor">#43A047</item>
    </style>

    <!--custom max label style-->
    <style name="CsMaxLabelStyle.Custom" parent="CsLabelStyle">
        <item name="android:textColor">#e53935</item>
    </style>
```

    Copyright [2020] [Magno Ramos]
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
      http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
