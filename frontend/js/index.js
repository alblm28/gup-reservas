// Referencias DOM escritorio
const cuadriculaCabanas  = document.getElementById('cuadricula_cabanas');
const estadoCarga        = document.getElementById('estado_carga');
const estadoError        = document.getElementById('estado_error');
const estadoVacio        = document.getElementById('estado_vacio');
const botonReintentar    = document.getElementById('boton_reintentar');

// Filtros escritorio
const botonFiltrar       = document.getElementById('boton_filtrar');
const botonLimpiar       = document.getElementById('boton_limpiar');
const btnMasFiltros      = document.getElementById('btn_mas_filtros');
const panelMasFiltros    = document.getElementById('panel_mas_filtros');
const btnCerrarFiltros   = document.getElementById('btn_cerrar_filtros');

// Filtros movil
const btnToggleFiltros   = document.getElementById('btn_toggle_filtros');
const panelFiltrosMovil  = document.getElementById('panel_filtros_movil');
const botonFiltrarMovil  = document.getElementById('boton_filtrar_movil');
const botonLimpiarMovil  = document.getElementById('boton_limpiar_movil');

// Menu movil cabecera
const btnMenuMovil       = document.getElementById('btn_menu_movil');
const menuMovil          = document.getElementById('menu_movil');

// Estado
let todasLasCabanas = [];

// Mostrar estado
function mostrarEstado(cual) {
    estadoCarga.hidden       = cual !== 'carga';
    estadoError.hidden       = cual !== 'error';
    estadoVacio.hidden       = cual !== 'vacio';
    cuadriculaCabanas.hidden = cual !== 'cuadricula';
}

// Renderizar tarjeta
function renderizarTarjeta(cabana) {
    const plantilla = document.getElementById('plantilla_cabana');
    const tarjeta = plantilla.content.cloneNode(true).querySelector('.tarjeta_cabana');

    const etiqueta = tarjeta.querySelector('.tarjeta_cabana_estado');
    etiqueta.textContent = cabana.estado;
    etiqueta.classList.add(`tarjeta_cabana_estado_${cabana.estado}`);

    tarjeta.querySelector('.tarjeta_cabana_nombre').textContent = cabana.nombreCabania;
    tarjeta.querySelector('.tarjeta_cabana_descripcion').textContent =
        cabana.descripcion || 'Sin descripción disponible.';
    tarjeta.querySelector('.tarjeta_cabana_capacidad').textContent =
        `${cabana.capacidadMax} persona${cabana.capacidadMax !== 1 ? 's' : ''}`;
    tarjeta.querySelector('.tarjeta_cabana_cama').textContent =
        cabana.cama.replace(/_/g, ' ');
    tarjeta.querySelector('.tarjeta_cabana_banios').textContent =
        `${cabana.numBanios} baño${cabana.numBanios !== 1 ? 's' : ''}`;
    tarjeta.querySelector('.tarjeta_cabana_precio').textContent =
        `${Number(cabana.precioNoche).toFixed(2)} € / noche`;

    const boton = tarjeta.querySelector('.tarjeta_cabana_boton');
    boton.href = `cabania.html?id=${cabana.idCabania}`;

    if (cabana.estado !== 'disponible') {
        boton.style.pointerEvents = 'none';
        boton.style.opacity = '0.4';
        boton.textContent = 'No disponible';
    }

    return tarjeta;
}

// Renderizar cuadricula
function renderizarCuadricula(cabanas) {
    cuadriculaCabanas.innerHTML = '';
    if (cabanas.length === 0) { mostrarEstado('vacio'); return; }
    cabanas.forEach(c => cuadriculaCabanas.appendChild(renderizarTarjeta(c)));
    mostrarEstado('cuadricula');
}

// Ordenar
function ordenarCabanas(cabanas, criterio) {
    const lista = [...cabanas];
    switch (criterio) {
        case 'precio_asc':  return lista.sort((a, b) => a.precioNoche - b.precioNoche);
        case 'precio_desc': return lista.sort((a, b) => b.precioNoche - a.precioNoche);
        case 'capacidad':   return lista.sort((a, b) => b.capacidadMax - a.capacidadMax);
        default:            return lista.sort((a, b) => a.nombreCabania.localeCompare(b.nombreCabania));
    }
}

// Leer valores de filtros (escritorio o movil segun pantalla)
function leerFiltros() {
    const esMovil = window.innerWidth <= 768;
    const sufijo  = esMovil ? '_movil' : '';

    return {
        fechaInicio: document.getElementById(`filtro_fecha_inicio${sufijo}`)?.value || '',
        fechaFin:    document.getElementById(`filtro_fecha_fin${sufijo}`)?.value || '',
        capacidad:   parseInt(document.getElementById(`filtro_capacidad${sufijo}`)?.value) || 0,
        cama:        document.getElementById(`filtro_cama${sufijo}`)?.value || '',
        ventilacion: document.getElementById(`filtro_ventilacion${sufijo}`)?.value || '',
        enchufes:    document.querySelector(`input[name="filtro_enchufes${sufijo}"]:checked`)?.value || '',
        orden:       document.getElementById(`filtro_orden${sufijo}`)?.value || 'nombre',
    };
}

// Aplicar filtros
function aplicarFiltros() {
    const f = leerFiltros();
    let resultado = [...todasLasCabanas];

    if (f.capacidad > 0) resultado = resultado.filter(c => c.capacidadMax >= f.capacidad);
    if (f.cama)          resultado = resultado.filter(c => c.cama === f.cama);
    if (f.ventilacion)   resultado = resultado.filter(c => c.ventilacion === f.ventilacion);
    if (f.enchufes === 'si') resultado = resultado.filter(c => c.enchufes > 0);
    if (f.enchufes === 'no') resultado = resultado.filter(c => !c.enchufes || c.enchufes === 0);

    renderizarCuadricula(ordenarCabanas(resultado, f.orden));
}

// Limpiar filtros
function limpiarFiltros() {
    ['filtro_fecha_inicio', 'filtro_fecha_fin', 'filtro_capacidad',
     'filtro_cama', 'filtro_ventilacion', 'filtro_orden',
     'filtro_fecha_inicio_movil', 'filtro_fecha_fin_movil', 'filtro_capacidad_movil',
     'filtro_cama_movil', 'filtro_ventilacion_movil', 'filtro_orden_movil'
    ].forEach(id => {
        const el = document.getElementById(id);
        if (el) el.value = id.includes('orden') ? 'nombre' : '';
    });

    document.querySelectorAll('input[name="filtro_enchufes"][value=""],' +
        'input[name="filtro_enchufes_movil"][value=""]').forEach(r => r.checked = true);

    renderizarCuadricula(ordenarCabanas(todasLasCabanas, 'nombre'));
}

// Toggle desplegable escritorio
btnMasFiltros?.addEventListener('click', (e) => {
    e.stopPropagation();
    const abierto = !panelMasFiltros.hidden;
    panelMasFiltros.hidden = abierto;
    btnMasFiltros.setAttribute('aria-expanded', String(!abierto));
});

btnCerrarFiltros?.addEventListener('click', () => {
    panelMasFiltros.hidden = true;
    btnMasFiltros.setAttribute('aria-expanded', 'false');
});

// Cerrar desplegable al hacer clic fuera
document.addEventListener('click', (e) => {
    if (!panelMasFiltros?.hidden &&
        !panelMasFiltros.contains(e.target) &&
        e.target !== btnMasFiltros) {
        panelMasFiltros.hidden = true;
        btnMasFiltros.setAttribute('aria-expanded', 'false');
    }
});

// Toggle filtros movil
btnToggleFiltros?.addEventListener('click', () => {
    const abierto = !panelFiltrosMovil.hidden;
    panelFiltrosMovil.hidden = abierto;
    btnToggleFiltros.setAttribute('aria-expanded', String(!abierto));
});

// Toggle menu movil cabecera
btnMenuMovil?.addEventListener('click', (e) => {
    e.stopPropagation();
    const abierto = !menuMovil.hidden;
    menuMovil.hidden = abierto;
    btnMenuMovil.setAttribute('aria-expanded', String(!abierto));
});

document.addEventListener('click', (e) => {
    if (!menuMovil?.hidden && !menuMovil.contains(e.target) && e.target !== btnMenuMovil) {
        menuMovil.hidden = true;
        btnMenuMovil.setAttribute('aria-expanded', 'false');
    }
});

// Carga inicial
async function cargarCabanas() {
    mostrarEstado('carga');
    try {
        todasLasCabanas = await api.obtenerCabanas();
        renderizarCuadricula(ordenarCabanas(todasLasCabanas, 'nombre'));
    } catch (error) {
        console.error('Error al cargar cabañas:', error);
        mostrarEstado('error');
    }
}

// Eventos
botonFiltrar?.addEventListener('click', aplicarFiltros);
botonLimpiar?.addEventListener('click', limpiarFiltros);
botonFiltrarMovil?.addEventListener('click', aplicarFiltros);
botonLimpiarMovil?.addEventListener('click', limpiarFiltros);
botonReintentar?.addEventListener('click', cargarCabanas);

// Inicio
cargarCabanas();