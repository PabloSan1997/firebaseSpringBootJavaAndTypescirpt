


const baseApi = 'http://localhost:3008/api/image';

export const readApi = {
    async save(data){
        const ft = await fetch(baseApi, {
            method:'POST',
            body:data
        });
        if(!ft.ok)
            throw 'Error con guardar imagen';
        return ft.json();
    },
    async find(name){
        const ft = await fetch(`${baseApi}?fileName=${name}`);
        if(!ft.ok)
            throw 'Error con buscar imagen';
        return ft.json();
    },
    async delete(name){
        const ft = await fetch(`${baseApi}/${name}`, {
            method:'DELETE'
        });
        if(!ft.ok)
            throw 'Error con borrar imagen';
    }
}