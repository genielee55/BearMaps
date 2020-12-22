# BearMaps
CS61B Bear Maps

BearMaps is a Google Maps inspired clone for the vicinity of the UC Berkeley campus. It is capable of performing most features you would expect of a mapping application. The "smart" features of the application include map dragging/zooming, map rasterization, A* search algorithm between two points, and an auto-complete search feature.

Feature | Description
------- | -------
[RasterAPIHandler]( ) | Renders map images given a user's requested area and level of zoom.
[AugmentedStreetMapGraph](https://github.com/genielee55/BearMaps/blob/main/bearmaps/proj2d/AugmentedStreetMapGraph.java) | Graph representation of the contents of Berkeley Open Street Map data.
[AStarSolver](https://github.com/kcheeeung/BearMaps/blob/master/bearmaps/hw4/AStarSolver.java) | The A* search algorithm to find the shortest path between two points in Berkeley.
[MyTrieSet](https://github.com/genielee55/BearMaps/blob/main/bearmaps/proj2d/MyTrieSet.java) | A TrieSet backs the autocomplete search feature, matching a prefix to valid location names in Θ(k) time, where k in the number of words sharing the prefix.
[KDTree]( ) | A K-Dimensional Tree backs the A* search algorithm, allowing efficient nearest neighbor lookup averaging O(log(n)) time.
[ArrayHeapMinPQ]( ) | A min-heap priority queue backs the A* search algorithm.
