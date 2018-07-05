var express = require('express');
var consultorioRouter =  express.Router();

var Consultorio = require('../modelos/consultorio');
var Endereco = require('../modelos/endereco');


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

consultorioRouter.get('/:id',function(res,req){
	var consultorio = Consultorio.find({_id: req.param.id});

	consultorio.exec(function(err,data){
		if(err) {
            res.sendStatus(400).json('Consutolrio nao encontrado no sistema');
        }else{
			res.json(data);
		}
	});
});

consultorioRouter.post('/consultorioByNome',function(res,req){
	var consultorio = Consultorio.find({nomeConsultorio: req.param.nomeConsultorio});

	consultorio.exec(function(err,data){
		if(err) {
            res.sendStatus(400).json('Consutolrio nao encontrado no sistema');
        }else{
			res.json(data);
		}
	});
});


consultorioRouter.post('/add', function(req,res){

    var endereco;

    Endereco.findOne({idEndereco: req.body.idEndereco }, function(err,data){

        if(data == undefined || err){
          console.log("_______________FALHA AO CADASTRAR consultorio (Endereco NAO ENCONTRADO)_________________");
          res.status(400).json("Endereco nao encontrado");
        }
        else{
          endereco = data;
          console.log(endereco);
          console.log("_______________ INSERINDO CONSULTORIO_________________");
          var novoConsultorio = new Consultorio(req.body);
          novoConsultorio.endereco = endereco;
          novoConsultorio.save(function(err, data) {

              if (err) {
                  res.status(400).json(err);
              } else {
                  res.status(201).json(data);
              }
          });
          console.log("_______________Consultorio REGISTRADO_________________");
        }
    });
});


consultorioRouter.post('/enderecoByIdConsultorio',function(req,res){

  var endereco;

  Consultorio.findOne({idConsultorio: req.body.idConsultorio }, function(err,data1){
    console.log(data1);

    if(data == undefined || err){
        res.status(400).json(err);
    }
    else{

      var endereco = Endereco.find({idEndereco: data1.endereco.idEndereco});

      endereco.exec(function(err,data2){
        if(err) {
                res.sendStatus(400).json('Endereco nao encontrado no sistema');
            }else{
          res.json(data2);
        }
      });
    }
  });
});

module.exports = consultorioRouter;
