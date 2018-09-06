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

consultaRouter.post('/consultasByDateAndCpfCliente',function(req,res){
    Consulta.find({dataConsulta: req.body.dataConsulta, cliente: req.params.id}).exec(function(err,data){
        if(err){
            res.sendStatus(400).send("Cliente não cadastrado no sistema");

        }else{
            res.send(data);
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

consultaRouter.post('/TconsultasByCpfClienteAndStatus',function(req,res){
    Consulta.find({status: req.body.status, cliente: req.param.id}).exec(function(err,data){
        if(err){
            res.sendStatus(400).send("cliente não cadastrado no sistema");
        }else{
            res.send(data);
        }
    })

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

consultaRouter.post('/TconsultasByCpfCliente',function(req,res){
    Consulta.find({cliente: req.params.id}).exec(function(err,data){
        if ( err){
            res.sendStatus(400).send('Cliente nao encontrado no sistema');
        }else{
            res.send(data);
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

consultaRouter('/TconsultasByDateCrmStatus', function(req,res){
    Consulta.find({dataConsulta: req.body.dataConsulta, status:req.body.status, medico: req.params.id })
    .exec(function(err,data){
        if(err){
            res.sendStatus(400).send("Medico não econtrado no sistema" );
        }else{
            res.send(data);
        }
    })


});

consultaRouter.post('/add', function(req,res){
    var novaConsulta = new Consulta(req.body);
    if(req.body.medico == null){
        res.sendStatus(400).send('Medico precisa estar cadastrado no sistema');
    }else{
        var novaconsulta = new Consulta(req.body);
        if(req.body.cliente == null){
            novaConsulta.cliente = null;
            novaConsulta.save(function(err, consulta) {
                if (err) {
                    res.status(400).send(err);
                } else {
                    res.status(201).send(consulta);
                }
            });

        }else{
            novaConsulta.save(function(err, consulta) {
                if (err) {
                    res.status(400).send(err);
                } else {
                    res.status(201).send(consulta);
                }
            });

        }
    }

});

consultaRouter.post('/consultaByIdMedico', function(req,res){
    Consulta.find({medico: req.body.id}).exec(function(err,data){
        console.log(req.body.id);
        console.log(data);
        if(err){
            res.sendStatus(400).send('medico nao cadastrado no sistema');
        }else{
            res.send(data);
        }

    });
});


consultaRouter.post('/consultaByIdMedicoAndStatus', function(req,res){
    Consulta.find({medico: req.body.id, status: req.body.status}).exec(function(err,consultas){
        if(err){
            res.sendStatus(400).send('medico ou estatos nao cadastrado no sistema');
        }else{
            res.send(consultas);
        }

    });
});

consultaRouter.put('/desmarcarConsulta/:idConsulta',function(req,res){
    var corpo = req.body;
    
    Consulta.findByIdAndUpdate(req.params.idConsulta,corpo,{new: true}, function(err,data){
        if(err){
             res.status(500).send(err);
        }else{
             res.send(data)
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
