var express = require('express');
var consultaRouter =  express.Router();

var Consulta = require('../modelos/consulta');
var Consultorio = require('../modelos/consultorio');
var Medico = require('../modelos/userMedico');
var Cliente = require('../modelos/userCliente');


consultaRouter.use(function(req, res, next) {

    // log each request to the console
    console.log(req.method, req.url);

    // continue doing what we were doing and go to the route
    next();
});

// define the home page route
consultaRouter.get('/consultas', function(req, res) {
  var registros = Consulta.find({});

  registros.exec(function(err,data){
  	if(err){
  		res.sendStatus(500);
  	}else{
  		res.json(data);
  	}
  });
});

consultaRouter.get('/:id',function(res,req){
	var consulta = Consulta.find({_id: req.param.id});

	consulta.exec(function(err,data){
		if(err) {
            res.sendStatus(400).json('Consulta nao encontrada no sistema');
        }else{
			res.json(data);
		}
	});
});

consultaRouter.post('/consultasByDateAndCrm',function(req,res){

  var medico = Medico.find({crm: req.body.crm});
  medico.exec(function(err,data){
      console.log(data)
      if(err){
          res.status(500).send(err);
      }else{
        var consulta = Consulta.find({dataConsulta: req.body.dataConsulta, medico: data});
        consulta.exec(function(err,data2){
            console.log(data2)
            if(err){
                res.status(500).send(err);
            }else{
               res.send(data2);
            }
        });
      }
  });

});

consultaRouter.post('/consultasByDateCrmAndCpfCliente',function(req,res){

  var medico = Medico.find({crm: req.body.crm});
  medico.exec(function(err,data){
      console.log(data)
      if(err){
          res.status(500).send(err);
      }else{

        var cliente = Cliente.find({cpf: req.body.cpf});
        cliente.exec(function(err,data1){
            console.log(data1)
            if(err){
                res.status(500).send(err);
            }else{
              var consulta = Consulta.find({dataConsulta: req.body.dataConsulta, medico: data, cliente: data1});
              consulta.exec(function(err,data2){
                  console.log(data2)
                  if(err){
                      res.status(500).send(err);
                  }else{
                     res.send(data2);
                  }
              });
            }
        });
      }
  });

});

consultaRouter.post('/consultasByDateAndCpfCliente',function(req,res){

  var cliente = Cliente.find({cpf: req.body.cpf});
  cliente.exec(function(err,data){
      console.log(data)
      if(err){
          res.status(500).send(err);
      }else{
        var consulta = Consulta.find({dataConsulta: req.body.dataConsulta, cliente: data});
        consulta.exec(function(err,data2){
            console.log(data2)
            if(err){
                res.status(500).send(err);
            }else{
               res.send(data2);
            }
        });
      }
  });

});

consultaRouter.post('/consultasByCpfClienteAndStatus',function(req,res){

  var cliente = Cliente.find({cpf: req.body.cpf});
  cliente.exec(function(err,data){
      console.log(data)
      if(err){
          res.status(500).send(err);
      }else{
        var consulta = Consulta.find({status: req.body.status, cliente: data});
        consulta.exec(function(err,data2){
            console.log(data2)
            if(err){
                res.status(500).send(err);
            }else{
               res.send(data2);
            }
        });
      }
  });

});

consultaRouter.post('/consultasByCpfCliente',function(req,res){

  var cliente = Cliente.find({cpf: req.body.cpf});
  cliente.exec(function(err,data){
      console.log(data)
      if(err){
          res.status(500).send(err);
      }else{
        var consulta = Consulta.find({cliente: data});
        consulta.exec(function(err,data2){
            console.log(data2)
            if(err){
                res.status(500).send(err);
            }else{
               res.send(data2);
            }
        });
      }
  });

});

consultaRouter.post('/consultasByDateCrmStatus',function(req,res){

  var medico = Medico.find({crm: req.body.crm});
  medico.exec(function(err,data){
      console.log(data)
      if(err){
          res.status(500).send(err);
      }else{
        var consulta = Consulta.find({dataConsulta: req.body.dataConsulta, status:req.body.status, medico: data});
        consulta.exec(function(err,data2){
            console.log(data2)
            if(err){
                res.status(500).send(err);
            }else{
               res.send(data2);
            }
        });
      }
  });

});

consultaRouter.get('/:id',function(res,req){
	var consulta = Consulta.find({_id: req.param.id});

	consulta.exec(function(err,data){
		if(err) {
            res.sendStatus(400).json('Consulta nao encontrada no sistema');
        }else{
			res.json(data);
		}
	});
});


/*

esse era o teu , ele estava quebrando
consultaRouter.post('/add', function(req,res){

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

                var cliente;
                var medico;
                cliente = data0;
                medico = data1;
                console.log("_______________ INSERINDO CONSULTA_________________");
                var novaConsulta = new Consulta(req.body);
                novaConsulta.cliente = cliente;
                novaConsulta.medico = medico;
                novaConsulta.save(function(err, data3) {

                    if (err) {
                        res.status(400).json(err);
                    } else {
                        res.status(201).json(data3);
                    }
                });
              }
          });
        }
    });
});

*/

consultaRouter.post('/add',function(req,res){

  Medico.findOne({crm: req.body.crm }, function(err,medico){
    console.log(medico +" aqui é o medico");
    if(err){
      res.sendStatus(400).send('medico nao cadastrado no sistema');
    }else{
      var novaConsulta = new Consulta(req.body);
      novaConsulta.medico = medico;
      Cliente.findOne({cpf: req.body.cpf }, function(err,cliente){
        console.log(cliente +" aqui é cliente");
        if(cliente == null){
          novaConsulta.cliente = null;
        }else{
          novaConsulta.cliente = cliente;
        }

      });

      novaConsulta.save(function(err, data3) {
        if (err) {
          res.status(400).json(err);
          } else {
            res.status(201).json(data3);
          }
        });
    }

  });
});

consultaRouter.put('/:idConsulta',function(req,res){
    var corpo = req.body;
    console.log(corpo);
    console.log(req.params.idConsulta);
    Consulta.findByIdAndUpdate(req.params.idConsulta,corpo,{new: true}, function(err,data){
        if(err){
             res.status(500).send(err);
        }else{
             res.send(data)
        }
    });
});


module.exports = consultaRouter;
