var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var medicoSchema = new Schema({
	nome:{
		type:String,
		required:true
	},
	DataNascimento:{
		type:String,
		required:true
	},
	cpf:{
		type:Number,
		required:true
	},
	sexo:{
		type:String,
		required:true
	},
	especialização:{
		type:String,
		required:true
	}
},{strict:false});

var medico = mongoose.model('Medico',medicoSchema);

module.exports = medico;