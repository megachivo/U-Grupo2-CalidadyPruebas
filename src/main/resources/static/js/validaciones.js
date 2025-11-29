function validarLogin() {
    // Basic check, detailed validation can be here or handled by backend
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    
    if (!email || !password) {
        alert("Todos los campos son obligatorios");
        return false;
    }
    return true;
}

function validarReset() {
    const password = document.getElementById('password').value;
    
    // Requisitos: 5-10 chars, 1 mayúscula, 1 especial
    const lengthValid = password.length >= 5 && password.length <= 10;
    const upperValid = /[A-Z]/.test(password);
    const specialValid = /[!@#&()–[{}]:;',?/*~$^+=<>]/.test(password);
    
    if (!lengthValid) {
        alert("La contraseña debe tener entre 5 y 10 caracteres.");
        return false;
    }
    
    if (!upperValid) {
        alert("La contraseña debe tener al menos una letra mayúscula.");
        return false;
    }
    
    if (!specialValid) {
        alert("La contraseña debe tener al menos un carácter especial.");
        return false;
    }
    
    return true;
}


