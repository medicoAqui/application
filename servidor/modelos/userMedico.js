var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var medicoSchema = new Schema({
	name:{
		type:String,
		required:true
	},
	DataNascimento:{
		type:String,
		required:true
	},
	cpf:{
		type:Number,
		required:true,
		unique:true
	},
	sexo:{
		type:String,
		required:true
	},
	crm:{
		type:String,
		required:true,
		unique:true
	},
	consultorio : { type: Schema.Types.ObjectId, ref: 'Consultorio'},
	especialidades : [{ type: Schema.Types.ObjectId, ref: 'Especialidade' }]
});

var medico = mongoose.model('Medico',medicoSchema);

module.exports = medico;
