# EagleScout
Eagle Imperium's new scouting app stuff
This will eventually contain an Android app for data entry, a batch script to pull the data from phones over USB, and a java app to combine the individual data sheets into a database.  Right now, there's only the java part.

The finished system will work like this:  Six scouts are in the stands, each with an Android device with EagleScout installed.  They record data using the app, each for one robot per match.  The app appends data to a .xls file.  Every time the strategy team wants data, all of the scouts  plug their phones into cables that go into a USB hub.  The head scout runs the script (which includes the java app) to pull the data and combine it into one big sheet.  Analysis can then be done from Excel.
