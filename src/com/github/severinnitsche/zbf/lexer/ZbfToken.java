package com.github.severinnitsche.zbf.lexer;

public sealed interface ZbfToken permits ZbfToken.Literal, ZbfToken.DoubleColon, ZbfToken.Equals, ZbfToken.ColonEquals,
    ZbfToken.LeftSlimArrow, ZbfToken.RightSlimArrow, ZbfToken.RightFatArrow, ZbfToken.LeftCurly, ZbfToken.RightCurly,
    ZbfToken.LeftBracket, ZbfToken.RightBracket, ZbfToken.HashTag, ZbfToken.LineBreak, ZbfToken.Comma {
  record Literal(String value) implements ZbfToken {
  }

  record DoubleColon() implements ZbfToken {
  }

  record Equals() implements ZbfToken {
  }

  record ColonEquals() implements ZbfToken {
  }

  record LeftSlimArrow() implements ZbfToken {
  }

  record RightSlimArrow() implements ZbfToken {
  }

  record RightFatArrow() implements ZbfToken {
  }

  record LeftCurly() implements ZbfToken {
  }

  record RightCurly() implements ZbfToken {
  }

  record LeftBracket() implements ZbfToken {
  }

  record RightBracket() implements ZbfToken {
  }

  record HashTag() implements ZbfToken {

  }

  record LineBreak() implements ZbfToken {

  }

  record Comma() implements ZbfToken {

  }

  static ZbfToken of(String s) {
    return switch(s) {
      case ")" -> new RightBracket();
      case "(" -> new LeftBracket();
      case "}" -> new RightCurly();
      case "{" -> new LeftCurly();
      case "=>","⇒" -> new RightFatArrow();
      case "->","→" -> new RightSlimArrow();
      case "<-","←" -> new LeftSlimArrow();
      case ":=","≔" -> new ColonEquals();
      case "=" -> new Equals();
      case "::","∷" -> new DoubleColon();
      case "#" -> new HashTag();
      case "\n","\r\n" -> new LineBreak();
      case "," -> new Comma();
      default -> new Literal(s);
    };
  }
}
