// EchoInk.kt
// Reiden Johnson
// Simple Note-Taking App in Kotlin
// Demonstrates basic Kotlin concepts: variables, data classes, functions, classes, loops, conditionals, collections, and user input


// A data class to represent a note
// It has a title (cannot change), content (can change), and a category (default "General")
data class Note(val title: String, var content: String, val category: String = "General") {
    // Converts the note into a readable multi-line string for printing
    override fun toString(): String {
        return "Title: $title\nContent: $content\nCategory: $category"
    }
}

// Class to manage notes
class NoteManager {
    // A mutable list to store multiple notes
    private val notes: MutableList<Note> = mutableListOf()

    // Function to add a new note
    fun addNote() {
        println("Enter note title:") // Ask user for the note title
        val title = readlnOrNull()?.trim() ?: "" // Read input, remove spaces, default to empty
        if (title.isEmpty()) { // Check if user entered nothing
            println("Title cannot be empty!")
            return // Stop adding if title is empty
        }

        println("Enter note content:") // Ask for the note content
        val content = readlnOrNull()?.trim() ?: "" // Read content, remove spaces, default empty

        println("Enter note category (optional, press enter for 'General')") // Ask for category
        val categoryInput = readlnOrNull()?.trim() // Read category
        val category = if (categoryInput.isNullOrEmpty()) "General" else categoryInput // Default to General

        notes.add(Note(title, content, category)) // Create a Note object and add to list
        println("Note added successfully!") // Confirmation message
    }

    // Function to view all notes
    fun viewNotes() {
        if (notes.isEmpty()) { // Check if the list is empty
            println("No notes available.") // Inform user if empty
            return // Stop function
        }
        println("Your Notes:") // Header
        for ((index, note) in notes.withIndex()) { // Loop through list with index
            println("${index + 1}. ${note.toString()}") // Print each note with a number
        }
    }

    // Function to edit a note
    fun editNote() {
        if (notes.isEmpty()) { // Check if there are no notes
            println("No notes to edit.")
            return // Stop function
        }

        viewNotes() // Show current notes to the user
        println("Enter the number of the note to edit:") // Ask for which note
        val index = (readlnOrNull()?.toIntOrNull() ?: 0) - 1 // Convert to index (1-based to 0-based)

        if (index !in notes.indices) { // Validate the index
            println("Invalid index!")
            return // Stop if invalid
        }

        println("Enter new content for '${notes[index].title}':") // Ask for new content
        val newContent = readlnOrNull()?.trim() ?: "" // Read new content
        notes[index] = notes[index].copy(content = newContent) // Update note with new content
        println("Note updated successfully!") // Confirmation
    }

    // Function to delete a note
    fun deleteNote() {
        if (notes.isEmpty()) { // Check if there are notes to delete
            println("No notes to delete.")
            return // Stop if empty
        }

        viewNotes() // Show notes to choose from
        println("Enter the number of the note to delete:") // Ask which note to delete
        val index = (readlnOrNull()?.toIntOrNull() ?: 0) - 1 // Convert input to 0-based index

        if (index !in notes.indices) { // Validate the index
            println("Invalid index!")
            return // Stop if invalid
        }

        println("Are you sure you want to delete '${notes[index].title}'? (y/n)") // Confirm deletion
        val confirm = readlnOrNull()?.trim()?.lowercase() // Read confirmation
        if (confirm != "y") { // If user does not type 'y'
            println("Deletion canceled.") // Stop deletion
            return
        }

        notes.removeAt(index) // Remove the note from the list
        println("Note deleted successfully!") // Confirmation
    }
}

// Main function: program starts here
fun main() {
    val manager = NoteManager() // Create NoteManager object to handle notes

    println("=== Welcome to the Kotlin Note-Taking App ===") // Welcome message

    // Loop to repeatedly show menu until user exits
    while (true) {
        println("\nMenu Options:") // Print menu
        println("1. Add Note")
        println("2. View Notes")
        println("3. Edit Note")
        println("4. Delete Note")
        println("5. Exit")
        print("Choose an option (1-5): ") // Prompt user for choice

        val choice = readlnOrNull()?.toIntOrNull() // Read input safely as integer
        val message = when (choice) { // Decide what to do based on input
            1 -> { manager.addNote(); "Added a note" } // Call addNote
            2 -> { manager.viewNotes(); "Here are your notes!" } // Call viewNotes
            3 -> { manager.editNote(); "Edited a note" } // Call editNote
            4 -> { manager.deleteNote(); "Deleted a note" } // Call deleteNote
            5 -> "Exiting Note App. Data secured. I’ll be in the void, not caring!" // Exit message
            else -> "Invalid option, try again." // Handle wrong input
        }

        println(message) // Print result message

        if (choice == 5) break // Stop loop if user chose exit
    }
}
