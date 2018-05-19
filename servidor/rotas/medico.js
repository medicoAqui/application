var express = require('express');
var medicoRouter =  express.Router();
var Medico = require('../modelos/userMedico.js');

medicoRouter.use(function(req, res, next) {

    // log each request to the console
    console.log(req.method, req.url);

    // continue doing what we were doing and go to the route
    next(); 
});

// define the home page route
medicoRouter.get('/', function(req, res) {
  res.send('pega dados do medico');
});



medicoRouter.post('/add', function(req,res){
	var novoMedico = new Medico(req.body);

	novoMedico.save(function(err, data) {
		console.log(novoMedico);

		console.log(data);

		if (err) {
			res.status(400).json(err);
		} else {
			res.status(201).json(data);
		}
	});

});

medicoRouter.put('/:id', function(red,res){

});

medicoRouter.delete('/:id', function(req,res){
	var idUsuario = { _id: req.params.id };

	Medico.remove(idUsuario, function(err, data) {
		if (err) {
			res.status(400).json(err);
		} else {
			res.json(data);
		}
	});

});

module.exports = medicoRouter;