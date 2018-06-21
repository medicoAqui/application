var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var consultaSchema = new Schema({
	
	cpfMedico:{type: String,required:true},
	cpfPaciente:{type:String},
	data:{type:Date, required:true},
	hora:{type:String,required:true},
	disponibilidade:{type:Boolean }
})

var consulta = mongoose.model('Consulta',consultaSchema);

module.exports = consulta;