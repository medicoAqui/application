var mongoose = require('mongoose');
var Schema = mongoose.Schema;
const AutoIncrement = require('mongoose-sequence')(mongoose);

var consultaSchema = new Schema({

	observacao:{type:String},
	dataConsulta:{type:String, required:true},
	hora:{type:String,required:true},
	status:{type:String, required:true },
  medico : { type: Schema.Types.ObjectId, ref: 'Medico' },
  cliente : { type: Schema.Types.ObjectId, ref: 'Usuario' }

})

consultaSchema.plugin(AutoIncrement, {inc_field: 'idConsulta'});

var consulta = mongoose.model('Consulta',consultaSchema);

module.exports = consulta;
