async function carregarMunicipios() {
    const ufSigla = document.getElementById("ufSelect").value;
    const municipioSelect = document.getElementById("municipioSelect");

    municipioSelect.innerHTML = '<option>Carregando...</option>';

    if (!ufSigla) {
        municipioSelect.innerHTML = '<option value="">Selecione um estado primeiro</option>';
        return;
    }

    try {
        const response = await fetch(`/municipios/${ufSigla}`);
        if (!response.ok) throw new Error("Erro ao carregar municípios.");

        const municipios = await response.json();
        municipioSelect.innerHTML = '<option value="">Selecione um município</option>';

        municipios.forEach(m => {
            const opt = document.createElement("option");
            opt.value = m.id;
            opt.textContent = m.nome;
            municipioSelect.appendChild(opt);
        });
    } catch (error) {
        municipioSelect.innerHTML = '<option value="">Erro ao carregar municípios</option>';
        console.error(error);
    }
}
