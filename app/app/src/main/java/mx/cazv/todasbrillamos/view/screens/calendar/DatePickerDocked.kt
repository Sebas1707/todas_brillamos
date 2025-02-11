package mx.cazv.todasbrillamos.view.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.cazv.todasbrillamos.model.CalendarModel
import mx.cazv.todasbrillamos.ui.theme.AccentColor
import mx.cazv.todasbrillamos.viewmodel.CalendarVM
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/**
 * Composable que muestra un campo de texto con un botón para seleccionar una fecha
 * Permite que el usuario seleccione una fecha y la muestra en el campo de texto que representa el
 * inicio de su periodo menstrual paasdo.
 * @author Min Che Kim
 *
 * @param text El texto a mostrar en el campo de texto.
 * @param calendarVM El ViewModel del calendario.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDocked(text: String = "", calendarVM: CalendarVM = viewModel()) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        SimpleDateFormat("dd/MM/yyyy", Locale.US).apply{
            timeZone = TimeZone.getTimeZone("UTC")
        }.format(Date(it))
    } ?: ""


    Row (modifier = Modifier) {

        MinimalDialog(text,
            Modifier
                .weight(0.5f)
                .align(Alignment.CenterVertically))

        Column(
            modifier = Modifier
                .weight(10f)
                .padding(start = 20.dp, end = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedDate,
                    onValueChange = { },
                    label = {
                        Text(
                            "¿Cuándo comenzó tu último periodo?",
                            fontSize = 14.sp
                        )
                    },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = !showDatePicker }) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Select date",
                                tint = AccentColor
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
//                        .height(64.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .clickable {  showDatePicker = true  }
                )

//                if (showDatePicker) {
//                    Popup(
//                        onDismissRequest = { showDatePicker = false },
//                        alignment = Alignment.TopStart
//                    ) {
//                        Box(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .offset(y = 64.dp)
//                                .shadow(elevation = 4.dp)
//                                .background(MaterialTheme.colorScheme.surface)
//                                .padding(16.dp)
//                        ) {
//                            DatePicker(
//                                state = datePickerState,
//                                showModeToggle = false
//                            )
//                        }
//                    }
//                }

                if (showDatePicker) {
                    DatePickerDialog(
                        onDismissRequest = { showDatePicker = false },
                        confirmButton = {
                            TextButton(onClick = { datePickerState.selectedDateMillis?.let {
                                Date(it)
                                calendarVM.updateSelectedDate(it) } ?: ""
                                showDatePicker = false
                            }) {
                                Text("Seleccionar")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDatePicker = false } ) {
                                Text("Cancelar")
                            }
                        }
                    ) {
                        DatePicker(
                            state = datePickerState,
                            showModeToggle = false
                        )
                    }
                }
            }
        }
    }
}