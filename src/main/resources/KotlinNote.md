## Data Classes

### `data class`
- Used specifically to hold data/state, eliminating Java-style boilerplate.
- Automatically generates useful functions behind the scenes: `equals()`, `hashCode()`, `toString()`, and `copy()`.
- Example: `data class User(val name: String, val age: Int)`
- **Destructuring:** Allows you to unpack the class directly into variables (e.g., `val (name, age) = myUser`).

---

## Collections (Lists, Sets, Maps)

### Immutable Collections (Read-Only)
- Cannot be modified (no adding, removing, or updating items) after creation.
- Created using `listOf()`, `setOf()`, `mapOf()`.
- Safer for concurrency and passing data around without accidental changes.

### Mutable Collections (Read & Write)
- Can be modified dynamically.
- Created using `mutableListOf()`, `mutableSetOf()`, `mutableMapOf()`.
- e.g., `val names = mutableListOf("Alice")` -> `names.add("Bob")`.

---

## Lambda Functions

### What they are
- Anonymous functions (functions without a name) that can be treated as variables, passed as arguments, or returned from other functions.
- Syntax: Surrounded by curly braces `{ }`, parameters go before the `->` arrow, and the body goes after.
- Example: `val sum = { a: Int, b: Int -> a + b }`

### The `it` Keyword
- If a lambda only has **one** parameter, Kotlin lets you skip naming it and automatically calls it `it`.
- Example: `val numbers = listOf(1, 2, 3)` -> `numbers.map { it * 2 }`

---

## Extensions

### Extension Functions
- Allows you to add brand-new functions to existing classes (even ones you don't own, like the core Kotlin `String` class) without inheriting from them.
- You write the class name, a dot, and your new function name.
- Example: `fun String.removeSpaces() = this.replace(" ", "")`
- You can now call `"Hello World".removeSpaces()` anywhere in your app.

### Extension Properties
- Similar to functions, but adds a property (getter) to an existing class.
- You cannot add actual memory/fields this way, only computed properties.
- Example: `val List<Int>.middleIndex: Int get() = this.size / 2`

---

## Coroutines (Asynchronous Programming)

### What they are
- "Lightweight threads." They allow you to run heavy tasks (like network calls or database queries) in the background without freezing the user interface.
- You can launch thousands of coroutines at the same time without crashing your app, unlike traditional Java Threads.

### `suspend` Functions
- A function marked with `suspend` can be paused and resumed later.
- It frees up the thread it was running on while it waits for a long task to finish, then picks up exactly where it left off.

### Dispatchers (The Thread Pools)
- **`Dispatchers.Main`:** For updating the UI (buttons, text).
- **`Dispatchers.IO`:** For heavy background work (Network calls, reading/writing files or databases).
- **`Dispatchers.Default`:** For heavy CPU math calculations (sorting massive lists, parsing JSON).