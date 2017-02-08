var referencia = {
    pesquisaRefByBook: function () {
        var url = '/pesquisaRefByBook';
        $('#ListaResult').empty();
        var data = {
            livro: $("#livros").val()
        };
        $.ajax({
            dataType: "json",
            url: url,
            data: data,
            success: callbackSucessoRef,
            error: callbackErroRef
        }); 
    },
    pesquisaRefByAssunto: function () {
        var url = '/pesquisaRefByAssunto';
        $('#ListaResult').empty();
        var data = {
            assunto: $("#assunto").val()
        };
        $.ajax({
            dataType: "json",
            url: url,
            data: data,
            success: callbackSucessoRef,
            error: callbackErroRef
        }); 
    },
    cadastrarReferencia: function () {
        var url = '/InserirRef';
        $('#result').empty();
        var data = {
            livro: $("#livros").val(),
            capitulo: $("#capitulo").val(),
            pagina: $("#pagina").val(),
            assunto: $("#assunto").val(),
            observacao : $("#observacao").val()
        };
        $.ajax({
            dataType: "json",
            url: url,
            data: data,
            success: callbackSucessoCadastroRef,
            error: callbackErroCadastroRef
        });
    }
}


var callbackSucessoRef = function(data){
    var result = '';
    data[0].forEach(function (pesquisa) {
        result += '<li><div class="panel panel-app"><div class="panel-heading">Referencia</div><div class="panel-body"><div class="row"><div class="form-group"><div class="col-lg-10"><label>Livro</label><samp id="livros">   '+pesquisa.livro.nome+'</samp></div>  </br>  <div class="col-lg-5"><label>Capitulo</label><samp id="capitulo">   '+pesquisa.capitulo+'</samp></div><div class="col-lg-5"><label>Pagina</label><samp id="pagina">   '+pesquisa.pagina+'</samp></div>  </br>  <div class="col-lg-10"><label>Assunto</label><samp id="assunto">   '+pesquisa.assunto+'</samp></div>  </br>  </div></div><div class="row"><div class="col-lg-12"><label>Observação</label><samp id="observacao">    '+pesquisa.observacao+'</samp></div></div></div></div></li>';
    });
    $('#ListaResult').append(result);
    
}


var callbackErroRef = function(data){
    var result = '<div class="alert alert-danger" role="alert">Erro ao carregar Itens</div>';
    $('#result').append(result);
}

var callbackSucessoCadastroRef = function(data){
    var result = '<div class="alert alert-success" role="alert">Item gravado com sucesso</div>';
    $('#result').append(result);
}

var callbackErroCadastroRef = function(data){
    var result = '<div class="alert alert-danger" role="alert">Erro ao gravar Item</div>';
    $('#result').append(result);
}