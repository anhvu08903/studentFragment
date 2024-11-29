package com.example.mystudent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
  private val studentList: MutableList<StudentModel>,
  private val onEditClick: (StudentModel) -> Unit, // Điều hướng tới EditStudentFragment
  private val onRemoveClick: (Int) -> Unit       // Xử lý xóa sinh viên theo vị trí
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.layout_student_item, parent, false)
    return StudentViewHolder(view)
  }

  override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
    val student = studentList[position]
    holder.name.text = student.studentName
    holder.studentId.text = student.studentId

    // Sự kiện click vào biểu tượng edit
    holder.imageEdit.setOnClickListener {
      onEditClick(student)
    }

    // Sự kiện click vào biểu tượng remove
    holder.imageRemove.setOnClickListener {
      onRemoveClick(holder.adapterPosition)
    }
  }

  override fun getItemCount(): Int = studentList.size

  inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.text_student_name)
    val studentId: TextView = itemView.findViewById(R.id.text_student_id)
    val imageEdit: ImageView = itemView.findViewById(R.id.image_edit) // Tham chiếu icon edit
    val imageRemove: ImageView = itemView.findViewById(R.id.image_remove) // Tham chiếu icon remove
  }

  // Hàm thêm sinh viên mới
  fun addStudent(student: StudentModel) {
    studentList.add(student)
    notifyItemInserted(studentList.size - 1)
  }

  // Hàm cập nhật thông tin sinh viên
  fun updateStudent(updatedStudent: StudentModel) {
    val index = studentList.indexOfFirst { it.studentId == updatedStudent.studentId }
    if (index != -1) {
      studentList[index] = updatedStudent
      notifyItemChanged(index)
    }
  }

  // Hàm xóa sinh viên (theo vị trí)
  fun removeStudentAt(position: Int) {
    if (position in studentList.indices) {
      studentList.removeAt(position)
      notifyItemRemoved(position)
    }
  }
}
