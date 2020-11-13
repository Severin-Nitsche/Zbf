package com.github.severinnitsche.zbf.ast;

import com.github.severinnitsche.function.algebra.type.List;

public interface AST {
  interface Variable extends AST {

    record Class(List<AST> statics, Expression.Declaration classDef, Expression.Block body) implements AST {
    }

    sealed interface DataType extends AST permits DataType.TypeCons, DataType.Type, DataType.Complex {
      record TypeCons(Type base, Type type) implements DataType {
      }

      sealed interface Type extends DataType permits Type.ConsType, Type.Void, Type.Tuple, Type.Function, Type.Method {
        record ConsType(String name, List<Type> args) implements Type {
        }

        record Void() implements Type {
        }

        record Tuple(List<Type> types) implements Type {

        }

        record Function(Type in, Type out) implements Type {
        }

        record Method(Type in, Function out) implements Type {
        }
      }

      sealed interface Complex extends DataType permits Complex.TypeDef, Complex.NamedType {
        record TypeDef(List<TypeCons> constraints, Type type) implements Complex {
        }

        record NamedType(String name, TypeDef type) implements Complex {
        }
      }
    }

    record Literal(String value) implements AST {
    }

    enum Modifier implements AST {
      OBJECT, STRUCTURE, DIRECTORY, DIRECTORY_LOCAL, GLOBAL
    }

    interface Expression extends AST {
      record Declaration(Literal name, Modifier visibility, DataType type) implements Expression {
      }

      record Block(List<Expression> expressions) implements Expression {
      }

      interface Simple extends Expression {
        record Assignment(Declaration leftHand, Simple rightHand) implements Simple {
        }

        record Definition(Declaration leftHand, Expression rightHand) implements Simple {
        }

        interface Operation extends Simple {
          record Asterisk(Operation leftHand, Operation rightHand) implements Operation {
          }

          record Minus(Operation leftHand, Operation rightHand) implements Operation {
          }

          record Plus(Operation leftHand, Operation rightHand) implements Operation {
          }

          record Slash(Operation leftHand, Operation rightHand) implements Operation {
          }

          record Value(Literal reference) implements Operation {

          }
        }
      }
    }
  }
}
