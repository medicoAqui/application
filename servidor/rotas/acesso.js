var express = require('express');
var router = express.Router();
var UserCliente = require('../modelos/userCliente.js');
var jwt = require('jsonwebtoken');
var config = require('../config');

var app = express();

router.post('/authenticate', function(req, res) {

    UserCliente.findOne({cpf: req.body.cpf}, function (err, user) {
        console.log(req.body.cpf );
        console.log(user);
        console.log(user.cpf);

        var to = jwt.sign(user.cpf, 'shhhhh');
         console.log(to);

        if (err) {
            throw err;
        }

        if (!user) {
            res.json({success: false, message: 'Usuario não encontrado'});
        } else if (user) {


            if (user.password != req.body.password) {
                res.json({success: false, message: 'Senha Invalida'});
            } else {

                // cria o token
                console.log('aqui é o config  '+ config.secret);
                var token = jwt.sign(user.cpf, config.secret);
                console.log(token);

                res.json({
                    usuario: user,
                    success: true,
                    message: 'Aqui o token!',
                    token: token
                });
            }
        }
    });
});

router.get('/logout', function(req, res) {
    res.status(200).send({  token: null });
});


module.exports = router;


