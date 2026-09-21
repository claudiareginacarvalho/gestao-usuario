var token = localStorage.getItem("token");
var usuarios = [];
var usuarioEditando = null;
var usuarioLogado = null;

var login = document.getElementById("login");
var sistema = document.getElementById("sistema");
var mensagem = document.getElementById("mensagem");

function fazerRequisicao(url, metodo, dados, quandoTerminar) {
    var opcoes = {
        method: metodo,
        headers: {}
    };

    if (token) {
        opcoes.headers.Authorization = "Bearer " + token;
    }

    if (dados) {
        opcoes.headers["Content-Type"] = "application/json";
        opcoes.body = JSON.stringify(dados);
    }

    fetch(url, opcoes)
        .then(function (response) {
            return response.text().then(function (texto) {
                var corpo = texto;

                if (texto) {
                    try {
                        corpo = JSON.parse(texto);
                    } catch (erro) {
                        corpo = texto;
                    }
                }

                quandoTerminar(response, corpo);
            });
        })
        .catch(function (erro) {
            mensagem.textContent = "Não foi possível acessar a API.";
        });
}

function mostrarSistema() {
    login.className = "hidden";
    sistema.className = "";
    buscarUsuarioLogado();
}

function pegarDadosDoToken() {
    var partes = token.split(".");
    var payload = partes[1].replace(/-/g, "+").replace(/_/g, "/");

    while (payload.length % 4 !== 0) {
        payload += "=";
    }

    return JSON.parse(atob(payload));
}

function buscarUsuarioLogado() {
    var dadosDoToken = pegarDadosDoToken();

    fazerRequisicao("/usuarios/" + dadosDoToken.id, "GET", null, function (response, dados) {
        if (!response.ok) {
            mensagem.textContent = dados;
            return;
        }

        usuarioLogado = dados;
        document.getElementById("usuarioIdLogado").textContent = dados.id;
        document.getElementById("usuarioEmailLogado").textContent = dados.email;
        document.getElementById("usuarioPerfilLogado").textContent = dados.perfil;

        if (dados.perfil === "CLIENTE") {
            usuarios = [dados];
            montarTabela();
            mensagem.textContent = "";
        } else {
            carregarUsuarios();
        }
    });
}

function carregarUsuarios() {
    document.getElementById("listaUsuarios").innerHTML = "";

    fazerRequisicao("/usuarios", "GET", null, function (response, dados) {
        if (!response.ok) {
            mensagem.textContent = dados;
            return;
        }

        usuarios = dados;
        montarTabela();
        mensagem.textContent = "";
    });
}

function montarTabela() {
    var tabela = document.getElementById("listaUsuarios");
    tabela.innerHTML = "";

    for (var i = 0; i < usuarios.length; i++) {
        var usuario = usuarios[i];
        var linha = document.createElement("tr");
        adicionarCelula(linha, usuario.id);
        adicionarCelula(linha, usuario.nome);
        adicionarCelula(linha, usuario.email);
        adicionarCelula(linha, usuario.perfil);

        var celulaAcoes = document.createElement("td");
        if (usuarioLogado.perfil === "ADMINISTRADOR" || usuarioLogado.perfil === "OPERADOR") {
            var botaoEditar = document.createElement("button");
            botaoEditar.textContent = "Editar";
            botaoEditar.onclick = criarFuncaoEditar(usuario);
            celulaAcoes.appendChild(botaoEditar);
        }

        if (usuarioLogado.perfil === "ADMINISTRADOR") {
            var botaoExcluir = document.createElement("button");
            botaoExcluir.textContent = "Excluir";
            botaoExcluir.onclick = criarFuncaoExcluir(usuario.id);
            celulaAcoes.appendChild(botaoExcluir);
        }

        linha.appendChild(celulaAcoes);
        tabela.appendChild(linha);
    }
}

function adicionarCelula(linha, valor) {
    var celula = document.createElement("td");
    celula.textContent = valor;
    linha.appendChild(celula);
}

function criarFuncaoEditar(usuario) {
    return function () {
        usuarioEditando = usuario.id;
        document.getElementById("tituloFormulario").textContent = "Editar usuário";
        document.getElementById("usuarioId").value = usuario.id;
        document.getElementById("nome").value = usuario.nome;
        document.getElementById("email").value = usuario.email;
        document.getElementById("senha").value = "";
        document.getElementById("perfil").value = usuario.perfil;
    };
}

function criarFuncaoExcluir(id) {
    return function () {
        if (confirm("Deseja excluir este usuário?")) {
            fazerRequisicao("/usuarios/" + id, "DELETE", null, function (response, dados) {
                if (response.ok) {
                    carregarUsuarios();
                } else {
                    mensagem.textContent = dados;
                }
            });
        }
    };
}

function limparFormulario() {
    usuarioEditando = null;
    document.getElementById("tituloFormulario").textContent = "Cadastrar usuário";
    document.getElementById("usuarioForm").reset();
    document.getElementById("usuarioId").value = "";
}

document.getElementById("loginForm").onsubmit = function (evento) {
    evento.preventDefault();

    var dados = {
        email: document.getElementById("loginEmail").value,
        senha: document.getElementById("loginSenha").value
    };

    fazerRequisicao("/login", "POST", dados, function (response, corpo) {
        if (response.ok) {
            token = corpo.token;
            localStorage.setItem("token", token);
            mostrarSistema();
        } else {
            mensagem.textContent = corpo;
        }
    });
};

document.getElementById("usuarioForm").onsubmit = function (evento) {
    evento.preventDefault();

    var dados = {
        nome: document.getElementById("nome").value,
        email: document.getElementById("email").value,
        senha: document.getElementById("senha").value,
        perfil: document.getElementById("perfil").value
    };

    var url = "/usuarios";
    var metodo = "POST";

    if (usuarioEditando) {
        url = "/usuarios/" + usuarioEditando;
        metodo = "PUT";
    }

    fazerRequisicao(url, metodo, dados, function (response, corpo) {
        if (response.ok) {
            limparFormulario();
            carregarUsuarios();
        } else {
            mensagem.textContent = corpo;
        }
    });
};

document.getElementById("atualizarLista").onclick = carregarUsuarios;
document.getElementById("cancelarEdicao").onclick = limparFormulario;
document.getElementById("sair").onclick = function () {
    localStorage.removeItem("token");
    location.reload();
};

if (token) {
    mostrarSistema();
}
