# Activity/Fragment

* ```fun listenTo{Element}{Type}``` - function with different type of listeners (e.g. types: Click,
  Hold, Input)
* ```fun setup{Element}``` - function with setuping different parts of the UI
* ```fun request{ViewEvent}``` - function-wrapper for sending events to ViewModel

# Repositories

Entities scope - **domain**

It is also available for all functions to add *suffixes* via **by** keyword. E.g. `getCachedCatByAge`

* `request{Action}` - function with network IO
* Working with cache
    * `cache{Entity}` - function for caching entities
    * `getCached{Entity}` - function for getting cache
    * `removeAllCache` or `removeCached{Entity}` - function for removing cache at all ot partially
    * `is{Entity}ExistsInCache` - function for checking is entity present in cache
    * `updateCached{Entity}By{Modificator}` - function for updating entities in cache
 

# Cache

Entities scope - **cache**

It is also available for all functions to add *suffixes* via **by** keyword. E.g. `getCatByAge`

* `get{Entity}` - function for returning some entity
* `update{Entity}By{Modificator}` - function for updating entities in cache
* `is{Entity}Exists` - function for checking is entity present in cache
* `insert{Entity}` - function for caching entities
