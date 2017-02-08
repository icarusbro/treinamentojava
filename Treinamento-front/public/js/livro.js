var livro = {
    realizarPesquisa: function () {
        var url = '/consultaLivros';
        $('#result').empty();
        var data = {
            nome: $("#livros").val(),
        };
        $.ajax({
            dataType: "json",
            url: url,
            success: callbackSucessoLivro,
            error: callbackErroLivro
        });
    },
    realizarPesquisaDropDown: function () {
        var url = '/consultaLivrosDrop';
        $('#result').empty();
        $.ajax({
            dataType: "json",
            url: url,
            success: callbackSucessoDropLivro,
            error: callbackErroLivro
        });
    },
    cadastrarLivro: function () {
        var url = '/InserirLivro';
        $('#resultCadastro').empty();
        var data = {
            nome: $("#nomeLivro").val(),
            autor: $("#nomeAutor").val()
        };
        $.ajax({
            dataType: "json",
            url: url,
            data: data,
            success: callbackSucessoCadastroLivro,
            error: callbackErroCadastroLivro
        });
    }
}


var callbackSucessoLivro = function(data){
    var result = '<div class="table-responsive"><table id="linkstable" class="table table-hover table-striped"><thead><tr><th>Nome</th><th>Descricao</th><th>Quantidade</th><th>Quantidade Emprestada</th></tr></thead><tbdoy>';
    data.forEach(function (pesquisa) {
        result += '<tr><td>'+pesquisa.nome+'</td><td>'+pesquisa.descricao+'</td><td>'+pesquisa.quantidade+'</td><td>'+pesquisa.quantidadeEmprestada+'</td></tr>';
    });
    result += '</tbody></table></div>';
    $('#result').append(result);
    
    $('#linkstable').DataTable();
}

var callbackSucessoDropLivro = function(data){
    var livros = '';
     data.forEach(function (pesquisa) {
        livros +=  '<option value="'+pesquisa._id+'">'+pesquisa.nome+'</option>';
    });
    $('#livros').append(livros);

}

var callbackErroLivro = function(data){
    var result = '<div class="alert alert-danger" role="alert">Erro ao carregar Itens</div>';
    $('#result').append(result);
}

var callbackSucessoCadastroLivro = function(data){
    var result = '<div class="alert alert-success" role="alert">Item gravado com sucesso</div>';
    $('#result').append(result);
}

var callbackErroCadastroLivro = function(data){
    var result = '<div class="alert alert-danger" role="alert">Erro ao gravar Item</div>';
    $('#result').append(result);
}