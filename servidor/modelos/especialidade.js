var mongoose = require('mongoose');
const AutoIncrement = require('mongoose-sequence')(mongoose);
var Schema = mongoose.Schema;

var especi_Schema = new Schema({
  
	nomeEspecialidade:{ type: String, required: true, unique:true}

});
especi_Schema.plugin(AutoIncrement, {inc_field: 'id'});
/*
function getNextSequenceValue(sequenceName){

   var sequenceDocument = Especilidade.findAndModify({
      query:{_id: sequenceName },
      update: {$inc:{sequence_value:1}},
      new:true
   });
	
   return sequenceDocument.sequence_value;
}

*/
var especilidade = mongoose.model('Especialidade', especi_Schema);

module.exports = especilidade;
