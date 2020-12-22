# BearMaps

BearMaps is a Google Maps inspired clone for the vicinity of the UC Berkeley campus. It is capable of performing most features you would expect of a mapping application. The "smart" features of the application include map dragging/zooming, map rasterization, A* search algorithm between two points, and an auto-complete search feature.

Feature | Description
------- | -------
[RasterAPIHandler](https://github.com/genielee55/BearMaps/blob/main/bearmaps/proj2d/server/handler/impl/RasterAPIHandler.java) | Renders map images given a user's requested area and level of zoom.
[AugmentedStreetMapGraph](https://github.com/genielee55/BearMaps/blob/main/bearmaps/proj2d/AugmentedStreetMapGraph.java) | Graph representation of the contents of Berkeley Open Street Map data.
[AStarSolver](https://github.com/genielee55/BearMaps/blob/main/bearmaps/proj2c/AStarSolver.java) | The A* search algorithm to find the shortest path between two points in Berkeley.
[Trie](https://github.com/genielee55/BearMaps/blob/main/bearmaps/proj2d/Trie.java) | A TrieSet backs the autocomplete search feature, matching a prefix to valid location names in Θ(k) time, where k in the number of words sharing the prefix.
[KDTree](https://github.com/genielee55/BearMaps/blob/main/bearmaps/proj2ab/KDTree.java) | A K-Dimensional Tree backs the A* search algorithm, allowing efficient nearest neighbor lookup averaging O(log(n)) time.
[ArrayHeapMinPQ](https://github.com/genielee55/BearMaps/blob/main/bearmaps/proj2ab/ArrayHeapMinPQ.java) | A min-heap priority queue backs the A* search algorithm.
