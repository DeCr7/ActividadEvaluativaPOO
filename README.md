# 📱 Pago de Servicios

Aplicación móvil desarrollada en **Kotlin con Jetpack Compose**, que permite registrar y visualizar pagos de servicios básicos como agua, luz, internet y cable.

---

## 🎯 Objetivo

Diseñar una interfaz moderna y funcional aplicando los principios de **Material Design 3**, como parte de una actividad evaluativa.

---

## 🧩 Funcionalidades

* Selección de servicio (Agua, Luz, Internet, Cable)
* Ingreso de monto pagado
* Registro de fecha de pago
* Formulario de información personal
* Validación de campos obligatorios
* Mensaje dinámico de confirmación
* Visualización de resumen del pago
* Historial de pagos registrados
* Navegación entre pantallas

---

## 🖼️ Pantallas

### 🟢 Pantalla de Bienvenida

* Mensaje inicial
* Botón para continuar

### 💳 Pantalla de Pago

* Selector de servicio
* Campos de entrada (monto, fecha, datos personales)
* Validaciones con mensajes de error
* Botón para guardar
* Resumen del pago en tarjeta (Card)

### 📜 Historial

* Lista de pagos registrados
* Uso de LazyColumn
* Visualización en Cards

---

## 🛠️ Tecnologías utilizadas

* Kotlin
* Jetpack Compose
* Material Design 3
* Android Studio

---

## 🧠 Conceptos aplicados

* Uso de `@Composable`
* Manejo de estado con `remember`
* Navegación entre pantallas (control manual)
* Componentes de Material 3 (`Scaffold`, `Card`, `TopAppBar`, etc.)
* Validación de formularios
* Diseño responsivo con `Modifier`

---

## 🎨 Decisiones de diseño

* Se utilizó **MaterialTheme** para mantener consistencia visual.
* Se implementaron **íconos representativos** para mejorar la usabilidad.
* Se añadieron validaciones para mejorar la experiencia del usuario.
* Se incluyó un historial para extender la funcionalidad base.
* Se organizó la interfaz en tarjetas (`Card`) para claridad visual.

---

## 👤 Autores

* Richard Bernard Castro Fonseca
* Dennis Amaru Cruz Abrego

---

## 📌 Notas

Este proyecto fue desarrollado con fines académicos como parte de una actividad evaluativa sobre diseño de interfaces con Material Design 3.