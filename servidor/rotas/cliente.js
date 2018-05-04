var express = require('express');
var usuarioRouter =  express.Router();
var Usuario = require('../modelos/userCliente.js');

usuarioRouter.use(function(req, res, next) {

    // log each request to the console
    console.log(req.method, req.url);

    // continue doing what we were doing and go to the route
    next(); 
});

// define the home page route
usuarioRouter.get('/', function(req, res) {
  res.send('pega dados do cliente');
});



usuarioRouter.post('', function(req,res){
	var novoUsuario = new Usuario(req.body);

	novoUsuario.save(function(err, data) {

		console.log(data);

		if (err) {
			res.status(400).json(err);
		} else {
			res.status(201).json(data);
		}
	});

});

usuarioRouter.put('/:id', function(red,res){

});

usuarioRouter.delete('/:id', function(req,res){
	var idUsuario = { _id: req.params.id };

	Usuario.remove(idUsuario, function(err, data) {
		if (err) {
			res.status(400).json(err);
		} else {
			res.json(data);
		}
	});

});

module.exports = usuarioRouter;