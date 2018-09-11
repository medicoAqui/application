var express = require('express');
var consultorioRouter =  express.Router();

var Consultorio = require('../modelos/consultorio');
//var Endereco = require('../modelos/endereco');


consultorioRouter.use(function(req, res, next) {

    // log each request to the console
    console.log(req.method, req.url);

    // continue doing what we were doing and go to the route
    next();
});

// define the home page route
consultorioRouter.get('/consultorios', function(req, res) {
  var registros = Consultorio.find({});

  registros.exec(function(err,data){
  	if(err){
  		res.sendStatus(500);
  	}else{
  		res.json(data);
  	}
  });
});

consultorioRouter.get('/:id',function(req,res){
	var consultorio = Consultorio.find({_id: req.param.id});

	consultorio.exec(function(err,data){
		if(err) {
            res.sendStatus(400).json('Consutolrio nao encontrado no sistema');
        }else{
			res.json(data);
		}
	});
});



consultorioRouter.post('/consultorioByNome',function(req,res){
	var consultorio = Consultorio.find({nomeConsultorio: req.body.nomeConsultorio}).exec(function(err,data){
    if(err){
      res.send("nome de consultorio nao existe");
    }else{
      res.send(data);
    }
  });

	
});


consultorioRouter.post('/add', function(req,res){
  var novoConsultorio = new Consultorio(req.body);

  novoConsultorio.save(function(err, data) {
    if (err) {
      res.status(400).json(err);
    } else {
      res.status(201).json(data);
    }
  });

    
});


consultorioRouter.post('/enderecoByIdConsultorio',function(req,res){

   Consultorio.findOne({_id: req.body.idConsultorio }).exec(function(err,data1){
    if(err){
      res.sendStatus(400).send(err);
    }else{
      res.send(data1);
    }

   });

});

module.exports = consultorioRouter;
