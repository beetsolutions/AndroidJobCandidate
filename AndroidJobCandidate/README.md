
Methods used: In this app, I used MVVM/Dagger2/Coroutine/Livedata with Kotlin. 

Function inclued: 
1. This app can show up a main scrolling list with data from 2 APIs;
2. After clicking on any item, a detailed page chould show up with related latest 3 comments.
3. It also checks the internet connection, show loading processbar, show timeout dialog.

Unsure point:
The given code from you shows that the logic between posts and photos is just: "Random.nextInt(postAndPhotoList.photos.size - 1)", which means all posts show all photos randomly from all 5000 photos, which is not logical for me.But as test, I did what it asked for.

Problem found:
There is problem with photoUrl, I got right URL, used glide in right way(also tested with picaso), but photo can't show up. I would be very happy to see the solution.

Further improvment:
If I have more time I will figure out how to use paging with coroutine to make data retrieving more efficiently.






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


