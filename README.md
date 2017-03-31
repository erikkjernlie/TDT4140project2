# UNIBOT

Our project consist of developing a native application for the Android Platform. The app will assist potential applicants for civ.ing-studies at NTNU Gløshaugen with finding relevant information and recommendations based on the user's interests, using an AI-chatbot api. The goal is for the user-bot-communication to remain as natural as possible. As an example, one might ask the bot "Hello! I would like to learn about some studies that focus on computer technology.", and the bot will reply with some information and additional links for appropriate studies.

In the initial release of the app, we have restricted the number of supported studies to include the following:
- Engineering and ICT
- Computer Science
- Informatics
- Industrial Economics and Technology Management with specialization in Computer- and Communication Technology

Implementing the software for a limited number of studies whom have many simularities allow us to develop the app functionality further without having to spend a large amount of time gathering information.

In order to run the program on a computer during the development phase, one would need to install a program called Android Studio and open the project from there. The project is stored here on GITHUB. The "Master"-branch consists of up-to-date functional code, while development is done in separate branches with appropriate branch names. At some point before the 27th of April, the app will be made available for download on Google Play.

Unit- and GUI tests are found in the following folders:
- TDT4140project2\app\src\test
- TDT4140project2\app\src\androidTest

GUI-tests and API.AI require an Android emulator to run. This is best done in Android Studio by simply right-clicking the folder and selecting "Run tests in folder". Other software programs that allow the use of such emulators can also be used, but is not recommended. JUnit-tests can be ran with any software that supports Java files, as long as they are able to import the appropriate methods to be tested.

The native app for Android with its functions are made using:
- Java for programming
- XML for sharing
- Firebase for database storage
- API.AI with google voice api for AI-behaviour

and is as of yet only published as UNIBOT version 1.0.0.

The app is made by the following students of the Engineering and ICT study at NTNU Gløshaugen:
- Herman Wika Horn
- Jonas Åsnes Sagild
- Erik Kjernlie
- Jørgen Mortensen

with contributions from
- Pekka Abrahamsson, lecturer for the TDT4140 course
- Kari Eline Strandjord, student assistant for our group
