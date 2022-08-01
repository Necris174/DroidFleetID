package com.example.droidfleetid.presentation.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.droidfleetid.R
import com.example.droidfleetid.databinding.FragmentReportBinding
import java.text.SimpleDateFormat
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReportFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReportFragment : Fragment() {


    private var _binding: FragmentReportBinding? = null
    private val binding: FragmentReportBinding
        get() = _binding ?: throw RuntimeException("LoginFragment == null")
    private var calendarBegin: Calendar = Calendar.getInstance()
    private var calendarEnd: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
      _binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.deviceTextview.setOnClickListener {
            findNavController().navigate(R.id.action_reportFragment_to_fragmentReportDeviceList)
        }

        val myFormat = "dd.MM.yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.editTextEnd.text = sdf.format(calendarBegin.time)
        binding.editTextBegin.text = sdf.format(calendarBegin.time)

        binding.timeBegin.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                calendarBegin.set(Calendar.HOUR_OF_DAY, hour)
                calendarBegin.set(Calendar.MINUTE, minute)

                binding.timeBegin.text = SimpleDateFormat("HH:mm").format(calendarBegin.time)
            }
            TimePickerDialog(context,
                timeSetListener,
                calendarBegin.get(Calendar.HOUR_OF_DAY),
                calendarBegin.get(Calendar.MINUTE), true).show()

        }

        binding.timeEnd.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                calendarBegin.set(Calendar.HOUR_OF_DAY, hour)
                calendarBegin.set(Calendar.MINUTE, minute)

                binding.timeEnd.text = SimpleDateFormat("HH:mm").format(calendarBegin.time)
            }
            TimePickerDialog(context,
                timeSetListener,
                calendarBegin.get(Calendar.HOUR_OF_DAY),
                calendarBegin.get(Calendar.MINUTE), true).show()

        }




        binding.editTextBegin.setOnClickListener{
            val dataPicker =   DatePickerDialog(requireActivity(), { _, year, monthOfYear, dayOfMonth ->
                calendarBegin.set(Calendar.YEAR, year)
                calendarBegin.set(Calendar.MONTH, monthOfYear)
                calendarBegin.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                if (calendarBegin.time.time>calendarEnd.time.time){
                    binding.editTextEnd.text = sdf.format(calendarBegin.time)
                }
                binding.editTextBegin.text = sdf.format(calendarBegin.time)
            },
                calendarBegin.get(Calendar.YEAR),
                calendarBegin.get(Calendar.MONTH),
                calendarBegin.get(Calendar.DAY_OF_MONTH))

            dataPicker.datePicker.maxDate = System.currentTimeMillis()
            dataPicker.show()
        }

        binding.editTextEnd.setOnClickListener{
           val dataPicker =  DatePickerDialog(requireActivity(), { _, year, monthOfYear, dayOfMonth ->
               calendarEnd.set(Calendar.YEAR, year)
               calendarEnd.set(Calendar.MONTH, monthOfYear)
               calendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth)

               binding.editTextEnd.text = sdf.format(calendarEnd.time)
            },
               calendarEnd.get(Calendar.YEAR),
               calendarEnd.get(Calendar.MONTH),
               calendarEnd.get(Calendar.DAY_OF_MONTH))
            dataPicker.datePicker.maxDate = System.currentTimeMillis()
            dataPicker.datePicker.minDate = calendarBegin.time.time
            dataPicker.show()
        }





        }

    }

