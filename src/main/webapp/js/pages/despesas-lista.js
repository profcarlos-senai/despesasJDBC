import {listarDespesas, excluirDespesa} from "../api/despesas.js";

const tbody = document.querySelector("#tabela tbody");

async function carregar(){

    const despesas = await listarDespesas();

    tbody.innerHTML = "";

    despesas.forEach(d => {

        const tr = document.createElement("tr");

        tr.innerHTML = `
        <td>${d.id}</td>
        <td>${d.descricao}</td>
        <td>${d.valor}</td>
        <td>${d.data}</td>
        <td>${d.categoria?.nome ?? ""}</td>
        <td>
            <a class="btn btn-sm btn-warning" href="editar-despesa.html?id=${d.id}">Editar</a>
            <button class="btn btn-sm btn-danger">Excluir</button>
        </td>
        `;

        tr.querySelector("button").onclick = async () => {

            if(confirm("Excluir?")){
                await excluirDespesa(d.id);
                carregar();
            }

        };

        tbody.appendChild(tr);

    });

}

carregar();