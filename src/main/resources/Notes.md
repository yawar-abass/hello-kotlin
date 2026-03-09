# Kotlin Learning Notes

## File 1: Fundamentals, SOLID, and Memory

### Type Casting in Kotlin

#### Upcasting

Casting from a child class to a parent class type (Implicit and 100% safe).

```kotlin
val myDog = Dog()
val myAnimal: Animal = myDog // Safe upcast
```

#### Downcasting

Casting from a parent class to a specific child class type.

Use Unsafe Cast (`as`) to force it, or Safe Cast (`as?`) to return null instead of crashing.

```kotlin
val animal: Animal = Dog()
val dog1: Dog = animal as Dog   // Unsafe: Crashes if animal is a Cat
val dog2: Dog? = animal as? Dog // Safe: Returns null if animal is a Cat
```

#### Smart Casting

Kotlin automatically downcasts a variable immediately after you check its type using `is`.

```kotlin
if (animal is Dog) {
    animal.fetch() // Kotlin smart casts 'animal' to Dog automatically here
}
```

### S.O.L.I.D. Principles

Single Responsibility Principle (SRP)
A class should have one, and only one, reason to change.

```kotlin
// BAD: One class doing math and database work
class Employee { fun calculatePay() {}  fun saveToDb() {} }

// GOOD: Separated responsibilities
class PayCalculator { fun calculate(emp: Employee) {} }
class EmployeeRepository { fun save(emp: Employee) {} }
```

#### Open-Closed Principle (OCP)

A class should be open for extension but closed for modification.

```kotlin
interface Report { fun generate(): String }

// We can add infinite new formats without modifying existing classes
class HtmlReport : Report { override fun generate() = "<html>...</html>" }
class PdfReport : Report { override fun generate() = "PDF Data" }
```

#### Liskov Substitution Principle (LSP)

You should be able to replace a parent with its child without breaking the app.

```kotlin
// BAD: Forcing a Penguin to inherit a fly() method it can't use
// GOOD: Segregate behaviors
interface Flyable { fun fly() }
class Eagle : Flyable { override fun fly() = println("Soaring!") }
class Penguin { fun swim() = println("Swimming!") } // Doesn't break rules
```

#### Interface Segregation Principle (ISP)

Classes should not be forced to depend on interfaces they do not use. Break up "fat" interfaces.

```kotlin
interface Workable { fun work() }
interface Breakable { fun takeLunchBreak() }

// Robot only implements what it actually does
class RobotWorker : Workable { override fun work() = println("Assembling...") }
```

#### Dependency Inversion Principle (DIP)

High-level logic should depend on interfaces, not low-level tools (Dependency Injection).

```kotlin
interface Database { fun save(data: String) }

// UserService doesn't care if it's MySQL or Mongo, it just uses the interface
class UserService(private val db: Database) {
    fun register(name: String) = db.save(name)
}
```

### String vs. StringBuilder

String (Immutable)
Cannot be changed. Modifying it creates a new object in memory (bad for loops).

```kotlin
var str = "Hello"
str += " World" // Creates a brand new object, abandons the old one
```

#### StringBuilder (Mutable)

Changes data in place without allocating new memory. Use `buildString` in Kotlin.

```kotlin
val result = buildString {
    append("Hello")
    for (i in 1..3) append(" $i")
} // Result: "Hello 1 2 3"
```

### Singletons in Kotlin

The object Keyword
Creates exactly one thread-safe instance in the entire app. No constructors allowed.

Kotlin
object DatabaseManager {
var isConnected = false
fun connect() { isConnected = true }
}
// Usage:
DatabaseManager.connect()

````

#### `companion object`
A Singleton tied inside a regular class (Kotlin's version of static).

```kotlin
class User(val name: String) {
    companion object {
        const val MAX_AGE = 120
        fun createGuest() = User("Guest")
    }
}
// Usage:
val guest = User.createGuest()
````

---

## File 2: Kotlin Features & Async

### Data Classes

#### `data class`

Used to hold state. Automatically generates `equals()`, `hashCode()`, `toString()`, and `copy()`.

```kotlin
data class User(val name: String, val age: Int)

val u1 = User("Alice", 25)
val u2 = u1.copy(age = 26) // Easily copy and modify
```

#### Destructuring

Unpack a data class directly into multiple variables.

```kotlin
val (userName, userAge) = u1
println("$userName is $userAge years old")
```

### Collections (Lists, Sets, Maps)

#### Immutable (Read-Only)

Cannot be modified after creation.

```kotlin
val names = listOf("Alice", "Bob")
// names.add("Charlie") // ERROR: Cannot mutate
```

#### Mutable (Read & Write)

Can be modified dynamically.

```kotlin
val mutableNames = mutableListOf("Alice", "Bob")
mutableNames.add("Charlie") // Works perfectly
```

### Lambda Functions

#### Anonymous Functions

Functions without a name, passed as variables.

```kotlin
val sum = { a: Int, b: Int -> a + b }
println(sum(5, 10)) // Prints 15
```

#### The `it` Keyword

If a lambda has only one parameter, you can omit the name and use `it`.

```kotlin
val numbers = listOf(1, 2, 3)
val doubled = numbers.map { it * 2 } // Result: [2, 4, 6]
```

### Extensions

#### Extension Functions

Add new functions to existing classes without inheriting them.

```kotlin
fun String.removeSpaces(): String {
    return this.replace(" ", "")
}

val cleanText = "A B C".removeSpaces() // Result: "ABC"
```

#### Extension Properties

Add computed properties (getters) to existing classes.

```kotlin
val List<Int>.middleIndex: Int
    get() = this.size / 2

val mid = listOf(1, 2, 3, 4, 5).middleIndex // Result: 2
```

### Coroutines (Asynchronous Programming)

#### `suspend` Functions

Functions that can be paused to wait for heavy tasks without blocking the main thread.

```kotlin
suspend fun fetchUserData(): String {
    delay(2000) // Simulates a 2-second network call safely
    return "User Data"
}
```

#### Dispatchers (Thread Pools)

- **Dispatchers.Main**: For UI updates.
- **Dispatchers.IO**: For Network/Database calls.
- **Dispatchers.Default**: For heavy CPU calculations.

```kotlin
// Launching a coroutine on the IO dispatcher for a database call
CoroutineScope(Dispatchers.IO).launch {
    val data = fetchUserData()

    // Switch to Main to update the UI
    withContext(Dispatchers.Main) {
        println(data)
    }
}
```

## Equality in Kotlin (`equals()`)

### Structural Equality (`==`)
- Checks if the **data/content** is the same.
- In Kotlin, `a == b` safely translates to `a?.equals(b) ?: (b === null)` behind the scenes.
- Eliminates the need to call `.equals()` manually.

### Referential Equality (`===`)
- Checks if two references point to the **exact same object in memory**.
- Mostly used for performance optimization or checking if a Singleton is identical.

### Default vs. Data Classes
- **Normal `class`:** `==` behaves like `===` by default (compares memory). You must manually override `equals()` and `hashCode()` to compare data.
- **`data class`:** Automatically overrides `equals()` to compare all properties listed in the primary constructor.

### Overriding `equals()`
- If you write custom equality logic, you **must** override `hashCode()`.
```kotlin
override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is MyClass) return false
    return this.id == other.id // Custom comparison
}

```

## Kotlin Serialization (`kotlinx.serialization`)

### Serialization vs. Deserialization
- **Serialization (Encoding):** Converting a Kotlin object in memory into a format that can be transmitted or stored (e.g., Object ➡️ JSON String).
- **Deserialization (Decoding):** Converting transmitted data back into a working Kotlin object (e.g., JSON String ➡️ Object).

### Why use `kotlinx.serialization`?
- It is Kotlin's official library.
- It uses a compiler plugin instead of slow Java reflection.
- It is 100% compatible with Kotlin's Null Safety and default parameter values (unlike older libraries like Gson).

### Implementation
- **The Annotation:** You must mark the target `data class` with `@Serializable`.
- **Encoding:** `Json.encodeToString(object)`
- **Decoding:** `Json.decodeFromString<MyClass>(jsonString)`

```kotlin
@Serializable
data class User(val name: String, val age: Int)

// Serialize
val jsonStr = Json.encodeToString(User("Alice", 25)) // {"name":"Alice","age":25}

// Deserialize
val userObj = Json.decodeFromString<User>(jsonStr)

```
## Pure Functions (Functional Programming)

### The Two Rules of a Pure Function
1. **Deterministic (Same Input = Same Output):** The function must always return the exact same result if given the same arguments. It cannot rely on external variables, random numbers, or the current time.
2. **No Side Effects:** The function cannot alter anything outside of its own scope. It cannot modify global variables, write to a database, alter a file, or print to the console.

### Why use them?
- **Testing:** Extremely easy to unit test because they require zero outside setup or mocking.
- **Thread Safety:** Safe to use in multi-threaded/coroutine environments. Since they don't modify shared state, they eliminate race conditions.

### Examples
- **Impure:** `fun add(a: Int) = a + globalVar` (Depends on external state)
- **Impure:** `fun save(data: String) { db.insert(data) }` (Causes a side effect)
- **Pure:** `fun multiply(a: Int, b: Int): Int = a * b` (Safe, predictable, isolated)

## Anonymous Classes (Object Expressions)

### What are they?
- A class that has no name, created on the fly for immediate, one-time use.
- Replaces Java's `new InterfaceName() { ... }` syntax.
- Unlike a Singleton (`object Name`), object expressions create a brand-new instance every time they run.

### Syntax
- Uses the `object : ParentType` syntax.
```kotlin
val listener = object : MouseListener {
    override fun onHover() { println("Hovering") }
    override fun onClick() { println("Clicked") }
}
```

### Extending Classes on the Fly
- You can create an anonymous subclass of an open class to override a method without creating a dedicated named class.

```Kotlin
val specialDog = object : Dog() {
override fun bark() = println("A very unique bark!")
}
```
### Anonymous Classes vs. Lambdas
- Use Lambdas ({ }): When implementing an interface that has only one single method (SAM interface).

- Use Anonymous Classes (object :): When implementing an interface or abstract class that requires overriding two or more methods.

## Scope Functions (`let`, `apply`, `run`, `also`, `with`)

Scope functions execute a lambda block within the context of an object. The key differences are whether they use `this` or `it`, and whether they return the *Context Object* or the *Lambda Result*.

### 1. `let` (Context: `it` | Returns: Lambda Result)
- Used almost exclusively for Null Safety.
- Combined with `?.`, it executes the block only if the object is not null.
```kotlin
val length = nullableName?.let { 
    println("Name: $it")
    it.length 
}
```
### 2. apply (Context: this | Returns: Context Object)
  - Used for configuring objects immediately after creating them.

- You can access properties directly without typing it.property.

```Kotlin
val car = Car().apply {
color = "Red"
doors = 4
}
```

### 3. also (Context: it | Returns: Context Object)
   - Used for side effects (like logging, debugging, or printing) without modifying the object or breaking a chain of calls.

```Kotlin
val list = mutableListOf(1, 2).also { println("Initial: $it") }

```

### 4. run (Context: this | Returns: Lambda Result)
   - Used when you need to configure an object and compute a final return value in one go.

```Kotlin
val isValid = user.run {
println("Checking $name")
age >= 18 // Returns this boolean
}

```


### 5. with (Context: this | Returns: Lambda Result)
 -  Identical to run, but called as a standard function instead of an extension. Used to group operations on an already existing object.

```Kotlin
with(databaseConnection) {
open()
insert(data)
close()
}
```
## Coroutines & Async Programming

### Core Concepts
- **Coroutines:** Lightweight threads. They pause (`suspend`) instead of blocking the underlying OS thread, allowing massive concurrency with low memory usage.
- **`suspend`:** A keyword telling the compiler the function can be paused and resumed later.

### Builders
- **`launch { }`:** Fire-and-forget. Starts a coroutine and returns a `Job` (no result data).
- **`async { }`:** Starts a coroutine and returns a `Deferred<T>` (like a Promise). Use `.await()` to extract the result. Perfect for running tasks in parallel.

### Dispatchers (Thread Pools)
- **`IO`:** For Network/Database waiting.
- **`Default`:** For heavy CPU math.
- **`Main`:** For UI updates.
- **`withContext()`:** Used inside a coroutine to safely jump to a different thread pool.

### Structured Concurrency
- Coroutines are strictly bound to a `CoroutineScope`.
- If a scope is destroyed, all child coroutines inside it are safely cancelled, preventing orphaned tasks and memory leaks.