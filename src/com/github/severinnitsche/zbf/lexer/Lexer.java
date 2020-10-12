package com.github.severinnitsche.zbf.lexer;

import com.github.severinnitsche.function.algebra.type.CharAccumulator;
import com.github.severinnitsche.function.algebra.type.CharList;
import com.github.severinnitsche.function.algebra.type.Either;
import com.github.severinnitsche.function.algebra.type.List;
import com.github.severinnitsche.function.algebra.util.Strings;

public interface Lexer {
  static Either<Throwable,List<ZbfToken>> lex(String s) {
    return Strings.
        reduce(s,
            (either, c) -> either.flatmap(
                accumulator -> accumulator.accumulate(c)
            )
            , Either.from(new CharAccumulator<>(
                CharList.empty(),
                List.empty(),
                list -> Either.from(ZbfToken.of(list.reduce(StringBuilder::append, new StringBuilder()).toString())),
                (list, c) -> Character.isAlphabetic(c) || Character.isWhitespace(c) ||
                    c == '.' || c == '(' || c == ')' || c == '-' || c == '>' || c == ':' || c == '{' || c == '}' ||
                    c == '=' || c == '<' || c == ',',
                (list, c) -> c == '<' || c == '(' || c == ')' || c == '{' || c == '}' || c == '\n' || c == ',' ||
                    (c == ':' && list instanceof CharList.Cons cons && cons.head() != ':') ||
                    (c == '=' && list instanceof CharList.Cons cons3 && cons3.head() != ':') ||
                    (c == '-' && list instanceof CharList.Cons cons2 && cons2.head() != '<'),
                (list, c) -> c == '.' || Character.isWhitespace(c),
                (list, c) -> c == '>' || c == '(' || c == ')' || c == '{' || c == '}' || c == '=' || c == '\n' ||
                    c == ',' ||
                    (c == ':' && list instanceof CharList.Cons cons && cons.head() == ':') ||
                    (c == '-' && list instanceof CharList.Cons cons2 && cons2.head() == '<')
            ))
        ).
        flatmap(CharAccumulator::reduce).
        map(CharAccumulator::accumulated);
  }

  static void main(String[] args) {
    System.out.println(lex(
        """
                List a -> Cons a, Nil :: {
                
                  append :: global a -> List a
                  prepend :: global a -> List a
                  concat :: global List a -> List a
                  empty :: global () -> List := new Nil()
                
                  List a <- Cons a -> :: global {
                    value :: a
                    tail :: List a
                    Cons :: object (this,value,tail) {
                      this.value := value
                      this.tail := tail
                    }
                    Cons :: global value := new Cons(value,new Nil())
                    append :: global value := new Cons(value,this.tail.append(value))
                    prepend :: global value := new Cons(value,this)
                    concat :: global list := new Cons(value,this.tail.concat(list))
                  }
                  List a <- Nil -> :: global {
                    {}
                    append :: global value := new Cons(value)
                    prepend :: global value := new Cons(value)
                    concat :: global list := list
                  }
                }
            """));
  }
}
