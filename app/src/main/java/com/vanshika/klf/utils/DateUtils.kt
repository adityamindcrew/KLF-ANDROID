package com.vanshika.klf.utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.Unit
import kotlin.jvm.functions.Function1

object DateUtils {

    /**
     * Returns the formatted date string.
     * @param date the date to format (nullable)
     * @param forApi whether to format for API ("yyyy-MM-dd") or UI ("dd MMM yyyy")
     * @return formatted date string or null if date is null
     */
    fun getFormattedDate(date: Date?, forApi: Boolean = false): String? {
        if (date == null) return null
        val format = if (forApi) "yyyy-MM-dd" else "dd MMM yyyy"
        return SimpleDateFormat(format, Locale.getDefault()).format(date)
    }

    /**
     * Shows a DatePickerDialog for selecting a date.
     * @param context the context to create dialog
     * @param isStartDate whether the date being picked is a start date
     * @param selectedStartDate the currently selected start date (nullable)
     * @param selectedEndDate the currently selected end date (nullable)
     * @param onDateSelected callback invoked with the chosen date
     */
    fun showDatePicker(
        context: Context,
        isStartDate: Boolean,
        selectedStartDate: Date?,
        selectedEndDate: Date?,
        onDateSelected: (Date) -> Unit
    ) {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val selectedCalendar = Calendar.getInstance().apply {
                    set(year, month, dayOfMonth)
                }
                val selectedDate = selectedCalendar.time

                if (isStartDate) {
                    if (selectedEndDate != null && selectedDate.after(selectedEndDate)) {
                        Toast.makeText(context, "Start Date cannot be after End Date", Toast.LENGTH_SHORT).show()
                        return@DatePickerDialog
                    }
                } else {
                    if (selectedStartDate != null && selectedDate.before(selectedStartDate)) {
                        Toast.makeText(context, "End Date must be after Start Date", Toast.LENGTH_SHORT).show()
                        return@DatePickerDialog
                    }
                }
                onDateSelected(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }
}
