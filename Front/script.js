function openTab(tabName) {
  document.querySelectorAll('.tab').forEach(tab => tab.classList.remove('active'));
  document.getElementById(tabName).classList.add('active');

  if (tabName === 'agenda') carregarAgenda();
  if (tabName === 'consulta') carregarSelects();
  if (tabName === 'agendaMedico') carregarMedicosAgenda();
}

function cadastrarPaciente(e) {
  e.preventDefault();
  const form = e.target;
  const dados = Object.fromEntries(new FormData(form).entries());
  fetch('http://localhost:8080/api/pacientes', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(dados)
  }).then(res => {
    document.getElementById('msgPaciente').textContent = res.ok ? "Cadastro realizado com sucesso!" : "Erro ao cadastrar paciente.";
    if (res.ok) form.reset();
  });
}

function cadastrarMedico(e) {
  e.preventDefault();
  const form = e.target;
  const dados = Object.fromEntries(new FormData(form).entries());
  fetch('http://localhost:8080/api/medicos', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(dados)
  }).then(res => {
    document.getElementById('msgMedico').textContent = res.ok ? "Cadastro realizado com sucesso!" : "Erro ao cadastrar médico.";
    if (res.ok) form.reset();
  });
}

function marcarConsulta(e) {
  e.preventDefault();
  const form = e.target;
  const dados = Object.fromEntries(new FormData(form).entries());

  const consulta = {
    paciente: { id: parseInt(dados.pacienteId) },
    medico: { id: parseInt(dados.medicoId) },
    data: dados.data,
    hora: dados.hora,
    status: "AGENDADA"
  };

  fetch('http://localhost:8080/api/consultas', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(consulta)
  }).then(res => {
    document.getElementById('msgConsulta').textContent = res.ok ? "Consulta marcada com sucesso" : "Erro ao marcar consulta.";
    if (res.ok) {
      form.reset();
      carregarAgenda();
      atualizarDatasHorarios();
    }
  });
}

function carregarAgenda() {
  fetch('http://localhost:8080/api/consultas')
    .then(res => res.json())
    .then(data => {
      const div = document.getElementById('agendaList');
      div.innerHTML = data.map(c => {
        const dataFormatada = new Date(c.data + 'T00:00:00').toLocaleDateString('pt-BR');
        const horaFormatada = c.hora ? c.hora.substring(0, 5) : '';
        const btnCancelar = (c.status === 'AGENDADA') ? `<button onclick="cancelarConsulta(${c.id})">Cancelar</button>` : '';
        return `
          <div class="agenda-item">
            <strong>${dataFormatada} às ${horaFormatada}</strong>
            <span><b>Paciente:</b> ${c.paciente?.nome || 'N/A'}</span>
            <span><b>Médico:</b> ${c.medico?.nome || 'N/A'}</span>
            <span><b>Status:</b> ${c.status}</span>
            ${btnCancelar}
          </div>
        `;
      }).join('');
    }).catch(() => {
      document.getElementById('agendaList').innerText = 'Erro ao carregar agenda.';
    });
}

function cancelarConsulta(id) {
  fetch(`http://localhost:8080/api/consultas/${id}/cancelar`, {
    method: 'PUT'
  }).then(res => {
    if (res.ok) {
      alert("Consulta cancelada com sucesso!");
      carregarAgenda();
      atualizarDatasHorarios();
    } else {
      alert("Erro ao cancelar a consulta.");
    }
  });
}

function carregarSelects() {
  const pacienteSelect = document.getElementById('selectPaciente');
  const medicoSelect = document.getElementById('selectMedico');

  pacienteSelect.innerHTML = '<option value="">Selecione o paciente</option>';
  medicoSelect.innerHTML = '<option value="">Selecione o médico</option>';

  fetch('http://localhost:8080/api/pacientes')
    .then(res => res.json())
    .then(pacientes => {
      pacientes.forEach(p => {
        const opt = document.createElement('option');
        opt.value = p.id;
        opt.textContent = `${p.nome} (ID: ${p.id})`;
        pacienteSelect.appendChild(opt);
      });
    });

  fetch('http://localhost:8080/api/medicos')
    .then(res => res.json())
    .then(medicos => {
      medicos.forEach(m => {
        const opt = document.createElement('option');
        opt.value = m.id;
        opt.textContent = `${m.nome} (ID: ${m.id})`;
        medicoSelect.appendChild(opt);
      });

      medicoSelect.addEventListener('change', atualizarDatasHorarios);
    });
}

function atualizarDatasHorarios() {
  const medicoId = document.getElementById('selectMedico').value;
  const dataSelect = document.getElementById('selectData');
  const horaSelect = document.getElementById('selectHora');

  dataSelect.innerHTML = '<option value="">Selecione a data</option>';
  horaSelect.innerHTML = '<option value="">Selecione a hora</option>';

  if (!medicoId) return;

  fetch(`http://localhost:8080/api/agendas/medico/${medicoId}`)
    .then(res => res.json())
    .then(agendas => {
      fetch('http://localhost:8080/api/consultas')
        .then(res => res.json())
        .then(consultas => {
          const agendados = consultas
            .filter(c => c.status === 'AGENDADA' && c.medico.id == medicoId)
            .map(c => `${c.data}_${c.hora}`);

          const agendasDisponiveis = agendas.filter(a => !agendados.includes(`${a.data}_${a.hora}`));

          const datasUnicas = [...new Set(agendasDisponiveis.map(a => a.data))];
          datasUnicas.forEach(data => {
            const opt = document.createElement('option');
            opt.value = data;
            opt.textContent = new Date(data + 'T00:00:00').toLocaleDateString('pt-BR');
            dataSelect.appendChild(opt);
          });

          dataSelect.onchange = () => {
            const dataSelecionada = dataSelect.value;
            horaSelect.innerHTML = '<option value="">Selecione a hora</option>';
            const horas = agendasDisponiveis
              .filter(a => a.data === dataSelecionada)
              .map(a => a.hora.substring(0, 5));
            horas.forEach(h => {
              const opt = document.createElement('option');
              opt.value = h;
              opt.textContent = h;
              horaSelect.appendChild(opt);
            });
          };
        });
    });
}

function carregarMedicosAgenda() {
  const select = document.getElementById("selectMedicoAgenda");
  select.innerHTML = '<option value="">Selecione o médico</option>';

  fetch('http://localhost:8080/api/medicos')
    .then(res => res.json())
    .then(medicos => {
      medicos.forEach(m => {
        const opt = document.createElement('option');
        opt.value = m.id;
        opt.textContent = `${m.nome} (ID: ${m.id})`;
        select.appendChild(opt);
      });
    });
}

function cadastrarAgendaMedico(e) {
  e.preventDefault();
  const medicoId = document.getElementById("selectMedicoAgenda").value;
  const data = document.getElementById("dataAgenda").value;
  const horaManual = document.getElementById("horaAgenda").value;
  const horariosCheckboxes = document.querySelectorAll("#horariosGerados input[type='checkbox']:checked");

  const horarios = [];

  if (horariosCheckboxes.length > 0) {
    horariosCheckboxes.forEach(cb => horarios.push(cb.value));
  } else if (horaManual) {
    horarios.push(horaManual);
  }

  if (!medicoId || !data || horarios.length === 0) {
    document.getElementById('msgAgendaMedico').textContent = "Preencha todos os campos obrigatórios.";
    return;
  }

  const promises = horarios.map(hora => {
    const agenda = {
      medico: { id: parseInt(medicoId) },
      data: data,
      hora: hora
    };

    return fetch('http://localhost:8080/api/agendas', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(agenda)
    });
  });

  Promise.all(promises)
    .then(results => {
      const sucesso = results.every(res => res.ok);
      document.getElementById('msgAgendaMedico').textContent = sucesso
        ? "Horário(s) cadastrados com sucesso!"
        : "Erro ao cadastrar um ou mais horários.";
      e.target.reset();
      document.getElementById('horariosGerados').innerHTML = '';
    });
}

function sugerirHorarios() {
  const turno = document.getElementById("turnoAgenda").value;
  const container = document.getElementById("horariosGerados");
  container.innerHTML = "";

  if (!turno) return;

  let horaInicio, horaFim;

  if (turno === "manha") {
    horaInicio = 8;
    horaFim = 12;
  } else if (turno === "tarde") {
    horaInicio = 13;
    horaFim = 17;
  }

  for (let h = horaInicio; h < horaFim; h++) {
    for (let m = 0; m < 60; m += 30) {
      const horaStr = `${h.toString().padStart(2, '0')}:${m.toString().padStart(2, '0')}`;
      const checkbox = document.createElement("input");
      checkbox.type = "checkbox";
      checkbox.value = horaStr;
      checkbox.id = "hora_" + horaStr;

      const label = document.createElement("label");
      label.htmlFor = checkbox.id;
      label.textContent = horaStr;

      const wrapper = document.createElement("div");
      wrapper.appendChild(checkbox);
      wrapper.appendChild(label);
      container.appendChild(wrapper);
    }
  }
}
