# giphy

Project using GIPHY (the largest GIF and Sticker library in the world) API (see https://developers.giphy.com/docs/)

To make the project compile, you will need to set your API key to it. To do that just add giphyApiKey=YOUR-OWN-KEY in your global gradle.properties (file in USER_HOME/.gradle) to make the project compile

Features

display a random gif (image and title) with a refresh button to change the gif
display a list of gifs when a keyword is tap on the search
display the gif on fullscreen on click
dark mode

Evolution ideas:

Finish the use of search pagination
Use more Giphy API to display more type of images, integrate Stickers
Use Giphy SDK to use a GiphyView instead ImageView (not done in this version because the ghiphy dependency was not worked)

Add a CI
Use Coroutine instead of RxJava
Save gif data on database
Manage gifs vy category and by users
