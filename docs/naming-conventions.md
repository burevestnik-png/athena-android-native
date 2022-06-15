# Activity/Fragment

* ```fun listenTo{Element}{Type}``` - function with different type of listeners (e.g. types: Click,
  Hold, Input)
* ```fun setup{Element}``` - function with setuping different parts of the UI
* ```fun request{ViewEvent}``` - function-wrapper for sending events to ViewModel

# Repositories

Entities scope - **domain**

* `request{Action}` - function with network IO
* Working with cache
    * `cache{Entity}` - function for caching entities
    * `getCached{Entity}` - function for getting cache
    * `removeAllCache` or `removeCached{Entity}` - function for removing cache at all ot partially

# Cache

Entities scope - **cache**

* `get{Entity}` - function for returning some entity
* ``