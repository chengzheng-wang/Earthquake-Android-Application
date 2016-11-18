This is an android application to show latest earthquake information from the world. The data is fetched by the API from USGS:
https://www.usgs.gov/

The user could set the preference such as: whether the data is order by time or by the magnitude; what is the minimum magnitude to show and how much earth quake information the user wants to reach.

The user could also choose to searth the information from national wide or from user location: There is a EditPreference named radius which could be provided by user. If the user enter 0, the result will be national wide. If an integer more than 0 is entered, the app will show the earthquake information from the user's location(with the circle whose radius is provided by user) based on GPS of the device.

The app will show the magnitude, the address and the date of a certain earthquake. By clicking it, it will redirect to the USGS website for more information.
