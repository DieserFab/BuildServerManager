# BuildServerManager

The plugin BuildServerManager is a necessity for modern building server.

It has many different utilities to manage the needs of an modern building server.

## Installation

To install the plugin follow these steps:

```bash
1. Go to the release section and download the .jar file
2. Put the plugin into your /plugins folder 
3. Now stop and start the server again (you could just reload but i wouldnt suggest that anyway)
4. (Optional) the plugin will create a few configuration files at /plugins/BuildServerManager
   you can now decide wether the plugin should load all created maps on server startup or just
   when you want to work on it by changing the setting within the config.yml file
```

## Requirements

Spigot Version: 1.8-1.15.2

Official Support just for 1.8,1.12,1.14,1.15

Java 8



## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## Developer API
```java
//first get a reference to the api
BSMAPI api = BSMAPI.getInstance();

//you can then access the different methods
api.addDomain(String name); - boolean

api.removeDomain(String name); - boolean

api.getDomain(String name); - Domain

api.getDomains(); - List<Domain>

api.addCategory(String domain,String name); - boolean

api.removeCategory(String domain,String name); - boolean

api.getCategory(String domain,String name); - Category

api.getCategories(String domain); - List<Category>

api.addMap(String domain,String category,String name,String type); - boolean

api.removeMap(String domain,String category,String name); - boolean

api.getMap(String domain,String category,String name); - Map

api.getMaps(String domain,String category); - List<Map>

api.getAllDeclaredMaps(); - List<String>

api.getMapsToClassify(); - List<String>

//some debug methods mainly for the author
api.getDisplayname(String path); - String

api.getType(String path); - String
```