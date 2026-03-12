const URL = "despesas/";

export async function listarDespesas(){

    const res = await fetch(URL);

    return await res.json();

}

export async function obterDespesa(id){

    const res = await fetch(URL + id);

    return await res.json();

}

export async function salvarDespesa(d){

    const metodo = d.id ? "PUT" : "POST";

    const url = d.id ? URL + d.id : URL;

    await fetch(url,{
        method:metodo,
        headers:{'Content-Type':'application/json'},
        body:JSON.stringify(d)
    });

}

export async function excluirDespesa(id){

    await fetch(URL + id,{
        method:"DELETE"
    });

}