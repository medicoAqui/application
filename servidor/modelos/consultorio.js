var mongoose = require('mongoose');
var Schema = mongoose.Schema;
const AutoIncrement = require('mongoose-sequence')(mongoose);

var consultorioSchema = new Schema({

	nomeConsultorio:{type: String,required:true},
  endereco : { type: Schema.Types.ObjectId, ref: 'Endereco'}
})
consultorioSchema.plugin(AutoIncrement, {inc_field: 'idConsultorio'});

var consultorio = mongoose.model('Consultorio',consultorioSchema);

module.exports = consultorio;
