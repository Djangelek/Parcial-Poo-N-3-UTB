## Descripción del Repositorio - Sistema Estadístico de Clasificación de Notas Escolares
Este repositorio contiene el código fuente y los recursos necesarios para implementar un sistema estadístico de clasificación de notas para una escuela primaria utilizando JavaFX y SQLite.

El sistema está diseñado para ayudar a los profesores a analizar y visualizar el desempeño de los estudiantes en los exámenes, y utiliza las siguientes Librerias:

JavaFX: Se utiliza para desarrollar la interfaz de usuario del sistema, incluyendo las ventanas de inicio de sesión, registro y registro de estudiantes.
SQLite: Se utiliza como la base de datos del sistema, donde se almacenan los datos de los usuarios, estudiantes y notas.

Características principales del sistema:

Ventana de Inicio de Sesión: Permite a los usuarios iniciar sesión proporcionando un usuario o email y una contraseña. El sistema valida los datos ingresados en la base de datos y muestra un cuadro de diálogo informando si el inicio de sesión fue exitoso. Si los datos son incorrectos, el sistema redirecciona al usuario a la ventana de registro y muestra un mensaje de error.

Ventana de Registro: Permite a los usuarios crear una cuenta proporcionando un usuario o email y una contraseña. El sistema verifica que la contraseña y la verificación de contraseña coincidan, que el usuario no exceda los 20 caracteres y que sea único. Si alguna de estas condiciones no se cumple, se muestra un mensaje de error y no se permite el registro.

Ventana de Registro de Estudiantes: Permite a los usuarios ingresar los datos de los estudiantes, incluyendo su ID, nombre, género y las notas obtenidas en informática, física y química. El sistema valida los campos y agrega los datos del estudiante a la base de datos. Se muestran mensajes de error si los datos no cumplen con las restricciones especificadas.

Se busca mostrar:
Promedio de notas de cada materia del grupo.
Porcentaje de notas Excelentes en informática.
Porcentaje de notas Regulares en física.
Porcentaje de notas Deficientes en química.

El repositorio incluye el código fuente, los archivos de configuración necesarios y los recursos gráficos utilizados en el desarrollo de la interfaz de usuario.


Este repositorio se basa en el trabajo previo del ING. Luis Fernando Mendoza @LuisMendozaDev

#   P r o y e c t o _ F i n a l 
