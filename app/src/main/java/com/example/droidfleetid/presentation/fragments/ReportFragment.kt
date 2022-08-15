package com.example.droidfleetid.presentation.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.droidfleetid.R
import com.example.droidfleetid.databinding.FragmentReportBinding
import com.example.droidfleetid.presentation.DroidFleetViewModel
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat
import java.util.*

class ReportFragment : Fragment() {


    private var _binding: FragmentReportBinding? = null
    private val binding: FragmentReportBinding
        get() = _binding ?: throw RuntimeException("LoginFragment == null")
    private var calendarBegin: Calendar = Calendar.getInstance()
    private var calendarEnd: Calendar = Calendar.getInstance()

    private val viewModel: DroidFleetViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

      _binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val myFormat = "dd.MM.yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        super.onViewCreated(view, savedInstanceState)
        calendarBegin.set(Calendar.HOUR, 0)
        calendarBegin.set(Calendar.MINUTE, 0)
        calendarBegin.set(Calendar.SECOND, 0)
        calendarBegin.set(Calendar.HOUR_OF_DAY,0)
        binding.editTextEnd.text = sdf.format(calendarBegin.time)
        binding.editTextBegin.text = sdf.format(calendarBegin.time)
        calendarEnd.apply {
            set(Calendar.HOUR,23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND,59)
            set(Calendar.HOUR_OF_DAY,23)
        }
        binding.deviceTextview.text
        binding.reportButton.setOnClickListener {
        }

        viewModel.calendarBegin.observe(viewLifecycleOwner){
            calendarBegin = it
            binding.timeBegin.text = SimpleDateFormat("HH:mm").format(calendarBegin.time)
            binding.editTextBegin.text = sdf.format(calendarBegin.time)
        }
        viewModel.calendarEnd.observe(viewLifecycleOwner){
            calendarEnd = it
            binding.timeEnd.text = SimpleDateFormat("HH:mm").format(calendarEnd.time)
            binding.editTextEnd.text = sdf.format(calendarEnd.time)

        }
        viewModel.selectedReportDevice.observe(viewLifecycleOwner){
            binding.deviceTextview.text = "${it.number} ${it.model}"
        }
        binding.reportButton.setOnClickListener {
               lifecycleScope.launch{
                   val device = viewModel.selectedReportDevice.value?.imei
                   val accountId = viewModel.selectedReportDevice.value?.account_id
                   val dateFrоm = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",Locale.US).format(calendarBegin.time)
                   val dateTo = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",Locale.US).format(calendarEnd.time)
                   Log.d("test", "$device $accountId $dateFrоm $dateTo")

                   try {
                       if (device != null&&accountId!=null ) {
                           viewModel.getTrack(device,accountId, dateFrоm,dateTo,"")
                       }
                   }catch (e: HttpException){
                       val body = e.response()?.errorBody()
                       val gson = Gson()
                       val adapter: TypeAdapter<MyError> = gson.getAdapter(MyError::class.java)
                       try {
                           val error: MyError = adapter.fromJson(body?.string())
                           Log.d("test", " code = " + error.code + " message = " + error.message)
                       } catch (e: IOException) {
                           Log.d("test", " Error in parsing")
                       }
                   }
               }

        }

        binding.deviceTextview.setOnClickListener {
            findNavController().navigate(R.id.action_reportFragment_to_fragmentReportDeviceList)
        }
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
                calendarEnd.set(Calendar.HOUR_OF_DAY, hour)
                calendarEnd.set(Calendar.MINUTE, minute)

                binding.timeEnd.text = SimpleDateFormat("HH:mm").format(calendarEnd.time)
            }
            TimePickerDialog(context,
                timeSetListener,
                calendarEnd.get(Calendar.HOUR_OF_DAY),
                calendarEnd.get(Calendar.MINUTE), true).show()

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

    override fun onPause() {
        super.onPause()
        viewModel.saveCalendar(calendarBegin,calendarEnd)
    }
}

data class MyError(
    @SerializedName("code")
    val code: String,
    @SerializedName("message")
    val message: String
)
