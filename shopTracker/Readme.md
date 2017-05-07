Asset Management Digital Challenge
===============================

Description-->This project contains the assignment of Asset Management System 

### Prerequisites

1)It is necessary to have gradle and jdk 1.8 configured on your workstation

2)Postman should be present to make Rest API calls.


### Modules
In the application I have used-:
	
	Postman as Rest Client
	Gradle to build application
	Spring boot
	Hibernate for database interaction
	H2 as in-memory database
	JUnit for test case writing


### How to run

a)To start application run

```
gradlew build && java -jar build/libs/shopTracker-0.1.0.jar
```

from within this directory.  

b)This will start building the application using gradle and initially it will run the test case.
After application is build, if you want to see the test results, go to

```
build\reports\tests\index.html
```

from within this directory. You will be able to see the results.

c)Once you've started the application, you will be able to make Rest api calls through Postman.

### Rest Api's 

The operations exposed by the REST API are:

1)shops (POST)
-->receives a JSON Shop as parameter
	
	Sample URL Request  -:  http://localhost:8080/shops
	Sample json input -:  {"shopName":"Al-Bek","shopAddress":{"number":"1","postCode":"560032"}}

a)If the shop is inserted first time
-->returns a String stating 	

	 data inserted successfully

b)If data with same shop name is already there
-->returns a JSON String containing the inserted shop and the previous shop with the same name (if any)

	Sample output -: previousVersion:{"shopName":"Al-Bek","shopAddress":{"number":"527","postCode":"560003"},"location":{"latitude":"12.9942789","longitude":"77.57152680000002"}},  newVersion:{"shopName":"Al-Bek","shopAddress":{"number":"1","postCode":"560032"},"location":{"latitude":"13.0223542","longitude":"77.5926841"}}

	Sample URL Request  -:  http://localhost:8080/shops

2)nearestShop (GET)
-->receives longitude and latitude as parameters
	
	Sample URL Request -:  http://localhost:8080/nearestShop?latitude=13.0265706&longitude=77.58980609999999

-->returns the nearest shop

	Sample Output -: {"shopName": "Families Super Market","shopAddress": {"number": "80","postCode": "560032"},"location":{"latitude": "13.0266406","longitude": "77.5934844"}}



## Authors

* **prasannakharche@gmail.com**
