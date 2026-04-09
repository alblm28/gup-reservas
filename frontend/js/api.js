// api.js — Llamadas al backend
const URL_BASE = 'http://localhost:8080/api';

// Cabanas
const api = {

    obtener_cabanas: async () => {
        const r = await fetch(`${URL_BASE}/cabanas`);
        if (!r.ok) throw new Error('Error al obtener cabañas');
        return r.json();
    },

    obtener_cabana_por_id: async (id) => {
        const r = await fetch(`${URL_BASE}/cabanas/${id}`);
        if (!r.ok) throw new Error('Cabaña no encontrada');
        return r.json();
    },

    obtener_cabanas_por_estado: async (estado) => {
        const r = await fetch(`${URL_BASE}/cabanas/estado/${estado}`);
        if (!r.ok) throw new Error('Error al filtrar por estado');
        return r.json();
    },

    obtener_cabanas_por_capacidad: async (capacidad) => {
        const r = await fetch(`${URL_BASE}/cabanas/capacidad/${capacidad}`);
        if (!r.ok) throw new Error('Error al filtrar por capacidad');
        return r.json();
    },

    // Usuarios
    login: async (datos) => {
        const r = await fetch(`${URL_BASE}/auth/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(datos),
        });
        if (!r.ok) throw new Error('Correo o contraseña incorrectos');
        return r.json();
    },

    registrar: async (datos) => {
        const r = await fetch(`${URL_BASE}/usuarios/registro`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(datos),
        });
        if (!r.ok) throw new Error('No se pudo crear la cuenta');
        return r.json();
    },

    // Reservas
    obtener_mis_reservas: async () => {
        const r = await fetch(`${URL_BASE}/reservas/mis-reservas`, {
            headers: { 'Authorization': `Bearer ${sesion_obtener_token()}` },
        });
        if (!r.ok) throw new Error('Error al obtener reservas');
        return r.json();
    },

    crear_reserva: async (datos) => {
        const r = await fetch(`${URL_BASE}/reservas`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${sesion_obtener_token()}`,
            },
            body: JSON.stringify(datos),
        });
        if (!r.ok) throw new Error('No se pudo crear la reserva');
        return r.json();
    },
};

// Sesion (token JWT, se usara cuando Spring Security este listo)
function sesion_guardar_token(token) {
    localStorage.setItem('gup_token', token);
}

function sesion_obtener_token() {
    return localStorage.getItem('gup_token');
}

function sesion_cerrar() {
    localStorage.removeItem('gup_token');
}

function sesion_esta_activa() {
    return !!sesion_obtener_token();
}