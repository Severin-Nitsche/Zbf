# Zbf

Zbf is proposed to be a modern, imperative language which shall also enable one to write truly functional Code without any ridiculous overhang. It is designed to be accessible for people, who are familiar with programming or formal Mathematics.

## Syntax
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