// Referencias
const formulario_registro   = document.getElementById('formulario_registro');
const entrada_nombre        = document.getElementById('nombre');
const entrada_apellido1     = document.getElementById('apellido1');
const entrada_apellido2     = document.getElementById('apellido2');
const entrada_email         = document.getElementById('email');
const entrada_tel           = document.getElementById('tel');
const entrada_contrasenia   = document.getElementById('contrasenia');
const entrada_confirmar     = document.getElementById('confirmar_contrasenia');

const campo_nombre          = document.getElementById('campo_nombre');
const campo_apellido1       = document.getElementById('campo_apellido1');
const campo_apellido2       = document.getElementById('campo_apellido2');
const campo_email           = document.getElementById('campo_email');
const campo_tel             = document.getElementById('campo_tel');
const campo_contrasenia     = document.getElementById('campo_contrasenia');
const campo_confirmar       = document.getElementById('campo_confirmar');

const error_nombre          = document.getElementById('error_nombre');
const error_apellido1       = document.getElementById('error_apellido1');
const error_apellido2       = document.getElementById('error_apellido2');
const error_email           = document.getElementById('error_email');
const error_tel             = document.getElementById('error_tel');
const error_contrasenia     = document.getElementById('error_contrasenia');
const error_confirmar       = document.getElementById('error_confirmar');

const alerta_error          = document.getElementById('alerta_error');
const alerta_error_texto    = document.getElementById('alerta_error_texto');
const alerta_exito          = document.getElementById('alerta_exito');
const alerta_exito_texto    = document.getElementById('alerta_exito_texto');
const boton_registro        = document.getElementById('boton_registro');
const btn_ver_contrasenia   = document.getElementById('btn_ver_contrasenia');
const btn_ver_confirmar     = document.getElementById('btn_ver_confirmar');
const seguridad_contenedor  = document.getElementById('seguridad_contrasenia');
const seguridad_relleno     = document.getElementById('seguridad_relleno');
const seguridad_texto       = document.getElementById('seguridad_texto');
const texto_boton           = boton_registro.querySelector('.auth_boton_texto');
const cargando_boton        = boton_registro.querySelector('.auth_boton_cargando');
const btn_menu_movil        = document.getElementById('btn_menu_movil');
const menu_movil            = document.getElementById('menu_movil');

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

// Ver contrasenia
function alternar_visibilidad(entrada, boton) {
    const es_pass = entrada.type === 'password';
    entrada.type = es_pass ? 'text' : 'password';
    boton.setAttribute('aria-label', es_pass ? 'Ocultar contraseña' : 'Mostrar contraseña');
}

btn_ver_contrasenia.addEventListener('click', () => alternar_visibilidad(entrada_contrasenia, btn_ver_contrasenia));
btn_ver_confirmar.addEventListener('click',   () => alternar_visibilidad(entrada_confirmar, btn_ver_confirmar));

// Seguridad contrasenia
function evaluar_seguridad(valor) {
    let puntos = 0;
    if (valor.length >= 8)               puntos++;
    if (valor.length >= 12)              puntos++;
    if (/[A-Z]/.test(valor))             puntos++;
    if (/[0-9]/.test(valor))             puntos++;
    if (/[^A-Za-z0-9]/.test(valor))      puntos++;
    if (puntos <= 1) return 'debil';
    if (puntos <= 3) return 'media';
    return 'fuerte';
}

const textos_seguridad = { debil: 'Débil', media: 'Media', fuerte: 'Fuerte' };

entrada_contrasenia.addEventListener('input', () => {
    limpiar_error(campo_contrasenia, error_contrasenia);
    const valor = entrada_contrasenia.value;
    if (!valor) { seguridad_contenedor.hidden = true; return; }

    const nivel = evaluar_seguridad(valor);
    seguridad_contenedor.hidden = false;
    seguridad_relleno.className = `seguridad_relleno seguridad_relleno_${nivel}`;
    seguridad_texto.className   = `seguridad_texto seguridad_texto_${nivel}`;
    seguridad_texto.textContent = textos_seguridad[nivel];
});

// Limpiar error campo
function limpiar_error(campo, span_error) {
    campo.classList.remove('campo_error_activo');
    span_error.textContent = '';
}

[
    [entrada_nombre,     campo_nombre,     error_nombre],
    [entrada_apellido1,  campo_apellido1,  error_apellido1],
    [entrada_apellido2,  campo_apellido2,  error_apellido2],
    [entrada_email,      campo_email,      error_email],
    [entrada_tel,        campo_tel,        error_tel],
    [entrada_confirmar,  campo_confirmar,  error_confirmar],
].forEach(([entrada, campo, span]) =>
    entrada.addEventListener('input', () => limpiar_error(campo, span))
);

// Mostrar error campo
function mostrar_error_campo(campo, span_error, mensaje) {
    campo.classList.add('campo_error_activo');
    span_error.textContent = mensaje;
}

// Validar
function validar() {
    let valido = true;

    if (!entrada_nombre.value.trim()) {
        mostrar_error_campo(campo_nombre, error_nombre, 'El nombre es obligatorio.');
        valido = false;
    }
    if (!entrada_apellido1.value.trim()) {
        mostrar_error_campo(campo_apellido1, error_apellido1, 'El primer apellido es obligatorio.');
        valido = false;
    }
    if (!entrada_apellido2.value.trim()) {
        mostrar_error_campo(campo_apellido2, error_apellido2, 'El segundo apellido es obligatorio. Si no tienes, repite el primero.');
        valido = false;
    }

    const email = entrada_email.value.trim();
    if (!email) {
        mostrar_error_campo(campo_email, error_email, 'El correo es obligatorio.');
        valido = false;
    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
        mostrar_error_campo(campo_email, error_email, 'Introduce un correo válido.');
        valido = false;
    }

    const tel = entrada_tel.value.trim();
    if (tel && !/^[\d\s+\-()]{7,15}$/.test(tel)) {
        mostrar_error_campo(campo_tel, error_tel, 'Introduce un teléfono válido.');
        valido = false;
    }

    const contrasenia = entrada_contrasenia.value;
    if (!contrasenia) {
        mostrar_error_campo(campo_contrasenia, error_contrasenia, 'La contraseña es obligatoria.');
        valido = false;
    } else if (contrasenia.length < 8) {
        mostrar_error_campo(campo_contrasenia, error_contrasenia, 'Mínimo 8 caracteres.');
        valido = false;
    }

    if (!entrada_confirmar.value) {
        mostrar_error_campo(campo_confirmar, error_confirmar, 'Confirma tu contraseña.');
        valido = false;
    } else if (entrada_confirmar.value !== contrasenia) {
        mostrar_error_campo(campo_confirmar, error_confirmar, 'Las contraseñas no coinciden.');
        valido = false;
    }

    return valido;
}

// Alertas
function mostrar_alerta_error(mensaje) {
    alerta_exito.hidden = true;
    alerta_error_texto.textContent = mensaje;
    alerta_error.hidden = false;
}

function mostrar_alerta_exito(mensaje) {
    alerta_error.hidden = true;
    alerta_exito_texto.textContent = mensaje;
    alerta_exito.hidden = false;
}

function ocultar_alertas() {
    alerta_error.hidden = true;
    alerta_exito.hidden = true;
}

// Estado boton
function set_cargando(cargando) {
    boton_registro.disabled = cargando;
    texto_boton.hidden      = cargando;
    cargando_boton.hidden   = !cargando;
}

// Submit
formulario_registro.addEventListener('submit', async (e) => {
    e.preventDefault();
    ocultar_alertas();
    if (!validar()) return;
    set_cargando(true);

    try {
        const datos = {
            nombre:      entrada_nombre.value.trim(),
            apellido1:   entrada_apellido1.value.trim(),
            apellido2:   entrada_apellido2.value.trim(),
            email:       entrada_email.value.trim(),
            tel:         entrada_tel.value.trim() || null,
            contrasenia: entrada_contrasenia.value,
        };
        // TODO: api.registrar(datos) cuando el backend este listo
        console.log('Registro:', datos);
        mostrar_alerta_exito('Cuenta creada correctamente. Redirigiendo...');
        setTimeout(() => window.location.href = 'login.html', 2000);
    } catch (error) {
        mostrar_alerta_error(error.message || 'No se pudo crear la cuenta.');
    } finally {
        set_cargando(false);
    }
});