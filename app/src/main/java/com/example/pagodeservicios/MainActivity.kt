package com.example.pagodeservicios

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pagodeservicios.ui.theme.PagoDeServiciosTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField

data class Payment(
    val service: String,
    val amount: String,
    val date: String,
    val name: String,
    val email: String,
    val phone: String
)

enum class Screen {
    Welcome,
    Payment,
    History
}

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PagoDeServiciosTheme {
                var currentScreen by remember { mutableStateOf(Screen.Welcome) }
                var payments by remember { mutableStateOf(listOf<Payment>()) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        if (currentScreen == Screen.Payment) {
                            TopAppBar(
                                title = { Text("Pago de Servicios") }
                            )
                        } else if (currentScreen == Screen.History) {
                            TopAppBar(
                                title = { Text("Historial de Pagos") }
                            )
                        }
                    }
                ) { innerPadding ->
                    when (currentScreen) {
                        Screen.Welcome -> WelcomeScreen(
                            modifier = Modifier.padding(innerPadding),
                            onContinue = { currentScreen = Screen.Payment }
                        )
                        Screen.Payment -> PagoScreen(
                            modifier = Modifier.padding(innerPadding),
                            onSavePayment = { payment -> payments = payments + payment },
                            onNavigateToHistory = { currentScreen = Screen.History },
                            onBackToWelcome = { currentScreen = Screen.Welcome }
                        )
                        Screen.History -> HistoryScreen(
                            modifier = Modifier.padding(innerPadding),
                            payments = payments,
                            onBack = { currentScreen = Screen.Payment }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PagoScreen(modifier: Modifier = Modifier, onSavePayment: (Payment) -> Unit, onNavigateToHistory: () -> Unit, onBackToWelcome: () -> Unit) {
    var selectedService by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var isSaved by remember { mutableStateOf(false) }
    var showErrors by remember { mutableStateOf(false) }

    Card(modifier = modifier.padding(16.dp)) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = onBackToWelcome, modifier = Modifier.align(Alignment.Start)) {
                Text("Volver al inicio")
            }

            // Service selector
            val services = listOf("Agua", "Luz", "Internet", "Cable")
            var expanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                OutlinedTextField(
                    value = selectedService,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Servicio") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Build,
                            contentDescription = "Servicio"
                        )
                    },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    isError = showErrors && selectedService.isEmpty(),
                    supportingText = {
                        if (showErrors && selectedService.isEmpty()) {
                            Text("Campo requerido")
                        }
                    }
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    services.forEach { service ->
                        DropdownMenuItem(
                            text = { Text(service) },
                            onClick = {
                                selectedService = service
                                expanded = false
                            }
                        )
                    }
                }
            }

            // Amount
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Monto pagado") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.AttachMoney,
                        contentDescription = "Monto"
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                isError = showErrors && amount.isEmpty(),
                supportingText = {
                    if (showErrors && amount.isEmpty()) {
                        Text("Campo requerido")
                    }
                }
            )

            // Date
            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                label = { Text("Fecha de pago") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = "Fecha"
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                isError = showErrors && date.isEmpty(),
                supportingText = {
                    if (showErrors && date.isEmpty()) {
                        Text("Campo requerido")
                    }
                }
            )

            // Personal Information
            Text("Información Personal", style = MaterialTheme.typography.titleMedium)
            HorizontalDivider()
            var name by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }
            var phone by remember { mutableStateOf("") }

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre completo") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Nombre"
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                isError = showErrors && name.isEmpty(),
                supportingText = {
                    if (showErrors && name.isEmpty()) {
                        Text("Campo requerido")
                    }
                }
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = "Correo"
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                isError = showErrors && email.isEmpty(),
                supportingText = {
                    if (showErrors && email.isEmpty()) {
                        Text("Campo requerido")
                    }
                }
            )

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Teléfono") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Phone,
                        contentDescription = "Teléfono"
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                isError = showErrors && phone.isEmpty(),
                supportingText = {
                    if (showErrors && phone.isEmpty()) {
                        Text("Campo requerido")
                    }
                }
            )

            // Button
            Button(
                onClick = {
                    if (selectedService.isNotEmpty() && amount.isNotEmpty() && date.isNotEmpty() && name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty()) {
                        isSaved = true
                        showErrors = false
                        onSavePayment(Payment(selectedService, amount, date, name, email, phone))
                        onNavigateToHistory()
                    } else {
                        showErrors = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar")
            }

            // Message and Card
            if (isSaved) {
                Text("Pago registrado exitosamente", color = MaterialTheme.colorScheme.primary)
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Resumen del pago", style = MaterialTheme.typography.titleMedium)
                        Text("Servicio: $selectedService")
                        Text("Monto: $amount")
                        Text("Fecha: $date")
                        Text("Nombre: $name")
                        Text("Correo: $email")
                        Text("Teléfono: $phone")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(modifier: Modifier = Modifier, onContinue: () -> Unit) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bienvenidos",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Paga tus servicios con nosotros",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Button(onClick = onContinue) {
            Text("Continuar")
        }
        Button(onClick = { /* Salir de la app */ }) {
            Text("Salir")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(modifier: Modifier = Modifier, payments: List<Payment>, onBack: () -> Unit) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Historial de Pagos",
            style = MaterialTheme.typography.headlineMedium
        )
        Button(onClick = onBack) {
            Text("Volver a la página de pago")
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(payments) { payment ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Servicio: ${payment.service}")
                        Text("Monto: ${payment.amount}")
                        Text("Fecha: ${payment.date}")
                        Text("Nombre: ${payment.name}")
                        Text("Correo: ${payment.email}")
                        Text("Teléfono: ${payment.phone}")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PagoScreenPreview() {
    PagoDeServiciosTheme {
        PagoScreen(modifier = Modifier, onSavePayment = {}, onNavigateToHistory = {}, onBackToWelcome = {})
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    PagoDeServiciosTheme {
        WelcomeScreen(onContinue = {})
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryScreenPreview() {
    PagoDeServiciosTheme {
        HistoryScreen(modifier = Modifier, payments = listOf(), onBack = {})
    }
}
