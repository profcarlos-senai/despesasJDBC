import {obterDespesa, salvarDespesa} from "../api/despesas.js";
import {listarCategorias} from "../api/categorias.js";

const params = new URLSearchParams(location.search);

const id = params.get("id");

const form = document.getElementById("form");

async function carregarCategorias(){

    const categorias = await listarCategorias();

    const sel = document.getElementById("categoria");

    categorias.forEach(c => {

        const opt = document.createElement("option");

        opt.value = c.id;
        opt.textContent = c.nome;

        sel.appendChild(opt);

    });

}

async function carregar(){

    if(!id) return;

    const d = await obterDespesa(id);

    id.value = d.id;
    descricao.value = d.descricao;
    valor.value = d.valor;
    data.value = d.data;
    categoria.value = d.categoria.id;

}

form.onsubmit = async e => {

    e.preventDefault();

    const d = {

        id:id.value,
        descricao:descricao.value,
        valor:valor.value,
        data:data.value,
        categoria:{id:categoria.value}

    };

    await salvarDespesa(d);

    location.href = "index.html";

};

carregarCategorias();
carregar();