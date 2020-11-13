package com.github.severinnitsche.zbf.lexer;

import com.github.severinnitsche.function.algebra.type.CharAccumulator;
import com.github.severinnitsche.function.algebra.type.CharList;
import com.github.severinnitsche.function.algebra.type.Either;
import com.github.severinnitsche.function.algebra.type.List;
import com.github.severinnitsche.function.algebra.util.Strings;

import static java.lang.Character.isAlphabetic;
import static java.lang.Character.isWhitespace;

public interface Lexer {
  static Either<Throwable, List<ZbfToken>> lex(String s) {
    return Strings.
        reduce(s,
            (either, c) -> either.flatmap(
                accumulator -> accumulator.accumulate(c)
            )
            , Either.from(new CharAccumulator<>(
                CharList.empty(),
                List.empty(),
                list -> Either.from(ZbfToken.of(list.reduce((str, c) -> str.length() > 0 && str.charAt(0) == '\n' ? str : str.append(c), new StringBuilder()).toString())),
                (list, c) -> isAlphabetic(c) || isWhitespace(c) ||
                    c == '.' || c == '(' || c == ')' || c == '-' || c == '>' || c == ':' || c == '{' || c == '}' ||
                    c == '=' || c == '<' || c == ',',
                (list, c) -> c == '<' || c == '(' || c == ')' || c == '{' || c == '}' || c == ',' ||
                    (c == ':' && list instanceof CharList.Cons cons && cons.head() != ':') ||
                    (c == '=' && list instanceof CharList.Cons cons3 && cons3.head() != ':') ||
                    (c == '-' && list instanceof CharList.Cons cons2 && cons2.head() != '<') ||
                    (c == '\n' && list instanceof CharList.Nil) ||
                    (c == '\n' && list instanceof CharList.Cons cons4 && cons4.head() != '\n'),
                (list, c) -> c == '.' || (isWhitespace(c) && c != '\n') ||
                    (c != '\n' && list instanceof CharList.Cons cons && cons.head() == '\n'),
                (list, c) -> c == '>' || c == '(' || c == ')' || c == '{' || c == '}' || c == '=' ||
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
                \r
                \r
                
                
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
