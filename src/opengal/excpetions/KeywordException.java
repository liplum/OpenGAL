package opengal.excpetions;

import opengal.syntax.Keyword;

public class KeywordException extends RuntimeException {
    public KeywordException(String information) {
        super(information);
    }
}
