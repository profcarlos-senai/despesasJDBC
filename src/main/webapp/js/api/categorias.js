const URL = "categorias/";

export async function listarCategorias(){

    const res = await fetch(URL);

    return await res.json();

}