package com.github.severinnitsche.zbf.ast;

import com.github.severinnitsche.function.algebra.type.List;

public interface AST {
  interface Variable extends AST {

    record Class(List<AST> statics, Expression.Declaration classDef, Expression.Block body) implements AST {
    }

    record Type(Literal type, List<Literal> arguments) implements AST {
    }

    record Literal(String value) implements AST {
    }

    enum Modifier implements AST {
      OBJECT, STRUCTURE, DIRECTORY, DIRECTORY_LOCAL, GLOBAL
    }

    interface Expression extends AST {
      record Declaration(Literal name, Modifier visibility, Type type) implements Expression {
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
