//var Referencia = require('mongoose').model('Referencia');

module.exports = {
    cadastrarReferencia: function (req,res,next) {
        /*var ref = new Referencia();
        ref.livro = req.query.livro;
        ref.capitulo = req.query.capitulo;
        ref.pagina = req.query.pagina;
        ref.assunto = req.query.assunto;
        ref.observacao = req.query.observacao;
        ref.save(function (err) {
            if(err) return res.status(500).send();
            return res.send(ref);
        })*/
    },
    pesquisaRefByBook: function (req,res,next) {
        /*var response = new Array();
        var query = { 'livro': req.query.livro};
        Referencia.find(query).populate('livro').exec(function (err,referencias) {
            if(err) return res.status(500).send();
            response.push(referencias);
            return res.send(response);
        })*/
    },
    pesquisaRefByAssunto: function (req,res,next) {
        /*var response = new Array();
        var valor = new RegExp(req.query.assunto, 'i');
        var query = { 'assunto': valor};
        Referencia.find(query).populate('livro').exec(function (err,referencias) {
            if(err) return res.status(500).send();
            response.push(referencias);
            return res.send(response);
        })*/
    }
}