import { readApi } from "./readApi.js";

const forumlario1 = document.querySelector(".formulario1");
const input1 = document.querySelector("#nuevo");
const img1 = document.querySelector("#image1");

forumlario1.addEventListener("submit", async (e) => {
	e.preventDefault();
	const file = input1.files[0];

	const formdata = new FormData();
	formdata.append("file", file);
	try {
		const data = await readApi.save(formdata);
		if (data.url.trim()) {
			img1.src = data.url;
			img1.classList.add("activo");
		} else {
			img1.classList.remove("activo");
		}
	} catch (error) {
		alert(error);
	}
});

const forumlario2 = document.querySelector(".formulario2");
const input2 = document.querySelector("#buscar");
const img2 = document.querySelector("#image2");

forumlario2.addEventListener("submit", async (e) => {
	e.preventDefault();
	const name = input2.value;
	try {
		const data = await readApi.find(name);
		console.log(data);
		if (data.url.trim()) {
			img2.src = data.url;
			img2.classList.add("activo");
		} else {
			img2.classList.remove("activo");
		}
	} catch (error) {
		alert(error);
	}
});

const forumlario3 = document.querySelector(".formulario3");
const input3 = document.querySelector("#eliminar");

forumlario3.addEventListener("submit", async (e) => {
	e.preventDefault();
	const name = input3.value;
	try {
		await readApi.delete(name);
        input3.value = 'Borrado xd'
	} catch (error) {
		alert(error);
	}
});
