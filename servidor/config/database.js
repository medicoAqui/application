var MongoCliente = require('mongodb').MongoCliente
	,assert = require('assert');



	// conectar com a url

var url = 'mongodb://localhost:27017/medicoAqui';

MongoCliente.connect(url, function(err,db){
	assert.equal(null, err);
	console.log("Connected correctly to server");

	db.close();

});
