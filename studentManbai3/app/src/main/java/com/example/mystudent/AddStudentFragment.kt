package com.example.mystudent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class AddStudentFragment : Fragment() {
    private lateinit var nameEditText: EditText
    private lateinit var idEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_add, container, false)
        nameEditText = view.findViewById(R.id.name)
        idEditText = view.findViewById(R.id.mssv)

        view.findViewById<Button>(R.id.btnSave).setOnClickListener {
            val name = nameEditText.text.toString()
            val id = idEditText.text.toString()
            // Thêm sinh viên vào danh sách (có thể dùng ViewModel hoặc Adapter để thêm)
            // Quay lại fragment danh sách sinh viên
            fragmentManager?.popBackStack()
        }

        return view
    }
}