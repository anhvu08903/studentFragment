package com.example.mystudent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class StudentListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var studentAdapter: StudentAdapter
    private val studentList = mutableListOf(
        StudentModel("Nguyễn Văn An", "SV001"),
        StudentModel("Trần Thị Bảo", "SV002"),
        StudentModel("Lê Hoàng Cường", "SV003"),
        StudentModel("Phạm Thị Dung", "SV004"),
        StudentModel("Đỗ Minh Đức", "SV005"),
        StudentModel("Vũ Thị Hoa", "SV006"),
        StudentModel("Hoàng Văn Hải", "SV007"),
        StudentModel("Bùi Thị Hạnh", "SV008"),
        StudentModel("Đinh Văn Hùng", "SV009"),
        StudentModel("Nguyễn Thị Linh", "SV010"),
        StudentModel("Phạm Văn Long", "SV011"),
        StudentModel("Trần Thị Mai", "SV012"),
        StudentModel("Lê Thị Ngọc", "SV013"),
        StudentModel("Vũ Văn Nam", "SV014"),
        StudentModel("Hoàng Thị Phương", "SV015"),
        StudentModel("Đỗ Văn Quân", "SV016"),
        StudentModel("Nguyễn Thị Thu", "SV017"),
        StudentModel("Trần Văn Tài", "SV018"),
        StudentModel("Phạm Thị Tuyết", "SV019"),
        StudentModel("Lê Văn Vũ", "SV020")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // Đảm bảo menu được kích hoạt
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.list_students, container, false)

        recyclerView = view.findViewById(R.id.recycler_view_students)
        studentAdapter = StudentAdapter(
            studentList,
            onEditClick = { student ->
                val action = StudentListFragmentDirections
                    .actionStudentListFragmentToEditStudentFragment(student.studentName, student.studentId)
                findNavController().navigate(action)
            },
            onRemoveClick = { position ->
                if (position in studentList.indices) {
                    studentList.removeAt(position)
                    studentAdapter.notifyItemRemoved(position)
                }
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = studentAdapter

        // Lắng nghe dữ liệu trả về từ AddStudentFragment hoặc EditStudentFragment
        findNavController().currentBackStackEntry?.savedStateHandle?.apply {
            // Nhận sinh viên mới từ AddStudentFragment
            getLiveData<StudentModel>("newStudent").observe(viewLifecycleOwner) { newStudent ->
                addStudent(newStudent)
            }

            // Nhận sinh viên được cập nhật từ EditStudentFragment
            getLiveData<StudentModel>("updatedStudent").observe(viewLifecycleOwner) { updatedStudent ->
                updateStudent(updatedStudent)
            }
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add_new -> {
                // Điều hướng đến AddStudentFragment
                findNavController().navigate(R.id.action_studentListFragment_to_addStudentFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addStudent(student: StudentModel) {
        studentList.add(student)
        studentAdapter.notifyItemInserted(studentList.size - 1)
    }

    private fun updateStudent(updatedStudent: StudentModel) {
        val index = studentList.indexOfFirst { it.studentId == updatedStudent.studentId }
        if (index != -1) {
            studentList[index] = updatedStudent
            studentAdapter.notifyItemChanged(index)
        }
    }
}

