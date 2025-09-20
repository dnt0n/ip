# ✨Just A Chill Guy✨

A tiny, friendly CLI chatbot that helps you manage **ToDos**, **Deadlines**, and **Events** — and it auto-saves your tasks between runs.

> “✨ Chill and let me handle your tasks for ya! ✨”

---

## Features

- **Add ToDo**  
  Create a simple task with just a name. Great for quick notes.  
  _Example:_ `todo Buy milk`

- **Add Deadline** (`/by`)  
  Track a task with a specific due date/time. The app highlights it as a deadline in your list.  
  _Example:_ `deadline CS assignment /by 2025-09-21 2359`

- **Add Event** (`/from ... /to ...`)  
  Block out time windows (start → end). Perfect for meetings or activities.  
  _Example:_ `event Hackathon /from 2025-10-03 0900 /to 2025-10-04 1800`

- **List tasks**  
  Shows all tasks with a **1-based index**, type badges (T/D/E), and done/undone status.  
  _Example output:_ `1.[T][ ] Buy milk`

- **Mark / Unmark**  
  Toggle a task as **done** or **not done**. Marked tasks display a ✓ indicator in the list.  
  _Examples:_ `mark 2`, `unmark 2`

- **Delete**  
  Remove a task by its index. You’ll get a confirmation message.  
  _Example:_ `delete 3`

- **Tag / Untag**  
  Attach a short label to any task (like `urgent`, `school`) to group or filter mentally.  
  _Examples:_ `tag 1 urgent`, `untag 1`  
  _Tip:_ Tags show alongside tasks in `list`.

- **Search**  
  Find tasks by keyword (case-insensitive, partial matches). Returns only the matching lines.  
  _Example:_ `find report`

- **Auto-save**  
  Every change is saved instantly to a local file, and tasks are restored the next time you run the app. No extra steps needed.

- **Friendly responses** 😄 
  Short, human messages that keep things upbeat and easy to read.

---

## Install & Run

```bash
# Download the release JAR from the GitHub Releases page
java -jar <downloaded-file>.jar
```
---
Sample Entries
---
- todo Buy milk
- deadline CS assignment /by 2025-09-21 2359
- event Hackathon /from 2025-10-03 0900 /to 2025-10-04 1800
- list
- mark 2 
- tag 3 school 
- find hack 
- bye
