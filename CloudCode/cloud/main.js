Parse.Cloud.define("registerUser", function(request, response) {
	var query = new Parse.Query("Users");
	
	query.equalTo("username", request.params.username);
	
	query.find({
		success: function(result) {
			if (result.length == 0) {
				var UserClass = Parse.Object.extend("Users");
				var user = new UserClass();
			
				user.set("username", request.params.username);
				user.set("password", request.params.password);
				user.set("telephone", request.params.telephone);
				user.set("rating", []);
				user.save();
		
				response.success("User registered successfully!");
			}
			else
				response.error("Username already taken!");
		},

		error: function(error) {
			response.error("Error!");
		}
	});
});
	
Parse.Cloud.define("getUserInfo", function(request, response) {
	var query = new Parse.Query("Users");
	
	query.equalTo("username", request.params.username);
	
	query.first({
		success: function(result) {
			response.success(result);
		},

		error: function(error) {
			response.error("Error!");
		}
	});
});

Parse.Cloud.define("addPool", function(request, response) {
	var query = new Parse.Query("Pools");
	
	query.equalTo("driver", request.params.driver);
	query.equalTo("date", request.params.date);
	
	query.find({
		success: function(result) {
			if (result.length == 0) {
				var PoolClass = Parse.Object.extend("Pools");
				var pool = new PoolClass();
			
				pool.set("driver", request.params.driver);
				pool.set("passengers", []);
				pool.set("source", request.params.source);
				pool.set("destination", request.params.destination);
				pool.set("date", request.params.date);
				pool.set("seats", request.params.seats);
				pool.set("weekly", request.params.weekly);
				pool.save();
			
				response.success("Pool created successfully!");
			}
			else
				response.error("Pool already exists!");
		},

		error: function(error) {
			response.error("Error!");
		}
	});
});

Parse.Cloud.define("updateProfile", function(request, response) {
	var query = new Parse.Query("Users");

	query.equalTo("username", request.params.username);
	
	query.first({
		success: function(result) {
			result.set("name", request.params.name);
			result.set("telephone", request.params.telephone);
			result.set("smoking", request.params.smoking);
			result.set("music", request.params.music);
			result.set("car", request.params.car);
			result.save();
			
			response.success("Profile updated successfully!");
		},

		error: function(error) {
			response.error("User not found!");
		}
	});
});

Parse.Cloud.define("getMyPools", function(request, response) {
	var query = new Parse.Query("Pools");
	
	if (request.params.userType == 0)
		query.equalTo("driver", request.params.username);
	else 
		query.equalTo("passengers", request.params.username);
		
	query.find({
		success: function(result) {
			if (result.length != 0)
				response.success(result);
			else
				response.error("No pools to show!");
		},

		error: function(error) {
			response.error("Error!");
		}
	});
});

Parse.Cloud.define("viewPool", function(request, response) {
	var query = new Parse.Query("Pools");

	query.equalTo("objectId", request.params.id);
	
	query.find({
		success: function(result) {
			if (result.length != 0)
				response.success(result);
			else
				response.error("Pool not found!");
		},

		error: function(error) {
			response.error("Error!");
		}
	});
});

Parse.Cloud.define("getPhone", function(request, response) {
	var query = new Parse.Query("Users");

	query.equalTo("username", request.params.username);
	
	query.find({
		success: function(result) {
			if (result.length != 0) {
				response.success(result[0].get("telephone"));
			}
			else
				response.error("User not found!");
		},

		error: function(error) {
			response.error("Error!");
		}
	});
});

Parse.Cloud.define("rateDriver", function(request, response) {
	var query = new Parse.Query("Users");

	query.equalTo("username", request.params.username);
	
	query.first({
		success: function(result) {
			if (result.length != 0) {
				result.add("rating", request.params.rating);
				result.save();
				
				response.success("Driver rated successfully!");
			}
			else
				response.error("Driver not found!");
		},

		error: function(error) {
			response.error("Error!");
		}
	});
});

Parse.Cloud.define("getRating", function(request, response) {
	var query = new Parse.Query("Users");

	query.equalTo("username", request.params.username);
	
	query.first({
		success: function(result) {
			if (result.length != 0) {
				var sum = 0.0;
				var nr_ratings = result.get("rating");
				
				for (var i = 0; i < nr_ratings.length; i++)
					sum += nr_ratings[i];
				
				if (nr_ratings.length != 0)
					response.success((sum / nr_ratings.length).toFixed(2));
				else
					response.success("-");
			}
			else
				response.error("Driver not found!");
		},

		error: function(error) {
			response.error("Error!");
		}
	});
});

Parse.Cloud.define("matchingPools", function(request, response) {
	var query = new Parse.Query("Pools");
	
	query.withinKilometers("source", request.params.source, request.params.walking_distance);
	query.equalTo("date", request.params.date);
	
	query.find({
		success: function(result) {
			if (result.length != 0) {
				response.success(result);
			}
			else
				response.error("There are no pools!");
		},

		error: function(error) {
			response.error("Error!");
		}
	});
});

Parse.Cloud.define("joinPool", function(request, response) {
	var query = new Parse.Query("Pools");

	query.equalTo("objectId", request.params.id);
	query.notContainedIn("passengers", request.params.passenger);
	query.greaterThan("seats", 0);
	
	query.first({
		success: function(result) {
			result.add("passengers", request.params.passenger);
			result.set("seats", result.get("seats")-1);
			result.save();
		
			response.success("Joined successfully!");
		},

		error: function(error) {
			response.error("Passenger already joined!");
		}
	});
});