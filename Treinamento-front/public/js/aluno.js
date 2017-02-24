var aluno = {
    cadastrarAluno: function () {
        var url = 'http://127.0.0.1:8080/aluno/create';
        console.log("Entrou");
        var data = verificaEndereco();
        
        console.log(data);
        $.ajax({     
            type: "POST",
            contentType: "application/json",
            url: url,
            data: JSON.stringify(data),
            success: callbackSucessoCadastroAluno,
            error: callbackErroCadastroAluno
        });
        
    }
}

var verificaEndereco = function () {

    if ( $("#logradouro").val()!= "" || $("#numero").val() != ""
    || $("#cidade").val() != "" || $("#uf").val() != ""
    || $("#cep").val() != "" ) {
        return data = {
            cpf:$("#cpfAluno").val(),
            nome:$("#nomeAluno").val(),
            dataNascimento:$("#datanascimentoAluno").val(),
            sexo:$("#sexoAluno").val(),
            email:$("#emailAluno").val(),
            numero : $("#numeroAluno").val(),
            logradouro : $("#logradouroAluno").val(),
            cidade : $("#cidadeAluno").val(),
            bairro : $("#bairroAluno").val(),
            uf : $("#ufAluno").val(),
            cep : $("#cepAluno").val()
        }
    } else {
        return data = {
            cpf:$("#cpfAluno").val(),
            nome:$("#nomeAluno").val(),
            dataNascimento:$("#datanascimentoAluno").val(),
            sexo:$("#sexoAluno").val(),
            email:$("#emailAluno").val()
        }
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

var callbackSucessoCadastroAluno = function(data){
    var result = '<div class="alert alert-success" role="alert">Item gravado com sucesso</div>'; 
    console.log(data);
    $('#result').append(result);
}

var callbackErroCadastroAluno = function(data){
    var result = '<div class="alert alert-danger" role="alert">Erro ao gravar Item'+data.responseText+'</div>';
    console.log("erro: "+data.responseText);
    $('#result').append(result);
}