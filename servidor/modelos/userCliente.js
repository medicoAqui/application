var mongoose = require('mongoose');
var Schema = mongoose.Schema;


var usuarioSchema = new Schema({
	nome: {
		type:String,
		required: true
	},
	dataNascimento:{
		type:Date,
		required:true
	},
	email:{
		type:String,
		required:true
	},
	sexo:{
		type:String,
		required: true
	},
	tefefone:{
		type:String,
		required:true
	},
	cpf:{
		type:Number,
		required: true,
		unique:true
	},
	estadoCivil:{
		type:String,
		required: true,
	},
	password:{
		type:String,
		required:true
	}

},{ strict: false });

var usuario = mongoose.model('Usuario',usuarioSchema);

module.exports = usuario;