# Zbf

Zbf is proposed to be a modern, imperative language which shall also enable one to write truly functional Code without any ridiculous overhang. It is designed to be accessible for people, who are familiar with programming or formal Mathematics.

## Syntax

### Variables & Functions

`name [:: type] [= value]`\
To declare / assign a local, mutable variable\
`name [:: type] [:= value]`\
To declare / assign a local, final variable\
`name [:: type] [= value]`\
`name [:: modifier] [= value]`\
`name [:: modifier type] [= value]`\
To declare / assign a mutable field\
`name [:: type] [:= value]`\
`name [:: modifier] [:= value]`\
`name [:: modifier type] [:= value]`\
To declare / assign a final field\
**If there is no type provided it will be inferred by the value given.**\
`name :: [modifier] ([name[: type]]) -> [returntype] {}`\
To declare a final function. However, since functions are first-order types, they may also be declared / assigned like normal variables.\
`name # [modifier] type`\
To declare a native function.\
`<-` is used as return statement.

### Classes
Classes must be located within a file of the same name, except they are an inner class.\
Classes whom do not declare a constructor visible to other classes are considered abstract.\
Classes within a closed inheritance hierarchy may omit declaring their super class.\
Within a class file any class may be accessed unqualified if, and only if there is no ambiguity towards which class is being referred to.\
*new* is **not** considered a keyword, rather a function that allocates memory and invokes the appropriate constructor.\
It is still to be decided, whether this will refer to the pointer of the object, or the partially applied function of the structure.\
Objects as returned by the current implementation of *new* are considered to be the partially applied function of the structure, which type signature is as following:\
`name :: pointer -> literal -> *`\
wherein *name* is the structure, *pointer* is a Pointer to the memory location of the object, *literal* is the function or field name, * is any type (function or field)\
Adding to the "this discussion" it is to be answered, whether *this* represents just the *pointer* or *name(pointer)*.\
It is also to be sorted out, whether the current implementation of *new* shall be replaced with one that returns an either, because otherwise there must be a type that represents an unknown type if *new* is invoked with the wrong arguments.\ 
A class that provides inheritance restrictions (*->*) but does not allow for any classes to inherit is considered final.
```
[imports]
[static fields]
[static functions]
[Super <-] name [-> [Sub]] :: [modifier] {
  [fields]
  [constructors]
  [methods]
  [inner classes]
}
```
#### Imports
#### Fields
Fields must declare their respective type.
##### Static Fields
Static fields are accessed via a structure.
##### Instance Fields
Instance fields are accessed via an object.
#### Static Functions
Static functions are accessed via a structure
#### Constructors
```
[name [:: [modifier] [[this ~>] (args) [-> name]]]] {
    
}
```
#### Instance Methods
```
name :: [modifier] [this ~>] ([name[: type]]) -> [returntype] {}
```
#### Inner Classes

### Special Features
*.* is considered white space\
There needs to be a way to implement a function for just a subset of values of a type to correctly infer the return type of *new* to something more specific than *any* and validate method calls at compile time.\
*=*, *:=*, *<*, and *>* are considered functions that take two arguments. They are always used as infix. (Yes, they are technically impure)\

### Loops
#### For
`[for var] [from start to] [condition] end [with change] {}`\
`[for var] [start ..] [condition] end [| change] {}`\
`[for var] [from .. to] [condition] end [with change] {}`\
`[for var] [from start to] [condition] end [| change] {}`\
*var* is the variable, *start* is the initial value, *condition* is an expression that returns a function, that takes end as an argument, *change* is a unary-operator of *var*.

### Visibility Modifier:
    object              -> only in the same object (except classes/static)
    structure           -> only in the same (sub)structure (default in classes; precedence 1)
    dir[ectory] [local] -> only in the same directory
    global              -> all (class-default; precedence 0)

### Draft
```
name [:: datentyp] = value
name [:: datentyp] := value (final)
name [:: datentyp]

name :: [modifier] (name[: datentyp]) -> [returntype] := expression
name :: [modifier] (name[: datentyp]) -> [returntype] = expression 
name :: [modifier] (name[: datentyp]) -> [returntype] {}
name :: [modifier] (name[: datentyp]) -> [returntype] {}

Klassen:
Fields müssen den Datentyp angeben
Abstrakte Klassen definieren keinen Konstruktor, bzw. nur mit visibility structure
Konstruktoren sind geil
statische Methoden werden außerhalb des Klassenblocks definiert
Bei verschlossenen Vererbungshierarchien muss  die Superklasse nicht angegeben werden
[Super <-] name [-> [Sub]] :: [modifier] {
  msg :: string
  [name [:: [modifier] [([*this,]args) [-> name]]]] {
    
  }
}

List a -> Cons a, Nil a :: {
  Cons a -> :: global {
    value :: a
    tail :: List
    Cons :: (value) {
       this.value := value
       this.tail := new Nil
    }
  }
  Nil a -> :: global {
    {}
  }
}

new als Funktion:
constructor = new Foo
constructor(bar)

new :: global name -> args => Literal -> any -> any := {
  object := malloc(SizeLookupTable(name))
  function := FunctionLookupTable(name)
  <- function.object.name(args)
}

Bei gecurrieten Functionen wird die Grenze zwischen verwendeten variablen und uneigentlichen rückgabewert mit => gekennzeichnet, wird kein => verwendet ist nur der erste parameter ein variablenname

. als Alias fürs Leerzeichen:

Objekte sind Funktionen, die einen Literal entgegennehmen und any -> any zurückliefern

FunctionLookupTable :: global structure -> object => Literal -> any -> any := 
Gibt die Grundgfunction der Struktur zurück, dass bei nicht statischen Methoden object implizit übergibt

In einer Klasse werden Funktionsaufrufe zum gleichen Objekt als <Struktur>(<this>)(<method>)(<args>) interpretiert.
Funktionsaufrufe zur gleichen Struktur werden als <Struktur>(<method>)(<args>) interpretiert.
Andere Funktionsaufrufe richten sich nach den Imports.
Funktionsaufrufe der Form <Struktur>(<this>)(<args>) geben den Pointer zu dem Attribut zurück.
Die "Werte" von Literals werden als eigene Typen angesehen, dass ist wichtig, damit man den Rückgabewert von new korrekt inferen kann
= und := sind Funktionen, die als Infix benutzt werden
Dazu: Es ist eine überlegung wert, ob this nicht als Pointer, sondern Objekt übergeben wird, da so viele implizite Funktionsaufrufe vermieden werden und das Statement <- this nicht so ambigous wäre

[for i] [from 0 to] [i < ]7 [with i++]
for i 0..7 | i++
7 {}

visibility Modifier:
object              -> Nur im Objekt (nicht für Klassen/statische)
structure           -> Nur im Datentyp/Subtyp (default innerhalb von Klassen; precedence 1)
dir[ectory] [local] -> Nur im selben Verzeichnis
global              -> Alle (Klassendefault; precedence 0)
```