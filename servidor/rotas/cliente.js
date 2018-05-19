var express = require('express');
var usuarioRouter =  express.Router();
var Usuario = require('../modelos/userCliente.js');

//var checarToken = require('../rotas/checarToken');


usuarioRouter.use(function(req, res, next) {

    // log each request to the console
    console.log(req.method, req.url);

    // continue doing what we were doing and go to the route
    next(); 
});

// define the home page route
usuarioRouter.get('/clientes', function(req, res) {
    var clientes = Usuario.find({});
    clientes.exec(function(err, data) {
        if (err) {
            res.sendStatus(500);
        } else {
            res.json(data);
        }
    });
});



usuarioRouter.post('/add', function(req,res){
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

usuarioRouter.put('/:id', /* checarToken, */ function (req, res) {
    Usuario.findByIdAndUpdate(req.params.id, req.body, {new: true}, function (err, user) {
        if (err) return res.status(500).send("There was a problem updating the user.");
        res.status(200).send(user);
    });
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