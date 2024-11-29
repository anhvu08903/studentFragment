package com.example.mystudent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

class EditStudentFragment : Fragment() {
    private lateinit var nameEditText: EditText
    private lateinit var idEditText: EditText

    private val args: EditStudentFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.edit_student_fragment, container, false)

        // Ánh xạ view
        nameEditText = view.findViewById(R.id.nameEdit)
        idEditText = view.findViewById(R.id.mssvEdit)

        // Hiển thị thông tin sinh viên được truyền vào
        nameEditText.setText(args.studentName)
        idEditText.setText(args.studentId)

        // Xử lý khi bấm nút lưu
        view.findViewById<Button>(R.id.btnEditSave).setOnClickListener {
            val updatedName = nameEditText.text.toString()
            val updatedId = idEditText.text.toString()

            // Trả dữ liệu về danh sách sinh viên
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                "updatedStudent",
                StudentModel(updatedName, updatedId)
            )
            findNavController().popBackStack() // Quay lại màn hình trước
        }

        return view
    }
}
