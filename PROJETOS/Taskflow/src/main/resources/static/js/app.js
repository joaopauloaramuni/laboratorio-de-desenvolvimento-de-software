// ---------------------------------------------------------------------------
// TaskFlow - front-end
// Este arquivo demonstra como o front-end se comunica com o back-end
// exclusivamente atraves de requisicoes HTTP para o recurso REST /api/tasks,
// usando JSON como contrato de dados entre as partes.
// ---------------------------------------------------------------------------

const API_URL = "/api/tasks";

const form = document.getElementById("task-form");
const taskIdInput = document.getElementById("task-id");
const titleInput = document.getElementById("title");
const descriptionInput = document.getElementById("description");
const deadlineInput = document.getElementById("deadline");
const submitBtn = document.getElementById("submit-btn");
const cancelEditBtn = document.getElementById("cancel-edit-btn");
const tableBody = document.getElementById("task-table-body");
const reportContainer = document.getElementById("report");
const filterButtons = document.querySelectorAll(".filter-btn");

let currentFilter = "";

document.addEventListener("DOMContentLoaded", () => {
    loadTasks();
    loadReport();
});

// ---------------------------------------------------------------------------
// GET /api/tasks (com filtro opcional por status)
// ---------------------------------------------------------------------------
async function loadTasks() {
    const url = currentFilter ? `${API_URL}?status=${currentFilter}` : API_URL;
    const response = await fetch(url);
    const tasks = await response.json();
    renderTasks(tasks);
}

// ---------------------------------------------------------------------------
// GET /api/tasks/report
// ---------------------------------------------------------------------------
async function loadReport() {
    const response = await fetch(`${API_URL}/report`);
    const report = await response.json();
    renderReport(report);
}

function renderReport(report) {
    reportContainer.innerHTML = `
        <div class="report-item"><div class="value">${report.totalTarefas}</div><div class="label">Total</div></div>
        <div class="report-item"><div class="value">${report.pendentes}</div><div class="label">Pendentes</div></div>
        <div class="report-item"><div class="value">${report.emAndamento}</div><div class="label">Em andamento</div></div>
        <div class="report-item"><div class="value">${report.concluidas}</div><div class="label">Concluidas</div></div>
        <div class="report-item"><div class="value">${report.atrasadas}</div><div class="label">Atrasadas</div></div>
        <div class="report-item"><div class="value">${report.percentualConcluido}%</div><div class="label">% Concluido</div></div>
    `;
}

function renderTasks(tasks) {
    tableBody.innerHTML = "";
    if (tasks.length === 0) {
        tableBody.innerHTML = `<tr><td colspan="4" style="text-align:center; color:#888;">Nenhuma tarefa encontrada</td></tr>`;
        return;
    }

    tasks.forEach(task => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>
                <strong>${escapeHtml(task.title)}</strong><br>
                <span style="color:#888; font-size:12px;">${escapeHtml(task.description || "")}</span>
            </td>
            <td>${task.deadline}</td>
            <td><span class="status-badge status-${task.status}">${formatStatus(task.status)}</span></td>
            <td class="actions">
                <button onclick="editTask(${task.id})">Editar</button>
                <button onclick="cycleStatus(${task.id}, '${task.status}')">Avancar status</button>
                <button class="delete-btn" onclick="deleteTask(${task.id})">Excluir</button>
            </td>
        `;
        tableBody.appendChild(row);
    });
}

function formatStatus(status) {
    return {
        PENDENTE: "Pendente",
        EM_ANDAMENTO: "Em andamento",
        CONCLUIDA: "Concluida"
    }[status] || status;
}

function escapeHtml(str) {
    const div = document.createElement("div");
    div.textContent = str;
    return div.innerHTML;
}

// ---------------------------------------------------------------------------
// POST /api/tasks  e  PUT /api/tasks/{id}
// ---------------------------------------------------------------------------
form.addEventListener("submit", async (event) => {
    event.preventDefault();

    const payload = {
        title: titleInput.value,
        description: descriptionInput.value,
        deadline: deadlineInput.value,
        status: "PENDENTE"
    };

    const id = taskIdInput.value;

    try {
        let response;
        if (id) {
            // Edicao: PUT /api/tasks/{id}
            response = await fetch(`${API_URL}/${id}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload)
            });
        } else {
            // Criacao: POST /api/tasks
            response = await fetch(API_URL, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload)
            });
        }

        if (!response.ok) {
            const errorBody = await response.json().catch(() => null);
            const message = errorBody?.message || `Erro ${response.status} ao salvar a tarefa.`;
            alert(message);
            return; // nao limpa o formulario nem recarrega a lista se deu erro
        }

        resetForm();
        loadTasks();
        loadReport();
    } catch (err) {
        alert("Nao foi possivel conectar com o servidor. Verifique se a aplicacao esta rodando.");
        console.error(err);
    }
});

async function editTask(id) {
    const response = await fetch(`${API_URL}/${id}`);
    const task = await response.json();

    taskIdInput.value = task.id;
    titleInput.value = task.title;
    descriptionInput.value = task.description || "";
    deadlineInput.value = task.deadline;

    submitBtn.textContent = "Salvar alteracoes";
    cancelEditBtn.style.display = "inline-block";
    window.scrollTo({ top: 0, behavior: "smooth" });
}

cancelEditBtn.addEventListener("click", resetForm);

function resetForm() {
    form.reset();
    taskIdInput.value = "";
    submitBtn.textContent = "Adicionar tarefa";
    cancelEditBtn.style.display = "none";
}

// ---------------------------------------------------------------------------
// PATCH /api/tasks/{id}/status  -> avanca o status da tarefa (ciclo simples)
// ---------------------------------------------------------------------------
const STATUS_FLOW = {
    PENDENTE: "EM_ANDAMENTO",
    EM_ANDAMENTO: "CONCLUIDA",
    CONCLUIDA: "PENDENTE"
};

async function cycleStatus(id, currentStatus) {
    const newStatus = STATUS_FLOW[currentStatus];
    await fetch(`${API_URL}/${id}/status`, {
        method: "PATCH",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ status: newStatus })
    });
    loadTasks();
    loadReport();
}

// ---------------------------------------------------------------------------
// DELETE /api/tasks/{id}
// ---------------------------------------------------------------------------
async function deleteTask(id) {
    if (!confirm("Tem certeza que deseja excluir esta tarefa?")) return;
    await fetch(`${API_URL}/${id}`, { method: "DELETE" });
    loadTasks();
    loadReport();
}

// ---------------------------------------------------------------------------
// Filtros de status
// ---------------------------------------------------------------------------
filterButtons.forEach(btn => {
    btn.addEventListener("click", () => {
        filterButtons.forEach(b => b.classList.remove("active"));
        btn.classList.add("active");
        currentFilter = btn.dataset.status;
        loadTasks();
    });
});