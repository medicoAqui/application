var express = require('express');
var usuarioRouter =  express.Router();
var Usuario = require('../modelos/userCliente.js');
var Consulta = require('../modelos/consulta');

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

usuarioRouter.post('/me',function(req,res){
  
    Usuario.findOne({cpf: req.body.cpf}, function(err,data){
        console.log(data)
        if(err){
            res.status(500).send(err);
        }else{
            res.send(data);
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


usuarioRouter.put('/:cpf',function(req,res){
    //var idCliente = {_id: req.params.id};
    var corpo = req.body;
    console.log(corpo);
console.log(req.params.cpf);
    Usuario.findByIdAndUpdate(req.params.cpf,corpo,{new: true}, function(err,data){
        if(err){
             res.status(500).send(err);
        }else{
             res.send(data)
        }
    });
});


usuarioRouter.put('/consulta/:cpf', function(req,res){
    var corpo = req.body;
    console.log(corpo);

    Consulta.findByIdAndUpdate(req.params.cpf,corpo,{new: true}, function(err,data){
        console.log(data);
        if(err){
            res.status(500).send(err);
        }else{
            res.send(data)
        }
    });

});


usuarioRouter.delete('/:cpf', function(req,res){
	var idUsuario = { cpf: req.params.cpf };

	Usuario.remove(idUsuario, function(err, data) {
		if (err) {
			res.status(400).json(err);
		} else {
			res.json(data);
		}
	});

});

usuarioRouter.post('/clientePorEmail', function(req,res){
    
    Usuario.findOne({email: req.body.email}, function(err,data){
        console.log(data)
        if(data == null){
            res.status(400).json('Email não encontrado.');
        }else{
            res.send(data);
        }
    });

});

module.exports = usuarioRouter;