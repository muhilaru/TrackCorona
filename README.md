# TrackCorona

Author: Muhil Arumugam

---

TrackCorona is an Android application written in Kotlin that allows users to view visualizations of the latest COVID-19 statistics. All statistics were derived from the Johns Hopkins University, Center of Systems Science and Engineering [data set](https://github.com/CSSEGISandData/COVID-19). The main visualization includes a Google Maps interface in which users can scroll through a world map and zoom in on specific countries/locations to view their COVID-19 statistics. Each county/province/territory is represented with a marker with the name and the number of confirmed COVID-19 cases in that area. When the Map is zoomed out, the markers converge together to form [Marker Clusters](https://developers.google.com/maps/documentation/android-sdk/utility/marker-clustering) to increase the clarity and readability of the map. The Clusters will then diverge into markers as the user zooms into the map. The Google Map is also layered with a [heat map](https://developers.google.com/maps/documentation/android-sdk/utility/heatmap) of the United States that uses weighted LatLng objects to represent statistics from each county and shows the density of confirmed COVID-19 cases across the US. In addition to the Map, there are two charts created using [MPAndroidCharts](https://github.com/PhilJay/MPAndroidChart) to represent the top 10 countries and top 10 US counties with the highest number of confirmed COVID-19 cases.

# Images

!{}(images/main_menu.PNG)
!{}(images/us_map.PNG)
!{}(images/map_marker.PNG)
!{}(images/global_chart.PNG)

