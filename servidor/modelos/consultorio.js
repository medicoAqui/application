var mongoose = require('mongoose');
var Schema = mongoose.Schema;
const AutoIncrement = require('mongoose-sequence')(mongoose);

var consultorioSchema = new Schema({

	nomeConsultorio:{type: String,required:true},
	rua:{type: String,required:true},
	referencia:{type: String,required:false},
  	bairro:{type: String,required:true},
  	cidade:{type: String,required:true},
  	uf:{type: String,required:true},
  	cep:{type:Number,	required:true},
  	numero:{type:Number,	required:true}

});

consultorioSchema.plugin(AutoIncrement, {inc_field: 'idConsultorio'});

var consultorio = mongoose.model('Consultorio',consultorioSchema);

module.exports = consultorio;
