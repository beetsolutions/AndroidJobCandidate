# Android Job Candidate

### Task Description

Fix all of the TODOs and rewrite the project so it reflects your coding style and preferred way of displaying a list of items and a details page.

When clicking one of the items in the list, the details of that item should be shown.
When loading data from the Api, there should be a ProgressBar visible.
In the case of a connection timeout, there should be a fullscreen error message with a retry button.
Clicking the retry button should make a new request to the api.

Your solution should be something you would put into production.
This means that we expect that the app is stable and performs well in all possible use cases

*At the interview we expect you to walk us through the code and explain what you have done.*

### Task Completion release notes

* All TODOs have been fixed
* When you click an item in the list, a detail screen is shown with the details of the post showing 3 comments if availble
* There is a progress bar shown when loading data
* If there is an error from the REST endpoint or from connectivity an error screen is shown with a possibility to retry when conditions are favorable
* Post can also been saved locally given the possibility for offline capabilities
* MVVM with Data binding and Koin/Kotlin for DI has been used for development and architectural design (Clean Architecture)

