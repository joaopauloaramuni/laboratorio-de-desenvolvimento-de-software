// "Codando agora": busca o cartão pronto (fragmento do Thymeleaf) em
// GET /fragmentos/codando-agora e repete a cada wakatime.coding-now.refresh-seconds
// (60 s), como o useCodingNow do projeto React.
//
//   200 → mostra o cartão
//   204 → sem token ou sem o escopo read_heartbeats: esconde e para de perguntar
//   503 → erro passageiro (ex.: WakaTime fora do ar): esconde e tenta de novo
//
// Só pergunta com a aba visível; ao voltar para a aba, atualiza na hora.
(function () {
  const slot = document.getElementById("waka-now");
  if (!slot) return;

  const url = slot.dataset.url;
  const refreshMs = (Number(slot.dataset.refreshSeconds) || 60) * 1000;
  let timer = null;

  function hide() {
    slot.hidden = true;
    slot.innerHTML = "";
  }

  function stop() {
    clearInterval(timer);
    document.removeEventListener("visibilitychange", refresh);
  }

  async function refresh() {
    if (document.visibilityState === "hidden") return;
    try {
      const response = await fetch(url, { headers: { Accept: "text/html" } });
      if (response.status === 204) {
        hide();
        stop();
        return;
      }
      if (!response.ok) {
        hide();
        return;
      }
      // HTML gerado pelo Thymeleaf no servidor, com os textos já escapados
      slot.innerHTML = await response.text();
      slot.hidden = false;
    } catch {
      // Sem rede: mantém o que já está na tela e tenta no próximo ciclo
    }
  }

  refresh();
  timer = setInterval(refresh, refreshMs);
  document.addEventListener("visibilitychange", refresh);
})();
