// cabania.js — Detalle de cabana
const pagina_carga      = document.getElementById('pagina_carga');
const pagina_error      = document.getElementById('pagina_error');
const detalle_cabana    = document.getElementById('detalle_cabana');
const btn_menu_movil    = document.getElementById('btn_menu_movil');
const menu_movil        = document.getElementById('menu_movil');

// Menu movil
btn_menu_movil?.addEventListener('click', (e) => {
    e.stopPropagation();
    const abierto = !menu_movil.hidden;
    menu_movil.hidden = abierto;
    btn_menu_movil.setAttribute('aria-expanded', String(!abierto));
});

document.addEventListener('click', (e) => {
    if (!menu_movil?.hidden && !menu_movil.contains(e.target) && e.target !== btn_menu_movil) {
        menu_movil.hidden = true;
        btn_menu_movil.setAttribute('aria-expanded', 'false');
    }
});

// Estado local
let cabana_actual = null;

// Obtener id de la URL
function obtener_id_url() {
    return new URLSearchParams(window.location.search).get('id');
}

// Mostrar estado
function mostrar_estado(cual) {
    pagina_carga.hidden   = cual !== 'carga';
    pagina_error.hidden   = cual !== 'error';
    detalle_cabana.hidden = cual !== 'detalle';
}

// Galeria
function renderizar_galeria(fotos) {
    if (!fotos || fotos.length === 0) return;

    const foto_principal = document.getElementById('foto_principal');
    foto_principal.innerHTML = '';
    const img_principal = document.createElement('img');
    img_principal.src = fotos[0].urlImagen;
    img_principal.alt = `Foto de ${cabana_actual.nombreCabania}`;
    foto_principal.appendChild(img_principal);

    const miniaturas = document.getElementById('galeria_miniaturas');
    fotos.forEach((foto, i) => {
        const miniatura = document.createElement('div');
        miniatura.className = 'galeria_miniatura' + (i === 0 ? ' galeria_miniatura_activa' : '');
        const img = document.createElement('img');
        img.src = foto.urlImagen;
        img.alt = `Foto ${i + 1}`;
        miniatura.appendChild(img);
        miniatura.addEventListener('click', () => cambiar_foto(foto.urlImagen, miniatura));
        miniaturas.appendChild(miniatura);
    });
}

function cambiar_foto(url, miniatura_activa) {
    const img = document.getElementById('foto_principal').querySelector('img');
    if (img) img.src = url;
    document.querySelectorAll('.galeria_miniatura').forEach(m => m.classList.remove('galeria_miniatura_activa'));
    miniatura_activa.classList.add('galeria_miniatura_activa');
}

// Renderizar detalle
function renderizar_detalle(cabana) {
    cabana_actual = cabana;
    document.title = `GUP — ${cabana.nombreCabania}`;

    document.getElementById('miga_nombre').textContent       = cabana.nombreCabania;
    document.getElementById('detalle_nombre').textContent    = cabana.nombreCabania;
    document.getElementById('detalle_descripcion').textContent =
        cabana.descripcion || 'Sin descripción disponible.';
    document.getElementById('detalle_precio').textContent    =
        `${Number(cabana.precioNoche).toFixed(2)} € / noche`;
    document.getElementById('panel_precio').textContent      =
        `${Number(cabana.precioNoche).toFixed(2)} €`;

    const estado_el = document.getElementById('detalle_estado');
    estado_el.textContent = cabana.estado;
    estado_el.className   = `detalle_estado detalle_estado_${cabana.estado}`;

    document.getElementById('car_capacidad').textContent =
        `${cabana.capacidadMax} persona${cabana.capacidadMax !== 1 ? 's' : ''}`;
    document.getElementById('car_cama').textContent      = cabana.cama.replace(/_/g, ' ');
    document.getElementById('car_banios').textContent    =
        `${cabana.numBanios} baño${cabana.numBanios !== 1 ? 's' : ''}`;
    document.getElementById('car_enchufes').textContent  =
        cabana.enchufes != null ? String(cabana.enchufes) : 'No especificado';
    document.getElementById('car_ventilacion').textContent = cabana.ventilacion.replace(/_/g, ' ');

    if (cabana.estado !== 'disponible') {
        document.getElementById('panel_no_disponible').hidden = false;
        document.getElementById('panel_form').hidden          = true;
    } else {
        document.getElementById('num_personas').max = cabana.capacidadMax;
    }

    if (cabana.fotos && cabana.fotos.length > 0) renderizar_galeria(cabana.fotos);

    mostrar_estado('detalle');
}

// Fechas
function set_fechas_minimas() {
    const hoy = new Date().toISOString().split('T')[0];
    document.getElementById('fecha_entrada').min = hoy;
    document.getElementById('fecha_salida').min  = hoy;
}

function calcular_noches(entrada, salida) {
    return Math.floor((new Date(salida) - new Date(entrada)) / 86400000);
}

function actualizar_resumen() {
    const entrada = document.getElementById('fecha_entrada').value;
    const salida  = document.getElementById('fecha_salida').value;
    if (!entrada || !salida || !cabana_actual) { document.getElementById('panel_resumen').hidden = true; return; }

    const noches = calcular_noches(entrada, salida);
    if (noches <= 0) { document.getElementById('panel_resumen').hidden = true; return; }

    const subtotal = noches * Number(cabana_actual.precioNoche);
    document.getElementById('resumen_noches_texto').textContent =
        `${noches} noche${noches !== 1 ? 's' : ''} × ${Number(cabana_actual.precioNoche).toFixed(2)} €`;
    document.getElementById('resumen_subtotal').textContent = `${subtotal.toFixed(2)} €`;
    document.getElementById('resumen_total').textContent    = `${subtotal.toFixed(2)} €`;
    document.getElementById('panel_resumen').hidden         = false;
}

document.getElementById('fecha_entrada').addEventListener('change', () => {
    document.getElementById('fecha_salida').min = document.getElementById('fecha_entrada').value;
    if (document.getElementById('fecha_salida').value <= document.getElementById('fecha_entrada').value)
        document.getElementById('fecha_salida').value = '';
    actualizar_resumen();
});

document.getElementById('fecha_salida').addEventListener('change', actualizar_resumen);

// Boton reservar
document.getElementById('boton_reservar').addEventListener('click', () => {
    const entrada  = document.getElementById('fecha_entrada').value;
    const salida   = document.getElementById('fecha_salida').value;
    const personas = parseInt(document.getElementById('num_personas').value);
    const error_el = document.getElementById('error_personas');

    if (!entrada || !salida) { alert('Selecciona las fechas de entrada y salida.'); return; }
    if (isNaN(personas) || personas < 1) { error_el.textContent = 'Indica el número de personas.'; return; }
    if (personas > cabana_actual.capacidadMax) {
        error_el.textContent = `Capacidad máxima: ${cabana_actual.capacidadMax} personas.`;
        return;
    }
    error_el.textContent = '';

    const params = new URLSearchParams({
        id_cabana: cabana_actual.idCabania,
        entrada,
        salida,
        personas,
    });
    // TODO: si hay sesion activa ir a reserva.html, si no al login
    window.location.href = `login.html?${params.toString()}`;
});

// Carga inicial
async function cargar_cabana() {
    mostrar_estado('carga');
    set_fechas_minimas();
    const id = obtener_id_url();
    if (!id) { mostrar_estado('error'); return; }
    try {
        const cabana = await api.obtener_cabana_por_id(id);
        renderizar_detalle(cabana);
    } catch (error) {
        console.error('Error:', error);
        mostrar_estado('error');
    }
}

cargar_cabana();