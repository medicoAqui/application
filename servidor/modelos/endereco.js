var mongoose = require('mongoose');
var Schema = mongoose.Schema;

const AutoIncrement = require('mongoose-sequence')(mongoose);

var enderecoSchema = new Schema({

	rua:{type: String,required:true},
  referencia:{type: String,required:false},
  bairro:{type: String,required:true},
  cidade:{type: String,required:true},
  uf:{type: String,required:true},
  cep:{type:Number,	required:true},
  numero:{type:Number,	required:true}

})

enderecoSchema.plugin(AutoIncrement, {inc_field: 'idEndereco'});

var endereco = mongoose.model('Endereco',enderecoSchema);

module.exports = endereco;
