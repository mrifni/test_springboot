# How to run application
``
sh start.sh
``

above command will start the postgresql db and run the unit tests and start the spring boot application. 

###To create a geofence
```
POST
http://localhost:8080/v1/geofences
{
    "lat":5.133434,
    "lng":5.34341,
    "radius": 12
}
```

###To create an advertising
```
POST
http://localhost:8080/v1/geofences/$someid/advertising
{
    "href":"https://google.com"
}
```

###To update an advertising
```
PUT
http://localhost:8080/v1/geofences/$someid/advertising/$someId
{
    "href":"https://yahoo.com"
}
```

###To delete an advertising
```
DELETE
http://localhost:8080/v1/geofences/$someid/advertising/$someId
```

###To get all advertisings
```
GET
http://localhost:8080/v1/geofences/$someid/advertising
```
