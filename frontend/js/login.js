// Referencias
const formulario_login      = document.getElementById('formulario_login');
const entrada_email         = document.getElementById('email');
const entrada_contrasenia   = document.getElementById('contrasenia');
const campo_email           = document.getElementById('campo_email');
const campo_contrasenia     = document.getElementById('campo_contrasenia');
const error_email           = document.getElementById('error_email');
const error_contrasenia     = document.getElementById('error_contrasenia');
const alerta_error          = document.getElementById('alerta_error');
const alerta_error_texto    = document.getElementById('alerta_error_texto');
const boton_login           = document.getElementById('boton_login');
const btn_ver_contrasenia   = document.getElementById('btn_ver_contrasenia');
const texto_boton           = boton_login.querySelector('.auth_boton_texto');
const cargando_boton        = boton_login.querySelector('.auth_boton_cargando');
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
btn_ver_contrasenia.addEventListener('click', () => {
    const es_password = entrada_contrasenia.type === 'password';
    entrada_contrasenia.type = es_password ? 'text' : 'password';
    btn_ver_contrasenia.setAttribute('aria-label', es_password ? 'Ocultar contraseña' : 'Mostrar contraseña');
});

// Limpiar error de campo
function limpiar_error(campo, span_error) {
    campo.classList.remove('campo_error_activo');
    span_error.textContent = '';
}

entrada_email.addEventListener('input', () => limpiar_error(campo_email, error_email));
entrada_contrasenia.addEventListener('input', () => limpiar_error(campo_contrasenia, error_contrasenia));

// Mostrar error de campo
function mostrar_error_campo(campo, span_error, mensaje) {
    campo.classList.add('campo_error_activo');
    span_error.textContent = mensaje;
}

// Validar
function validar() {
    let valido = true;
    const email = entrada_email.value.trim();

    if (!email) {
        mostrar_error_campo(campo_email, error_email, 'El correo es obligatorio.');
        valido = false;
    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
        mostrar_error_campo(campo_email, error_email, 'Introduce un correo válido.');
        valido = false;
    }

    if (!entrada_contrasenia.value) {
        mostrar_error_campo(campo_contrasenia, error_contrasenia, 'La contraseña es obligatoria.');
        valido = false;
    }

    return valido;
}

// Alerta global
function mostrar_alerta(mensaje) {
    alerta_error_texto.textContent = mensaje;
    alerta_error.hidden = false;
}

function ocultar_alerta() {
    alerta_error.hidden = true;
}

// Estado boton
function set_cargando(cargando) {
    boton_login.disabled  = cargando;
    texto_boton.hidden    = cargando;
    cargando_boton.hidden = !cargando;
}

// Submit
formulario_login.addEventListener('submit', async (e) => {
    e.preventDefault();
    ocultar_alerta();
    if (!validar()) return;
    set_cargando(true);

    try {
        const datos = {
            email:      entrada_email.value.trim(),
            contrasenia: entrada_contrasenia.value,
        };
        // TODO: api.login(datos) cuando Spring Security este listo
        console.log('Login:', datos);
        alert('Login pendiente de implementar en el backend.');
    } catch (error) {
        mostrar_alerta(error.message || 'Correo o contraseña incorrectos.');
    } finally {
        set_cargando(false);
    }
});