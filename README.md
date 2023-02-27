# Alien News
News app that shows the latest news from the galaxy from the alien standpoint. I created this app to test android features from the book "Android Programming: The big nerd ranch". Also I wanted to make this app with the out jetpack compose components so it is based on xml and fragments.

The app needs ktor development server https://github.com/Risto12/development_server to show the latest news from the galaxy. It uses token authentication where the username can be anything and password is todo.

### Features
The credentials for fetching the token can be taken from your contact list. I used this as a database for fun. The app will ask permissions for contact list for this. The username will be contacts name and pin code will be the phone-number. If the connection to server is successful the credentials will be stored to Protobuf.

### To run
This should be run on the emulator because the ip of the development server is hardcoded to localhost(10.0.2.2:8080 - localhost of the computer)

### Running the app
1. Turn on development server
2. Turn on the alien news app
3. Tap to the connection icon in the top right. This will pop up a dialog to enter the credentials to fetch the token. You can use contact list to fetch the credentials or give them manually. If the connection is successful the dialog will close and the icon will turn green. After a while list of news channels will appear.
4. Now you can choose the channel and read the latest news and watch the pictures attached to them if there is any.

### Development
This was a test project and I'm not actively developing this any more. Sometimes I might add some optimisation.


![alt text](https://github.com/Risto12/Alien_news/blob/main/channels.png "Alien channels")

![alt text](https://github.com/Risto12/Alien_news/blob/main/channel.png "Alien channel")

![alt text](https://github.com/Risto12/Alien_news/blob/main/images.png "Images")

![alt text](https://github.com/Risto12/Alien_news/blob/main/nocon.png "No connection to server")
