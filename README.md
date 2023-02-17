# App1-Lifecycle-Management
CS4530


Create a simple Android app to accomplish the following:

1. Accept the user's first, middle, and last names in separate EditTexts.

2. Ask the user to take a profile picture using the phone camera and show a thumbnail on the app.

3. When the user hits a submit button, take them to a second page. This page says "First name last name is logged in!", where "First name" and "last name" are the names entered on the previous page.

Most importantly, each page of the app (an Activity) should have its lifecycle managed. Strategically use OnSaveInstanceState to back up UI details into a bundle, and restore backups in either OnCreate or OnRestoreInstanceState. We will test your app by enabling auto-rotate, but will also test pausing and resuming the app. Do all your testing for the Pixel 6 on API 33.

When you're done, submit a github link to your code in a .txt file. Also submit a .apk file (a runnable version of your app that can be created by dragging and dropping into the emulator or a real phone). You'll need to figure out that step on your own.
