const tabela = document.querySelector("#tabela tbody");

document.getElementById("btnCarregar").addEventListener("click", () => {

    fetch("api/despesas")
        .then(res => res.json())
        .then(lista => {

            tabela.innerHTML = "";

            lista.forEach(d => {

                tabela.innerHTML += `
                    <tr style="cursor:pointer" onclick="abrirModal(${d.id})">
                        <td>${d.descricao}</td>
                        <td>${d.valor}</td>
                        <td>${d.data}</td>
                        <td>${d.categoria.nome}</td>
                    </tr>
                `;
            });

        });

});

function abrirModal(id) {

    const iframe = document.getElementById("iframeDespesa");

    iframe.src = `mostradesp.html?id=${id}`;

    const modal = new bootstrap.Modal(document.getElementById('modalDespesa'));
    modal.show();
}