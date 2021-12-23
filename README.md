# android-tmdb-app

**Language and Libraries**

An application is implemented using Kotlin as a programming language. The application is implemented
as a single activity application.

Used API for movies: TheMovieDB

Libraries used in the project:

- **Paging 3** for API data pagination management
- **Room** for favorite and custom movies cache
- **ViewModel** for communication between View and Model layers (As part of MVVM architecture)
- **LiveData** for implementing the observer pattern
- **Navigation component** for navigation and communication between fragments
- **Retrofit** for network requests
- **AndroidX Libraries** that are part of Android Jetpack
- **Dagger Hilt** for dependency injection
- **View binding** for interacting with views
- **Glide** for loading images

**About application**

The movie screen will show movies fetched from API. There is a options menu in the upper right corner which navigates
through various types of movie lists:

1. Popular, Rated, and Upcoming (List of movies provided by API),
2. Favourite movies (Api movies marked as favorite) and
3. Added/Custom movies.

For every type of movies list user can open details for that movie. When a user opens Added/Custom
movies screen in the bottom right corner button for adding new movies will be shown along with the list
of previously added movies. Custom movies contain only image, title, and rating, and all of these three
fields cannot be empty (user must load image from the gallery).

Also, if there is a problem while fetching a new page of movies from API, a message will be shown along
with the button to retry to fetch new data.

Screenshots

![Movie list screen](../master/screenshots/movie_list.png)

![Options menu](../master/screenshots/options_menu.png)

![Movie details screen with favorite button](../master/screenshots/details.png)

P.S. Provide you TMDB API key in gradle.properties file.