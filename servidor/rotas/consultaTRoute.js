var express = require('express');
var consultaTRouter =  express.Router();

var ConsultaT = require('../modelos/consultaT');
var Consultorio = require('../modelos/consultorio');
var Medico = require('../modelos/userMedico');
var Cliente = require('../modelos/userCliente');


consultaTRouter.use(function(req, res, next) {

    // log each request to the console
    console.log(req.method, req.url);

    // continue doing what we were doing and go to the route
    next();
});

// define the home page route
consultaTRouter.get('/consultas', function(req, res) {
  var registros = ConsultaT.find({});

  registros.exec(function(err,data){
  	if(err){
  		res.sendStatus(500);
  	}else{
  		res.json(data);
  	}
  });
});

consultaTRouter.get('/:id',function(res,req){
	var consulta = ConsultaT.find({_id: req.param.id});

	consulta.exec(function(err,data){
		if(err) {
            res.sendStatus(400).json('ConsultaT nao encontrada no sistema');
        }else{
			res.json(data);
		}
	});
});
// Consulta nao funcionat1
consultaTRouter.post('/consultasByDate',function(res,req){
	var consulta = ConsultaT.find({dataConsulta: { $lte: new Date(req.params.dataConsulta) }});

	consulta.exec(function(err,data1){
		if(err) {
            res.sendStatus(400).json('ConsultaT nao encontrada no sistema');
        }else{
			res.json(data1);
		}
	});
});


consultaTRouter.post('/add', function(req,res){

    Cliente.findOne({cpf: req.body.cpf }, function(err,data0){

        if(data0 == undefined || err){
          console.log("_______________Nao foi encontrado o cliente para cadastro de consulta_________________");
          res.status(400).json("Cliente nao encontrado");
        }
        else{

          Medico.findOne({crm: req.body.crm }, function(err,data1){

              if(data1 == undefined || err){
                console.log("_______________Nao foi encontrado o Medico para cadastro de consulta_________________");
                res.status(400).json("Medico nao encontrado");
              }
              else{
                Consultorio.findOne({idConsultorio: req.body.idConsultorio }, function(err,data2){

                    if(data2 == undefined || err){
                      console.log("_______________Nao foi encontrado o Consultorio para cadastro de consulta_________________");
                      res.status(400).json("Consultorio nao encontrado");
                    }
                    else{
                      var cliente;
                      var medico;
                      var consultorio;
                      cliente = data0;
                      medico = data1;
                      consultorio = data2;
                      console.log("_______________ INSERINDO CONSULTA_________________");
                      var novaConsultaT = new ConsultaT(req.body);
                      novaConsultaT.cliente = cliente;
                      novaConsultaT.medico = medico;
                      novaConsultaT.consultorio = consultorio;
                      novaConsultaT.save(function(err, data3) {

                          if (err) {
                              res.status(400).json(err);
                          } else {
                              res.status(201).json(data3);
                          }
                      });
                      console.log("_______________Consultorio REGISTRADO_________________");
                    }
                });
              }
          });
        }
    });
});


module.exports = consultaTRouter;
