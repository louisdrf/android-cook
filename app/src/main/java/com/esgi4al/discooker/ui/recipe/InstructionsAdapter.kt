package com.esgi4al.discooker.ui.recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.esgi4al.discooker.R
import com.esgi4al.discooker.models.Instruction

class InstructionsAdapter(private var instructions: List<Instruction>, private val onDelete: (Instruction) -> Unit) : RecyclerView.Adapter<InstructionsAdapter.InstructionViewHolder>() {

    class InstructionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val instructionTextView: TextView = view.findViewById(R.id.instruction_text_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_instruction, parent, false)
        return InstructionViewHolder(view)
    }

    override fun onBindViewHolder(holder: InstructionViewHolder, position: Int) {
        val instruction = instructions[position]
        holder.instructionTextView.text = instruction.instruction

        holder.itemView.setOnLongClickListener {
            onDelete(instruction)
            true
        }
    }

    override fun getItemCount() = instructions.size

}
