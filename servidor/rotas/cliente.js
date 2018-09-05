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

usuarioRouter.post('/clientePor_id', function(req,res){

    Usuario.findOne({_id: req.body._id}, function(err,data){
        console.log(data)
        if(data == null){
            res.status(400).json('Cliente não encontrado.');
        }else{
            res.send(data);
        }
    });

});


usuarioRouter.put('/:id',function(req,res){
    var corpo = req.body;
    console.log(corpo);
    Usuario.findByIdAndUpdate(req.params.id,corpo,{new: true}, function(err,data){
        if(err){
             res.status(500).send(err);
        }else{
             res.send(data)
        }
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
